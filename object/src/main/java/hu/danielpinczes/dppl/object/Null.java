package hu.danielpinczes.dppl.object;


class Null implements Object {

    @Override
    public ObjectType getType() {
        return ObjectType.NULL_OBJ;
    }

    @Override
    public String inspect() {
        return "null";
    }
}