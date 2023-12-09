package hu.danielpinczes.dppl.ast;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// The root node of every AST our parser produces
@Getter
@Setter
public class Program {

    private List<Statement> statements;

    public Program() {
        this.statements = new ArrayList<>();
    }

    public String tokenLiteral() {
        if (statements.size() > 0) {
            return statements.get(0).tokenLiteral();
        } else {
            return "";
        }
    }

    @Override
    public String toString() {
        return statements.stream()
                .map(Statement::toString)
                .collect(Collectors.joining());
    }

}
