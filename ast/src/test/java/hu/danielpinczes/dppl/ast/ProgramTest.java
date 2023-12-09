package hu.danielpinczes.dppl.ast;

import hu.danielpinczes.dppl.ast.statement.LetStatement;
import hu.danielpinczes.dppl.ast.statement.expression.Identifier;
import hu.danielpinczes.dppl.lexer.token.Token;
import hu.danielpinczes.dppl.lexer.token.TokenType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProgramTest {


    @Test
    public void testLetStatement() {
        var token = new Token(TokenType.LET, "let");
        var name = new Identifier(new Token(TokenType.IDENT, "myVar"), "myVar");
        var value = new Identifier(new Token(TokenType.IDENT, "anotherVar"), "anotherVar");
        LetStatement letStatement = new LetStatement(token, name, value);

        Program program = new Program();
        program.setStatements(List.of(letStatement));

        assertEquals("let myVar = anotherVar;", program.toString());
    }
}