package hu.danielpinczes.dppl.ast.statement;

import hu.danielpinczes.dppl.ast.Statement;
import hu.danielpinczes.dppl.lexer.token.Token;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class BlockStatement implements Statement {

    private final Token token; // the '{' token
    private final List<Statement> statements;

    @Override
    public void statementNode() {}

    @Override
    public String tokenLiteral() {
        return token.literal();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Statement statement : statements) {
            result.append(statement.toString());
        }
        return result.toString();
    }

}