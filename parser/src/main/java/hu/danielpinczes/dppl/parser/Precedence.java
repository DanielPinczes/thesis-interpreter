package hu.danielpinczes.dppl.parser;

public enum Precedence {

    LOWEST,
    EQUALS,
    LESSGREATER,
    SUM,
    PRODUCT,
    PREFIX,
    CALL,
    INDEX
}
