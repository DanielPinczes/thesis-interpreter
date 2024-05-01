package hu.danielpinczes.dppl.object;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class BooleanObjectHashKeyTest {

    @Test
    void testBooleanHashKey() {
        BooleanObject true1 = new BooleanObject(true);
        BooleanObject true2 = new BooleanObject(true);
        BooleanObject false1 = new BooleanObject(false);
        BooleanObject false2 = new BooleanObject(false);

        assertEquals(true1.hashKey(), true2.hashKey(), "trues should have the same hash key");
        assertEquals(false1.hashKey(), false2.hashKey(), "falses should have the same hash key");
        assertNotEquals(true1.hashKey(), false1.hashKey(), "true and false should not have the same hash key");
    }
}