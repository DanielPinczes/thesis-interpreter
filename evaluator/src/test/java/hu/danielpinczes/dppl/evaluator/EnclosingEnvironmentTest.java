package hu.danielpinczes.dppl.evaluator;

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EnclosingEnvironmentTest extends BaseTest {


    private final String input = """
            let first = 10;
            let second = 10;
            let third = 10;

            let ourFunction = fn(first) {
              let second = 20;

              first + second + third;
            };

            ourFunction(20) + first + second;
            """;

    @Override
    @Test
    public void testAll() {
        var result = testEval(input);
        assertEquals("70", result.inspect(), "Failed for input: " + input);
    }
}