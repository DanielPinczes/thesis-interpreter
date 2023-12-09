package hu.danielpinczes.dppl.ast.statement.expression;

import hu.danielpinczes.dppl.ast.Expression;
import hu.danielpinczes.dppl.ast.statement.BlockStatement;
import hu.danielpinczes.dppl.lexer.token.Token;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class IfExpression implements Expression {

    private final Token token; // The 'if' token
    private final Expression condition;
    private final BlockStatement consequence;
    private final BlockStatement alternative;

    @Override
    public void expressionNode() {}

    @Override
    public String tokenLiteral() {
        return token.literal();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("if ").append(condition.toString()).append(" ")
                .append(consequence.toString());

        if (alternative != null) {
            result.append("else ").append(alternative);
        }

        return result.toString();
    }


}