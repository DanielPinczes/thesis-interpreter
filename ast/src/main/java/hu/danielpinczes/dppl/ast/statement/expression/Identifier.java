package hu.danielpinczes.dppl.ast.statement.expression;

import hu.danielpinczes.dppl.ast.Expression;
import hu.danielpinczes.dppl.lexer.token.Token;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Identifier implements Expression {

    private Token token; // the token.IDENT token
    private String value;

    public Identifier(Token token, String value) {
        this.token = token;
        this.value = value;
    }

    @Override
    public void expressionNode() {}

    @Override
    public String tokenLiteral() {
        return token.literal();
    }

    @Override
    public String toString() {
        return value;
    }

}