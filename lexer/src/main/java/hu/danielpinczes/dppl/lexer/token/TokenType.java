package hu.danielpinczes.dppl.lexer.token;

import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public enum TokenType {

    ILLEGAL("ILLEGAL"),
    EOF("EOF"),
    IDENT("IDENT"),
    INT("INT"),
    ASSIGN("="),
    PLUS("+"),
    MINUS("-"),
    BANG("!"),
    ASTERISK("*"),
    SLASH("/"),
    LT("<"),
    GT(">"),
    EQ("=="),
    NOT_EQ("!="),
    COMMA(","),
    SEMICOLON(";"),
    LPAREN("("),
    RPAREN(")"),
    LBRACE("{"),
    RBRACE("}"),
    FUNCTION("FUNCTION"),
    LET("LET"),
    TRUE("TRUE"),
    FALSE("FALSE"),
    IF("IF"),
    ELSE("ELSE"),
    RETURN("RETURN");

    private final String name;

    public static final Map<String, TokenType> keywords = new ImmutableMap.Builder<String, TokenType>()
            .put("fn", TokenType.FUNCTION)
            .put("let", TokenType.LET)
            .put("true", TokenType.TRUE)
            .put("false", TokenType.FALSE)
            .put("if", TokenType.IF)
            .put("else", TokenType.ELSE)
            .put("return", TokenType.RETURN)
            .build();
}
