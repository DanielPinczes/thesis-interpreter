package hu.danielpinczes.dppl.evaluator;

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BooleanTest extends BaseTest {


    private final Map<String, Boolean> tests = ImmutableMap.<String, Boolean>builder()
            .put("true", true)
            .put("false", false)
            .put("1 < 2", true)
            .put("1 > 2", false)
            .put("1 < 1", false)
            .put("1 > 1", false)
            .put("1 == 1", true)
            .put("1 != 1", false)
            .put("1 == 2", false)
            .put("1 != 2", true)
            .put("true == true", true)
            .put("false == false", true)
            .put("true == false", false)
            .put("true != false", true)
            .put("false != true", true)
            .put("(1 < 2) == true", true)
            .put("(1 < 2) == false", false)
            .put("(1 > 2) == true", false)
            .put("(1 > 2) == false", true)
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