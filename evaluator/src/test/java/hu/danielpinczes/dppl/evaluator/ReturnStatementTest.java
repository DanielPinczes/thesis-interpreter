package hu.danielpinczes.dppl.evaluator;

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReturnStatementTest extends BaseTest {

    private final Map<String, Object> tests = ImmutableMap.<String, Object>builder()
            .put("true", true)
   .put("return 10;", 10)
   .put("return 10; 9;", 10)
   .put("return 2 * 5; 9;", 10)
   .put("9; return 2 * 5; 9;", 10)
   .put("if (10 > 1) { return 10; }", 10)
            .put("if (10 > 1) {\n" +
                    "  if (10 > 1) {\n" +
                    "    return 10;\n" +
                    "  }\n" +
                    "\n" +
                    "  return 1;\n" +
                    "}", 10)
            .put("let f = fn(x) {\n" +
                    "  return x;\n" +
                    "  x + 10;\n" +
                    "};\n" +
                    "f(10);", 10)
            .put("let f = fn(x) {\n" +
                    "   let result = x + 10;\n" +
                    "   return result;\n" +
                    "   return 10;\n" +
                    "};\n" +
                    "f(10);", 20)
            .build();

    @Override
    @Test
    public void testAll() {
        for (var test : tests.entrySet()) {
            String input = test.getKey();
            var expected = String.valueOf(test.getValue());
            var result = testEval(input);
            assertEquals(expected, result.inspect(), "Failed test for input: " + input);
        }
    }
}