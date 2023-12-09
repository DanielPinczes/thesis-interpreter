package hu.danielpinczes.dppl.ast.statement;

import hu.danielpinczes.dppl.ast.Expression;
import hu.danielpinczes.dppl.ast.Statement;
import hu.danielpinczes.dppl.lexer.token.Token;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.signature.qual.Identifier;

@Getter
@RequiredArgsConstructor
public class ReturnStatement implements Statement {

    private final Token token; // the 'return' token
    private final Expression returnValue;

    @Override
    public void statementNode() {
        // Marker method
    }

    @Override
    public String tokenLiteral() {
        return token.literal();
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append(tokenLiteral()).append(" ");
        if (returnValue != null) {
            out.append(returnValue.toString());
        }
        out.append(";");
        return out.toString();
    }
}
