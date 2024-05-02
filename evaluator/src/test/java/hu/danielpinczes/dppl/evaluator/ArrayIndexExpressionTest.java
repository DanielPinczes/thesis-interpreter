package hu.danielpinczes.dppl.evaluator;

import com.google.common.collect.ImmutableMap;
import hu.danielpinczes.dppl.object.IntegerObject;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ArrayIndexExpressionTest extends BaseTest{

    private static final Map<String, java.lang.Object> tests = ImmutableMap.<String, java.lang.Object>builder()
            .put("[1, 2, 3][0]", "1")
            .put("[1, 2, 3][1]", "2")
            .put("[1, 2, 3][2]", "3")
            .put("let i = 0; [1][i];", "1")
            .put("[1, 2, 3][1 + 1];", "3")
            .put("let myArray = [1, 2, 3]; myArray[2];", "3")
            .put("let myArray = [1, 2, 3]; myArray[0] + myArray[1] + myArray[2];", "6")
            .put("let myArray = [1, 2, 3]; let i = myArray[0]; myArray[i]", "2")
            .put("[1, 2, 3][3]", "null")
            .put("[1, 2, 3][-1]", "null")
            .build();


    @Test
    @Override
    public void testAll() {
        for (Map.Entry<String, java.lang.Object> entry : tests.entrySet()) {
            var input = entry.getKey();
            var expected = entry.getValue();
            var result = testEval(input);
            if (expected != null) {
                assertNotNull(result, "Expected result to be not null for input: " + input);
                assertEquals(expected, String.valueOf(result.inspect()), "Failed for input: " + input);
            } else {
                assertNull(result, "Expected result to be null for input: " + input);
            }
        }
    }
}
