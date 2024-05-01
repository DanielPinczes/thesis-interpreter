package hu.danielpinczes.dppl.ast.statement.expression;

import hu.danielpinczes.dppl.ast.Expression;
import hu.danielpinczes.dppl.lexer.token.Token;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class ArrayLiteral implements Expression {

    private final Token token;
    private final List<Expression> elements;

    @Override
    public void expressionNode() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String tokenLiteral() {
        return token.literal();
    }

    @Override
    public String toString() {
        String elementsString = elements.stream()
                .map(Expression::toString)
                .collect(Collectors.joining(", "));
        return "[" + elementsString + "]";
    }
}
