package hu.danielpinczes.dppl.object;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class BooleanHashKeyTest {

    @Test
    void testBooleanHashKey() {
        Boolean true1 = new Boolean(true);
        Boolean true2 = new Boolean(true);
        Boolean false1 = new Boolean(false);
        Boolean false2 = new Boolean(false);

        assertEquals(true1.hashKey(), true2.hashKey(), "trues should have the same hash key");
        assertEquals(false1.hashKey(), false2.hashKey(), "falses should have the same hash key");
        assertNotEquals(true1.hashKey(), false1.hashKey(), "true and false should not have the same hash key");
    }
}