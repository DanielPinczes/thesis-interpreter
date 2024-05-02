package hu.danielpinczes.dppl.evaluator;

import hu.danielpinczes.dppl.object.FunctionObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class FunctionObjectTest extends BaseTest{

    @Test
    public void testFunctionObject() {
        var input = "fn(x) { x + 2; };";
        var evaluated = testEval(input);
        assertInstanceOf(FunctionObject.class, evaluated, "Object is not Function. Got=" + evaluated.getClass().getName());
        var fn = (FunctionObject) evaluated;
        assertEquals(1, fn.getParameters().size(), "Function has wrong number of parameters.");
        assertEquals("x", fn.getParameters().get(0).toString(), "Parameter is not 'x'.");
        var expectedBody = "(x + 2)";
        assertEquals(expectedBody, fn.getBody().toString(), "Body is not as expected.");
    }


    @Override
    public void testAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}