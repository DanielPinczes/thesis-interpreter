package hu.danielpinczes.dppl.evaluator;

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FunctionApplicationTest extends BaseTest {

    private final Map<String, Object> tests = ImmutableMap.<String, Object>builder()
            .put("let identity = fn(x) { x; }; identity(5);", 5)
            .put("let identity = fn(x) { return x; }; identity(5);", 5)
            .put("let double = fn(x) { x * 2; }; double(5);", 10)
            .put("let add = fn(x, y) { x + y; }; add(5, 5);", 10)
            .put("let add = fn(x, y) { x + y; }; add(5 + 5, add(5, 5));", 20)
            .put("fn(x) { x; }(5)", 5)
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
