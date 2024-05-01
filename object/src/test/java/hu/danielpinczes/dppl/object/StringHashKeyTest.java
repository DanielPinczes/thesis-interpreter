package hu.danielpinczes.dppl.object;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class StringHashKeyTest {

    @Test
    void testStringHashKey() {
        var hello1 = new StringObject("Hello World");
        var hello2 = new StringObject("Hello World");
        var diff1 = new StringObject("My name is johnny");
        var diff2 = new StringObject("My name is johnny");

        assertEquals(hello1.hashKey(), hello2.hashKey(), "strings with same content should have the same hash keys");
        assertEquals(diff1.hashKey(), diff2.hashKey(), "strings with same content should have the same hash keys");
        assertNotEquals(hello1.hashKey(), diff1.hashKey(), "strings with different content should have different hash keys");
    }
}
