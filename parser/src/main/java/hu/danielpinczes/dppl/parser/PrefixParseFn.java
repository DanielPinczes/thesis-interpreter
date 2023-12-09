package hu.danielpinczes.dppl.parser;

import hu.danielpinczes.dppl.ast.Expression;

import java.util.function.Supplier;

public interface PrefixParseFn extends Supplier<Expression> {
}
