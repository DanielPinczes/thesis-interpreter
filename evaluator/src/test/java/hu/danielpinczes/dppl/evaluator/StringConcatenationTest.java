package hu.danielpinczes.dppl.evaluator;

import hu.danielpinczes.dppl.object.StringObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringConcatenationTest extends BaseTest {

    @Override
    @Test
    public void testAll() {
        var input = "\"Hello\" + \" \" + \"World!\"";
        var evaluated = testEval(input);
        assertInstanceOf(StringObject.class, evaluated, "Object is not String. Got=" + evaluated.getClass().getName());
        var str = (StringObject) evaluated;
        assertEquals("Hello World!", str.getValue(), "String has wrong value.");
    }
}