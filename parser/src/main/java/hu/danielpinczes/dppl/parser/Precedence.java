package hu.danielpinczes.dppl.parser;

public enum Precedence {

    LOWEST,
    EQUALS,      // ==
    LESSGREATER, // > or <
    SUM,         // +
    PRODUCT,     // *
    PREFIX,      // -X or !X
    CALL,       // myFunction(X)
    INDEX
}
