package hu.danielpinczes.dppl.ast.statement.expression;

import hu.danielpinczes.dppl.ast.Expression;
import hu.danielpinczes.dppl.lexer.token.Token;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class PrefixExpression implements Expression {

    private final Token token; // The prefix token, e.g. !
    private final String operator;
    private final Expression right;

    @Override
    public void expressionNode() {}

    @Override
    public String tokenLiteral() {
        return token.literal();
    }

    @Override
    public String toString() {
        return "(" + operator + right.toString() + ")";
    }

}