package hu.danielpinczes.dppl.object;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class IntegerHashKeyTest {

    @Test
    void testIntegerHashKey() {
        Integer one1 = new Integer(1);
        Integer one2 = new Integer(1);
        Integer two1 = new Integer(2);
        Integer two2 = new Integer(2);
        assertEquals(one1.hashKey(), one2.hashKey(), "integers with the same content should have the same hash keys");
        assertEquals(two1.hashKey(), two2.hashKey(), "integers with the same content should have the same hash keys");
        assertNotEquals(one1.hashKey(), two1.hashKey(), "integers with different content should have different hash keys");
    }
}
