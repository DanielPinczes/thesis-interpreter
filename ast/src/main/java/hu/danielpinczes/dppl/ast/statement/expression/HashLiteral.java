package hu.danielpinczes.dppl.ast.statement.expression;

import hu.danielpinczes.dppl.ast.Expression;
import hu.danielpinczes.dppl.lexer.token.Token;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class HashLiteral implements Expression {

    private final Token token;
    private final Map<Expression, Expression> pairs;

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
        var pairsString = pairs.entrySet().stream()
                .map(entry -> entry.getKey().toString() + ":" + entry.getValue().toString())
                .collect(Collectors.joining(", "));
        return "{" + pairsString + "}";
    }
}
