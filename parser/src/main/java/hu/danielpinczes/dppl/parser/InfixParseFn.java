package hu.danielpinczes.dppl.parser;

import hu.danielpinczes.dppl.ast.Expression;

import java.util.function.UnaryOperator;

public interface InfixParseFn extends UnaryOperator<Expression> {
}
