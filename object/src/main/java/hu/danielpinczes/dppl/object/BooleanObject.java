package hu.danielpinczes.dppl.object;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BooleanObject implements Object {

    private final boolean value;

    @Override
    public ObjectType getType() {
        return ObjectType.BOOLEAN_OBJ;
    }

    @Override
    public String inspect() {
        return String.valueOf(value);
    }

    @Override
    public HashKey hashKey() {
        return new HashKey(getType(), value ? 1 : 0);
    }
}
