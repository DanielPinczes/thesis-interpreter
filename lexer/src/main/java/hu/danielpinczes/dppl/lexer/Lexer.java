package hu.danielpinczes.dppl.lexer;

import hu.danielpinczes.dppl.lexer.automata.*;
import hu.danielpinczes.dppl.lexer.token.Token;
import hu.danielpinczes.dppl.lexer.token.TokenType;

public class Lexer {
    private final String input;
    private int position;
    private int readPosition;
    private char ch;

    public Lexer(String input) {
        this.input = input;
        this.readChar();
    }

    public Token nextToken() {
        Token token;

        skipWhitespace();

        switch (ch) {
            case '=':
                if (peekChar() == '=') {
                    readChar();
                    token = new Token(TokenType.EQ, "==");
                } else {
                    token = new Token(TokenType.ASSIGN, Character.toString(ch));
                }
                break;
            case '+':
                token = new Token(TokenType.PLUS, Character.toString(ch));
                break;
            case '-':
                token = new Token(TokenType.MINUS, Character.toString(ch));
                break;
            case '!':
                if (peekChar() == '=') {
                    readChar();
                    token = new Token(TokenType.NOT_EQ, "!=");
                } else {
                    token = new Token(TokenType.BANG, Character.toString(ch));
                }
                break;
            case '/':
                token = new Token(TokenType.SLASH, Character.toString(ch));
                break;
            case '*':
                token = new Token(TokenType.ASTERISK, Character.toString(ch));
                break;
            case '<':
                token = new Token(TokenType.LT, Character.toString(ch));
                break;
            case '>':
                token = new Token(TokenType.GT, Character.toString(ch));
                break;
            case ';':
                token = new Token(TokenType.SEMICOLON, Character.toString(ch));
                break;
            case ':':
                token = new Token(TokenType.COLON, Character.toString(ch));
                break;
            case ',':
                token = new Token(TokenType.COMMA, Character.toString(ch));
                break;
            case '{':
                token = new Token(TokenType.LBRACE, Character.toString(ch));
                break;
            case '}':
                token = new Token(TokenType.RBRACE, Character.toString(ch));
                break;
            case '(':
                token = new Token(TokenType.LPAREN, Character.toString(ch));
                break;
            case ')':
                token = new Token(TokenType.RPAREN, Character.toString(ch));
                break;
            case '"':
                token = new Token(TokenType.STRING, readString());
                break;
            case '[':
                token = new Token(TokenType.LBRACKET, Character.toString(ch));
                break;
            case ']':
                token = new Token(TokenType.RBRACKET, Character.toString(ch));
                break;
            case '\0':
                token = new Token(TokenType.EOF, "");
                break;
            default:
                if (isLetter(ch)) {
//                    return readIdentifierUsingFSM();
                                        String literal = readIdentifier();
                    token = new Token(Token.lookupIdent(literal), literal);
                    return token; // Early return as readIdentifier advances the chars
                } else if (isDigit(ch)) {
                    token = new Token(TokenType.INT, readNumber());
                    return token; // Early return as readNumber advances the chars
                } else {
                    token = new Token(TokenType.ILLEGAL, Character.toString(ch));
                }
                break;
        }

        readChar();
        return token;
    }

    private void skipWhitespace() {
        while (ch == ' ' || ch == '\t' || ch == '\n' || ch == '\r') {
            readChar();
        }
    }

    private void readChar() {
        if (readPosition >= input.length()) {
            ch = '\0';
        } else {
            ch = input.charAt(readPosition);
        }
        position = readPosition;
        readPosition++;
    }

    private char peekChar() {
        if (readPosition >= input.length()) {
            return '\0';
        } else {
            return input.charAt(readPosition);
        }
    }

    private Token readIdentifierUsingFSM() {
        int startPosition = position;
        var initial = new State();
        var identifierPart = new State(true);  // Ez az állapot végállapot is lehet.

        // Betűk és aláhúzások hozzáadása az első állapothoz, csak a kezdeti karakterekhez
        for (char c = 'a'; c <= 'z'; c++) {
            initial = initial.addTransition(new Transition(String.valueOf(c), identifierPart));
            initial = initial.addTransition(new Transition(String.valueOf(Character.toUpperCase(c)), identifierPart));
        }
        initial = initial.addTransition(new Transition("_", identifierPart));

        // Betűk, számok és aláhúzások hozzáadása a további részekhez
        for (char c = 'a'; c <= 'z'; c++) {
            identifierPart = identifierPart.addTransition(new Transition(String.valueOf(c), identifierPart));
            identifierPart = identifierPart.addTransition(new Transition(String.valueOf(Character.toUpperCase(c)), identifierPart));
        }
        for (char c = '0'; c <= '9'; c++) {
            identifierPart = identifierPart.addTransition(new Transition(String.valueOf(c), identifierPart));
        }
        identifierPart = identifierPart.addTransition(new Transition("_", identifierPart));

        var fsm = new FiniteStateMachine(initial);
        while (isLetter(ch) || ch == '_' || isDigit(ch)) {
            fsm = fsm.createNextState(Character.toString(ch));
            readChar();
            if (!fsm.isFinalState()) {
                throw new IllegalStateException("Invalid identifier");
            }
        }
        var literal = input.substring(startPosition, position);
        return new Token(Token.lookupIdent(literal), literal);
    }


    private String readIdentifier() {
        int startPosition = position;
        while (isLetter(ch)) {
            readChar();
        }
        return input.substring(startPosition, position);
    }

    private String readNumber() {
        int startPosition = position;
        while (isDigit(ch)) {
            readChar();
        }
        return input.substring(startPosition, position);
    }

    private String readString() {
        int startPosition = position + 1;
        do {
            readChar();
        } while (ch != '"' && ch != '\0');
        return input.substring(startPosition, position);
    }

    private static boolean isLetter(char ch) {
        return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || ch == '_';
    }

    private static boolean isDigit(char ch) {
        return ch >= '0' && ch <= '9';
    }
}
