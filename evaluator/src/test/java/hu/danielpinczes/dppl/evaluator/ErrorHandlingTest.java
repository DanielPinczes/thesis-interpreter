package hu.danielpinczes.dppl.evaluator;

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ErrorHandlingTest extends BaseTest {

    private final Map<String, String> tests = ImmutableMap.<String, String>builder()
            .put("5 + true;", "ERROR: type mismatch: INTEGER_OBJ + BOOLEAN_OBJ")
            .put("5 + true; 5;", "ERROR: type mismatch: INTEGER_OBJ + BOOLEAN_OBJ")
            .put("-true", "ERROR: unknown operator: -BOOLEAN_OBJ")
            .put("true + false;", "ERROR: unknown operator: BOOLEAN_OBJ + BOOLEAN_OBJ")
            .put("true + false + true + false;", "ERROR: unknown operator: BOOLEAN_OBJ + BOOLEAN_OBJ")
            .put("5; true + false; 5", "ERROR: unknown operator: BOOLEAN_OBJ + BOOLEAN_OBJ")
            .put("\"Hello\" - \"World\"", "ERROR: unsupported operator for strings: -")
            .put("if (10 > 1) { true + false; }", "ERROR: unknown operator: BOOLEAN_OBJ + BOOLEAN_OBJ")
            .put("if (10 > 1) {\n" +
                    "  if (10 > 1) {\n" +
                    "    return true + false;\n" +
                    "  }\n" +
                    "\n" +
                    "  return 1;\n" +
                    "}", "ERROR: unknown operator: BOOLEAN_OBJ + BOOLEAN_OBJ")
            .put("foobar", "ERROR: identifier not found: foobar")
            .put("{\"name\": \"Monkey\"}[fn(x) { x }];", "ERROR: Unusable as hash key: FUNCTION_OBJ")
            .put("999[1]", "ERROR: index operator not supported: INTEGER_OBJ")
            .build();


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