package hu.danielpinczes.dppl.ast.statement.expression;

import hu.danielpinczes.dppl.ast.Expression;
import hu.danielpinczes.dppl.lexer.token.Token;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class BooleanExpression implements Expression {

    private final Token token;
    private final boolean value;

    @Override
    public void expressionNode() {}

    @Override
    public String tokenLiteral() {
        return token.literal();
    }

    @Override
    public String toString() {
        return token.literal();
    }

}