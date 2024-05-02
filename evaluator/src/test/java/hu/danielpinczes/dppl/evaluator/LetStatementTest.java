package hu.danielpinczes.dppl.evaluator;

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LetStatementTest extends BaseTest {


    private final Map<String, Object> tests = ImmutableMap.<String, Object>builder()
            .put("true", true)
            .put("let a = 5; a;", 5)
            .put("let a = 5 * 5; a;", 25)
            .put("let a = 5; let b = a; b;", 5)
            .put("let a = 5; let b = a; let c = a + b + 5; c;", 15)
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