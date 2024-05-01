package hu.danielpinczes.dppl.object;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class IntegerObjectHashKeyTest {

    @Test
    void testIntegerHashKey() {
        IntegerObject one1 = new IntegerObject(1);
        IntegerObject one2 = new IntegerObject(1);
        IntegerObject two1 = new IntegerObject(2);
        IntegerObject two2 = new IntegerObject(2);
        assertEquals(one1.hashKey(), one2.hashKey(), "integers with the same content should have the same hash keys");
        assertEquals(two1.hashKey(), two2.hashKey(), "integers with the same content should have the same hash keys");
        assertNotEquals(one1.hashKey(), two1.hashKey(), "integers with different content should have different hash keys");
    }
}
