package hu.danielpinczes.dppl.object;

public interface DpplObject {

    ObjectType getType();

    String inspect();

    default HashKey hashKey() {
        throw new UnsupportedOperationException("HashKey not supported for this type.");
    }
}