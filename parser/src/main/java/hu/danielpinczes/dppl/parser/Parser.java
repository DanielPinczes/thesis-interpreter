package hu.danielpinczes.dppl.parser;

import hu.danielpinczes.dppl.ast.Expression;
import hu.danielpinczes.dppl.ast.Program;
import hu.danielpinczes.dppl.ast.Statement;
import hu.danielpinczes.dppl.ast.statement.BlockStatement;
import hu.danielpinczes.dppl.ast.statement.ExpressionStatement;
import hu.danielpinczes.dppl.ast.statement.LetStatement;
import hu.danielpinczes.dppl.ast.statement.ReturnStatement;
import hu.danielpinczes.dppl.ast.statement.expression.*;
import hu.danielpinczes.dppl.lexer.Lexer;
import hu.danielpinczes.dppl.lexer.token.Token;
import hu.danielpinczes.dppl.lexer.token.TokenType;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class Parser {

    private Lexer lexer;
    @Getter
    private final List<String> errors;
    private Token curToken;
    private Token peekToken;

    private final Map<TokenType, PrefixParseFn> prefixParseFns;
    private final Map<TokenType, InfixParseFn> infixParseFns;

    public Parser(Lexer lexer){
        this.lexer = lexer;
        this.errors = new ArrayList<>();
        this.prefixParseFns = new HashMap<>();
        this.infixParseFns = new HashMap<>();
        prefixParseFns.put(TokenType.IDENT, this::parseIdentifier);
        prefixParseFns.put(TokenType.INT, this::parseIntegerLiteral);
        prefixParseFns.put(TokenType.BANG, this::parsePrefixExpression);
        prefixParseFns.put(TokenType.MINUS, this::parsePrefixExpression);
        prefixParseFns.put(TokenType.TRUE, this::parseBoolean);
        prefixParseFns.put(TokenType.FALSE, this::parseBoolean);
        prefixParseFns.put(TokenType.LPAREN, this::parseGroupedExpression);
        prefixParseFns.put(TokenType.IF, this::parseIfExpression);
        prefixParseFns.put(TokenType.FUNCTION, this::parseFunctionLiteral);

        infixParseFns.put(TokenType.PLUS, this::parseInfixExpression);
        infixParseFns.put(TokenType.MINUS, this::parseInfixExpression);
        infixParseFns.put(TokenType.SLASH, this::parseInfixExpression);
        infixParseFns.put(TokenType.ASTERISK, this::parseInfixExpression);
        infixParseFns.put(TokenType.EQ, this::parseInfixExpression);
        infixParseFns.put(TokenType.NOT_EQ, this::parseInfixExpression);
        infixParseFns.put(TokenType.LT, this::parseInfixExpression);
        infixParseFns.put(TokenType.GT, this::parseInfixExpression);

        nextToken();
        nextToken();
    }

    private void nextToken() {
        curToken = peekToken;
        peekToken = lexer.nextToken();
    }

    private boolean expectPeek(TokenType type) {
        if (isPeekToken(type)) {
            nextToken();
            return true;
        } else {
            peekError(type);
            return false;
        }
    }

    private boolean isPeekToken(TokenType type) {
        return peekToken.type() == type;
    }

    private void peekError(TokenType type) {
        String msg = String.format("Expected next token to be %s, got %s instead", type, peekToken.type());
        errors.add(msg);
    }

    private void noPrefixParseFnError(TokenType type) {
        String msg = String.format("No prefix parse function for %s found", type);
        errors.add(msg);
    }

    public Program parseProgram() {
        Program program = new Program();

        while (!isCurToken(TokenType.EOF)) {
            Statement stmt = parseStatement();
            if (stmt != null) {
                program.getStatements().add(stmt);
            }
            nextToken();
        }

        return program;
    }

    private boolean isCurToken(TokenType type) {
        return curToken.type() == type;
    }

    public Statement parseStatement() {
        switch (curToken.type()) {
            case LET:
                return parseLetStatement();
            case RETURN:
                return parseReturnStatement();
            default:
                return parseExpressionStatement();
        }
    }

    public LetStatement parseLetStatement() {
        Token token = curToken;

        if (!expectPeek(TokenType.IDENT)) {
            return null;
        }

        Identifier name = new Identifier(curToken, curToken.literal());

        if (!expectPeek(TokenType.ASSIGN)) {
            return null;
        }

        nextToken();

        Expression value = parseExpression(Precedence.LOWEST);

        if (isPeekToken(TokenType.SEMICOLON)) {
            nextToken();
        }

        return new LetStatement(token, name, value);
    }

    public ReturnStatement parseReturnStatement() {
        Token token = curToken;

        nextToken();

        Expression returnValue = parseExpression(Precedence.LOWEST);

        if (isPeekToken(TokenType.SEMICOLON)) {
            nextToken();
        }

        return new ReturnStatement(token, returnValue);
    }

    public ExpressionStatement parseExpressionStatement() {
        Token token = curToken;

        Expression expression = parseExpression(Precedence.LOWEST);

        if (isPeekToken(TokenType.SEMICOLON)) {
            nextToken();
        }

        return new ExpressionStatement(token, expression);
    }

    public Expression parseExpression(Precedence precedence) {
        Supplier<Expression> prefix = prefixParseFns.get(curToken.type());
        if (prefix == null) {
            noPrefixParseFnError(curToken.type());
            return null;
        }

        Expression leftExp = prefix.get();

        while (!isPeekToken(TokenType.SEMICOLON) && precedence.compareTo(peekPrecedence()) < 0) {
            UnaryOperator<Expression> infix = infixParseFns.get(peekToken.type());
            if (infix == null) {
                return leftExp;
            }

            nextToken();
            leftExp = infix.apply(leftExp);
        }

        return leftExp;
    }

    private Precedence peekPrecedence() {
        return PrecedenceMapInitializer.getPrecedences()
                .getOrDefault(peekToken.type(), Precedence.LOWEST);
    }

    private Precedence curPrecedence() {
        return PrecedenceMapInitializer.getPrecedences()
                .getOrDefault(curToken.type(), Precedence.LOWEST);
    }

    public Expression parseIdentifier() {
        return new Identifier(curToken, curToken.literal());
    }

    public Expression parseIntegerLiteral() {
        try {
            long value = Long.parseLong(curToken.literal());
            return new IntegerLiteral(curToken, value);
        } catch (NumberFormatException e) {
            errors.add("Could not parse \"" + curToken.literal() + "\" as integer");
            return null;
        }
    }

    public Expression parsePrefixExpression() {
        Token token = curToken;
        String operator = curToken.literal();

        nextToken();

        Expression right = parseExpression(Precedence.PREFIX);
        return new PrefixExpression(token, operator, right);
    }

    public Expression parseInfixExpression(Expression left) {
        Token token = curToken;
        String operator = curToken.literal();

        Precedence precedence = curPrecedence();
        nextToken();

        Expression right = parseExpression(precedence);
        return new InfixExpression(token, left, operator, right);
    }

    public Expression parseBoolean() {
        return new BooleanExpression(curToken, curTokenIs(TokenType.TRUE));
    }

    private boolean curTokenIs(TokenType type) {
        return curToken.type() == type;
    }

    public Expression parseGroupedExpression() {
        nextToken();

        Expression exp = parseExpression(Precedence.LOWEST);

        if (!expectPeek(TokenType.RPAREN)) {
            return null;
        }

        return exp;
    }

    public Expression parseIfExpression() {
        Token token = curToken;

        if (!expectPeek(TokenType.LPAREN)) {
            return null;
        }

        nextToken();
        Expression condition = parseExpression(Precedence.LOWEST);

        if (!expectPeek(TokenType.RPAREN)) {
            return null;
        }

        if (!expectPeek(TokenType.LBRACE)) {
            return null;
        }

        BlockStatement consequence = parseBlockStatement();

        BlockStatement alternative = null;
        if (isPeekToken(TokenType.ELSE)) {
            nextToken();

            if (!expectPeek(TokenType.LBRACE)) {
                return null;
            }

            alternative = parseBlockStatement();
        }

        return new IfExpression(token, condition, consequence, alternative);
    }

    public BlockStatement parseBlockStatement() {
        Token token = curToken;
        List<Statement> statements = new ArrayList<>();

        nextToken();

        while (!curTokenIs(TokenType.RBRACE) && !curTokenIs(TokenType.EOF)) {
            Statement stmt = parseStatement();
            if (stmt != null) {
                statements.add(stmt);
            }
            nextToken();
        }

        return new BlockStatement(token, statements);
    }

    public Expression parseFunctionLiteral() {
        Token token = curToken;

        if (!expectPeek(TokenType.LPAREN)) {
            return null;
        }

        List<Identifier> parameters = parseFunctionParameters();

        if (!expectPeek(TokenType.LBRACE)) {
            return null;
        }

        BlockStatement body = parseBlockStatement();

        return new FunctionLiteral(token, parameters, body);
    }

    public List<Identifier> parseFunctionParameters() {
        List<Identifier> identifiers = new ArrayList<>();

        if (isPeekToken(TokenType.RPAREN)) {
            nextToken();
            return identifiers;
        }

        nextToken();

        identifiers.add(new Identifier(curToken, curToken.literal()));

        while (isPeekToken(TokenType.COMMA)) {
            nextToken();
            nextToken();
            identifiers.add(new Identifier(curToken, curToken.literal()));
        }

        if (!expectPeek(TokenType.RPAREN)) {
            return null;
        }

        return identifiers;
    }

    public Expression parseCallExpression(Expression function) {
        Token token = curToken;
        List<Expression> arguments = parseCallArguments();
        return new CallExpression(token, function, arguments);
    }

    public List<Expression> parseCallArguments() {
        List<Expression> args = new ArrayList<>();

        if (isPeekToken(TokenType.RPAREN)) {
            nextToken();
            return args;
        }

        nextToken();
        args.add(parseExpression(Precedence.LOWEST));

        while (isPeekToken(TokenType.COMMA)) {
            nextToken();
            nextToken();
            args.add(parseExpression(Precedence.LOWEST));
        }

        if (!expectPeek(TokenType.RPAREN)) {
            return null;
        }

        return args;
    }

}
