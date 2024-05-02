package hu.danielpinczes.dppl.evaluator;

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IfElseExpressionTest extends BaseTest {

    private final Map<String, Object> tests = ImmutableMap.<String, Object>builder()
            .put("true", true)
            .put("if (true) { 10 }", 10)
            .put("if (false) { 10 }", "null")
            .put("if (1) { 10 }", 10)
            .put("if (1 < 2) { 10 }", 10)
            .put("if (1 > 2) { 10 }", "null")
            .put("if (1 > 2) { 10 } else { 20 }", 20)
            .put("if (1 < 2) { 10 } else { 20 }", 10)
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