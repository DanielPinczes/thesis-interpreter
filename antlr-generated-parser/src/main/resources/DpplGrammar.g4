grammar DpplGrammar;

// Lexer rules
LET                 : 'let';
RETURN              : 'return';
IF                  : 'if';
ELSE                : 'else';
TRUE                : 'true';
FALSE               : 'false';
FN                  : 'fn';
ASSIGN              : '=';
PLUS                : '+';
MINUS               : '-';
BANG                : '!';
ASTERISK            : '*';
SLASH               : '/';
LT                  : '<';
GT                  : '>';
EQUAL               : '==';
NOTEQUAL            : '!=';
LE                  : '<=';
GE                  : '>=';

LPAREN              : '(';
RPAREN              : ')';
LBRACE              : '{';
RBRACE              : '}';
COMMA               : ',';
SEMICOLON           : ';';

IDENT               : [a-zA-Z_][a-zA-Z_0-9]*;
INT                 : [0-9]+;

WS                  : [ \t\r\n]+ -> skip;
COMMENT             : '#' ~[\r\n]* -> skip;

// Parser rules
program             : statement*;

statement           : letStatement
                    | returnStatement
                    | expressionStatement;

letStatement        : LET IDENT ASSIGN expression SEMICOLON;

returnStatement     : RETURN expression SEMICOLON;

expressionStatement : expression SEMICOLON;

expression          : equalityExpression;

equalityExpression  : comparisonExpression ((EQUAL | NOTEQUAL) comparisonExpression)*;

comparisonExpression: additionExpression ((LT | GT | LE | GE) additionExpression)*;

additionExpression  : multiplicationExpression ((PLUS | MINUS) multiplicationExpression)*;

multiplicationExpression: unaryExpression ((ASTERISK | SLASH) unaryExpression)*;

unaryExpression     : (BANG | MINUS) unaryExpression
                    | primaryExpression;

primaryExpression   : INT
                    | TRUE
                    | FALSE
                    | IDENT
                    | LPAREN expression RPAREN
                    | ifExpression
                    | functionLiteral
                    | callExpression;

ifExpression        : IF expression blockStatement (ELSE blockStatement)?;

blockStatement      : LBRACE statement* RBRACE;

functionLiteral     : FN LPAREN parameters? RPAREN blockStatement;

parameters          : IDENT (COMMA IDENT)*;

callExpression      : IDENT LPAREN arguments? RPAREN;

arguments           : expression (COMMA expression)*;