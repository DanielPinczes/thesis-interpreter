package hu.danielpinczes.dppl.ast.statement.expression;

import hu.danielpinczes.dppl.ast.Expression;
import hu.danielpinczes.dppl.lexer.token.Token;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StringLiteral implements Expression {

    private final Token token;
    private final String value;


    @Override
    public void expressionNode() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String tokenLiteral() {
        return token.literal();
    }

}
