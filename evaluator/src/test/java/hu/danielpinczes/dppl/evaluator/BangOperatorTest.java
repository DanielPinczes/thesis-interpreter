package hu.danielpinczes.dppl.evaluator;

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BangOperatorTest extends BaseTest {

    private final Map<String, Boolean> tests = ImmutableMap.<String, Boolean>builder()
            .put("true", true)
            .put("!true", false)
            .put("!false", true)
            .put("!5", false)
            .put("!!true", true)
            .put("!!false", false)
            .put("!!5", true)
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