package hu.danielpinczes.dppl.lexer.token;


public record Token(TokenType type, String literal) {

    public static TokenType lookupIdent(String ident) {
        return TokenType.keywords.getOrDefault(ident, TokenType.IDENT);
    }
}
