package hu.danielpinczes.dppl.ast.statement.expression;

import hu.danielpinczes.dppl.ast.Expression;
import hu.danielpinczes.dppl.lexer.token.Token;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class IndexExpression implements Expression {

    private final Token token; // Token representing the '['
    private final Expression left; // The expression on the left side of the index operator
    private final Expression index; // The expression inside the brackets


    @Override
    public String tokenLiteral() {
        return token.literal();
    }

    // Method to get the string representation of the index expression
    @Override
    public String toString() {
        return "(" + left.toString() + "[" + index.toString() + "])";
    }

    // Mimics the Go's expressionNode method - typically empty but signifies that this class is part of the AST.
    @Override
    public void expressionNode() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}