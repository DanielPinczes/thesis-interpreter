package hu.danielpinczes.dppl.parser;


import com.google.common.collect.ImmutableMap;
import hu.danielpinczes.dppl.lexer.token.TokenType;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class PrecedenceMapInitializer {

    @Getter
    private static final Map<TokenType, Precedence> precedences = ImmutableMap.<TokenType, Precedence>builder()
            .put(TokenType.EQ, Precedence.EQUALS)
            .put(TokenType.NOT_EQ, Precedence.EQUALS)
            .put(TokenType.LT, Precedence.LESSGREATER)
            .put(TokenType.GT, Precedence.LESSGREATER)
            .put(TokenType.PLUS, Precedence.SUM)
            .put(TokenType.MINUS, Precedence.SUM)
            .put(TokenType.SLASH, Precedence.PRODUCT)
            .put(TokenType.ASTERISK, Precedence.PRODUCT)
            .put(TokenType.LPAREN, Precedence.CALL)
            .build();
}
