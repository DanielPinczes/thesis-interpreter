package hu.danielpinczes.dppl.ast.statement;

import hu.danielpinczes.dppl.ast.Expression;
import hu.danielpinczes.dppl.ast.Statement;
import hu.danielpinczes.dppl.lexer.token.Token;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class WhileStatement implements Statement {

    private final Token token;
    private final Expression condition;
    private final BlockStatement body;

    @Override
    public String tokenLiteral() {
        return token.literal();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(token.literal());
        builder.append(" (");
        builder.append(condition.toString());
        builder.append(") ");
        builder.append(body.toString());
        return builder.toString();
    }

    @Override
    public void statementNode() {
        // Intentionally empty
    }
}
