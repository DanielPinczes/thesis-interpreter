package hu.danielpinczes.dppl.object;


public class NullObject implements DpplObject {

    @Override
    public ObjectType getType() {
        return ObjectType.NULL_OBJ;
    }

    @Override
    public String inspect() {
        return "null";
    }
}