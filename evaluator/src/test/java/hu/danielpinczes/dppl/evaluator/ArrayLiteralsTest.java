package hu.danielpinczes.dppl.evaluator;

import com.google.common.collect.ImmutableMap;
import hu.danielpinczes.dppl.object.ArrayObject;
import hu.danielpinczes.dppl.object.IntegerObject;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ArrayLiteralsTest extends BaseTest {

    @Override
    @Test
    public void testAll() {
        var input = "[1, 2 * 2, 3 + 3]";
        var evaluated = testEval(input);
        assertInstanceOf(ArrayObject.class, evaluated, "Object is not Array. Got=" + evaluated.getClass().getName());
        var array = (ArrayObject) evaluated;
        assertEquals(3, array.getElements().size(), "Array has wrong number of elements.");
        assertIntegerObject(array.getElements().get(0), 1);
        assertIntegerObject(array.getElements().get(1), 4);
        assertIntegerObject(array.getElements().get(2), 6);
    }

    private void assertIntegerObject(Object obj, int expected) {
        assertInstanceOf(IntegerObject.class, obj, "Object is not Integer. Got=" + obj.getClass().getName());
        assertEquals(expected, ((IntegerObject) obj).getValue(), "Integer value mismatch.");
    }
}