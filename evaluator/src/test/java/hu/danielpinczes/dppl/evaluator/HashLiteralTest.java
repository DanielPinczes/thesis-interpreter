package hu.danielpinczes.dppl.evaluator;

import hu.danielpinczes.dppl.object.*;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HashLiteralTest extends BaseTest {

    private final String input = """
            let two = "two";
            {
                "one": 10 - 9,
                two: 1 + 1,
                "thr" + "ee": 6 / 2,
                4: 4,
                true: 5,
		        false: 6
            }
            """;

    @Test
    @Override
    public void testAll() {
        var evaluated = testEval(input);
        assertTrue(evaluated instanceof HashObject, "Eval didn't return Hash. Got=" + evaluated.getClass().getName());
        var result = (HashObject) evaluated;
        var expected = new HashMap<HashKey, Long>() {{
            put(new StringObject("one").hashKey(), 1L);
            put(new StringObject("two").hashKey(), 2L);
            put(new StringObject("three").hashKey(), 3L);
            put(new IntegerObject(4).hashKey(), 4L);
            put(new BooleanObject(true).hashKey(), 5L);
            put(new BooleanObject(false).hashKey(), 6L);
        }};
        assertEquals(expected.size(), result.getPairs().size(), "Hash has wrong number of pairs.");
        expected.forEach((key, value) -> {
            assertTrue(result.getPairs().containsKey(key), "No pair for given key in pairs.");
            assertEquals(value, ((IntegerObject) result.getPairs().get(key).getValue()).getValue(), "Value mismatch for key.");
        });
    }

    @Test
    public void testHashKeyEquality() {
        HashKey key1 = new HashKey(ObjectType.STRING_OBJ, "hello".hashCode());
        HashKey key2 = new HashKey(ObjectType.STRING_OBJ, "hello".hashCode());
        assertEquals(key1, key2);
    }
}
