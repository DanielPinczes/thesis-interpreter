package hu.danielpinczes.dppl.ast.statement.expression;

import hu.danielpinczes.dppl.ast.Expression;
import hu.danielpinczes.dppl.ast.statement.BlockStatement;
import hu.danielpinczes.dppl.lexer.token.Token;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class FunctionLiteral implements Expression {

    private final Token token; // The 'fn' token
    private final List<Identifier> parameters;
    private final BlockStatement body;


    @Override
    public void expressionNode() {}

    @Override
    public String tokenLiteral() {
        return token.literal();
    }

    @Override
    public String toString() {
        List<String> paramStrings = parameters.stream()
                .map(Identifier::toString)
                .collect(Collectors.toList());
        return tokenLiteral() + "(" + String.join(", ", paramStrings) + ") " + body.toString();
    }

}