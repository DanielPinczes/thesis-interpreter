package hu.danielpinczes.dppl.ast.statement;

import hu.danielpinczes.dppl.ast.Expression;
import hu.danielpinczes.dppl.ast.Statement;
import hu.danielpinczes.dppl.lexer.token.Token;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpressionStatement implements Statement {

    private Token token; // the first token of the expression
    private Expression expression;

    public ExpressionStatement(Token token, Expression expression) {
        this.token = token;
        this.expression = expression;
    }

    @Override
    public void statementNode() {}

    @Override
    public String tokenLiteral() {
        return token.literal();
    }

    @Override
    public String toString() {
        return (expression != null) ? expression.toString() : "";
    }
}