package hu.danielpinczes.dppl.parser;

import hu.danielpinczes.dppl.ast.Expression;
import hu.danielpinczes.dppl.ast.Program;
import hu.danielpinczes.dppl.ast.Statement;
import hu.danielpinczes.dppl.ast.statement.LetStatement;
import hu.danielpinczes.dppl.ast.statement.expression.BooleanExpression;
import hu.danielpinczes.dppl.ast.statement.expression.Identifier;
import hu.danielpinczes.dppl.ast.statement.expression.IntegerLiteral;
import hu.danielpinczes.dppl.lexer.Lexer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @Test
    public void testParserPerformance() throws IOException {

        var input = "let result = factorial(5);\n" +
                "let anotherVar = 10;\n" +
                "let add = fn(a, b) { return a + b; };\n" +
                "let sum = add(result, anotherVar);\n" +
                "var a = 5;";

        long startTime = System.nanoTime();


        Lexer l = new Lexer(input);
        Parser p = new Parser(l);
        Program program = p.parseProgram();

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("Parser Duration: " + duration + " nanoseconds");
    }

    @Test
    void testLetStatements() {
        class Test {
            String input;
            String expectedIdentifier;
            Object expectedValue;

            Test(String input, String expectedIdentifier, Object expectedValue) {
                this.input = input;
                this.expectedIdentifier = expectedIdentifier;
                this.expectedValue = expectedValue;
            }
        }

        Test[] tests = {
                new Test("let x = 5;", "x", 5),
                new Test("let y = true;", "y", true),
                new Test("let foobar = y;", "foobar", "y")
        };

        for (Test test : tests) {
            Lexer l = new Lexer(test.input);
            Parser p = new Parser(l);
            Program program = p.parseProgram();
            checkParserErrors(p);

            assertEquals(1, program.getStatements().size(), "program.Statements does not contain 1 statements.");

            LetStatement stmt = (LetStatement) program.getStatements().get(0);
            testLetStatement(stmt, test.expectedIdentifier);
            testLiteralExpression(stmt.getValue(), test.expectedValue);
        }
    }

    private void checkParserErrors(Parser parser) {
        List<String> errors = parser.getErrors();
        if (!errors.isEmpty()) {
            fail("Parser has " + errors.size() + " errors: " + String.join(", ", errors));
        }
    }

    private void testLetStatement(Statement stmt, String expectedName) {
        assertEquals("let", stmt.tokenLiteral(), "Statement TokenLiteral not 'let'.");

        assertTrue(stmt instanceof LetStatement, "Statement not instance of LetStatement.");
        LetStatement letStmt = (LetStatement) stmt;

        assertEquals(expectedName, letStmt.getName().getValue(), "LetStatement Name not '" + expectedName + "'.");
        assertEquals(expectedName, letStmt.getName().tokenLiteral(), "LetStatement Name TokenLiteral not '" + expectedName + "'.");
    }

    private void testLiteralExpression(Expression exp, Object expected) {
        if (expected instanceof Integer) {
            assertTrue(exp instanceof IntegerLiteral, "Expression not instance of IntegerLiteral.");
            assertEquals((((Integer) expected).longValue()), ((IntegerLiteral) exp).getValue(), "IntegerLiteral value mismatch.");
        } else if (expected instanceof String) {
            assertTrue(exp instanceof Identifier, "Expression not instance of Identifier.");
            assertEquals((String) expected, ((Identifier) exp).getValue(), "Identifier value mismatch.");
        } else if (expected instanceof Boolean) {
            assertTrue(exp instanceof BooleanExpression, "Expression not instance of BooleanExpression.");
            assertEquals((Boolean) expected, ((BooleanExpression) exp).isValue(), "BooleanExpression value mismatch.");
        } else {
            fail("Type of expression not handled: " + exp.getClass().getName());
        }
    }

}