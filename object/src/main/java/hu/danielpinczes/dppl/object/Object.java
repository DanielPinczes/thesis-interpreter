package hu.danielpinczes.dppl.object;

public interface Object {

    ObjectType getType();

    String inspect();

    default HashKey hashKey() {
        throw new UnsupportedOperationException("HashKey not supported for this type.");
    }
}