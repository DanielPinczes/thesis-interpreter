package hu.danielpinczes.dppl.ast.statement.expression;

import hu.danielpinczes.dppl.ast.Expression;
import hu.danielpinczes.dppl.lexer.token.Token;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class CallExpression implements Expression {

    private final Token token;
    private final Expression function;
    private final List<Expression> arguments;

    @Override
    public void expressionNode() {}

    @Override
    public String tokenLiteral() {
        return token.literal();
    }

    @Override
    public String toString() {
        List<String> argStrings = arguments.stream()
                .map(Expression::toString)
                .collect(Collectors.toList());
        return function.toString() + "(" + String.join(", ", argStrings) + ")";
    }

}