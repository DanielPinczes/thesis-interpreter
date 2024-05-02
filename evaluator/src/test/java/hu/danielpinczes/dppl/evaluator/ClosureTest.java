package hu.danielpinczes.dppl.evaluator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClosureTest extends BaseTest {


    private final String input = "let newAdder = fn(x) {\n" +
            "  fn(y) { x + y };\n" +
            "};\n" +
            "\n" +
            "let addTwo = newAdder(2);\n" +
            "addTwo(2);";

    @Override
    @Test
    public void testAll() {
        var result = testEval(input);
        assertEquals("4", result.inspect(), "Failed for input: " + input);
    }
}