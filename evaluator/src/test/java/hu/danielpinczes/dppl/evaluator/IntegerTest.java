package hu.danielpinczes.dppl.evaluator;

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;


import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IntegerTest extends BaseTest {

   private final  Map<String, Integer> tests = ImmutableMap.<String, Integer>builder()
           .put("5", 5)
           .put("10", 10)
           .put("-5", -5)
           .put("-10", -10)
           .put("5 + 5 + 5 + 5 - 10", 10)
           .put("2 * 2 * 2 * 2 * 2", 32)
           .put("-50 + 100 + -50", 0)
           .put("5 * 2 + 10", 20)
           .put("5 + 2 * 10", 25)
           .put("20 + 2 * -10", 0)
           .put("50 / 2 * 2 + 10", 60)
           .put("2 * (5 + 10)", 30)
           .put("3 * 3 * 3 + 10", 37)
           .put("3 * (3 * 3) + 10", 37)
           .put("(5 + 10 * 2 + 15 / 3) * 2 + -10", 50)
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