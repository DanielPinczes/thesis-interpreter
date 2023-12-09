package hu.danielpinczes.dppl.ast.statement;

import hu.danielpinczes.dppl.ast.Expression;
import hu.danielpinczes.dppl.ast.Statement;
import hu.danielpinczes.dppl.ast.statement.expression.Identifier;
import hu.danielpinczes.dppl.lexer.token.Token;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LetStatement implements Statement {

    private Token token; // the token.LET token
    private Identifier name;
    private Expression value;

    public LetStatement(Token token, Identifier name, Expression value) {
        this.token = token;
        this.name = name;
        this.value = value;
    }

    public void statementNode() {
        // Intentionally empty
    }

    public String tokenLiteral() {
        return token.literal();
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append(tokenLiteral()).append(" ");
        out.append(name.toString());
        out.append(" = ");
        if (value != null) {
            out.append(value);
        }
        out.append(";");
        return out.toString();
    }
}
