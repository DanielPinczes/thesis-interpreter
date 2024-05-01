package hu.danielpinczes.dppl.object;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class IntegerObject implements Object {

    private final long value;

    @Override
    public ObjectType getType() {
        return ObjectType.INTEGER_OBJ;
    }

    @Override
    public String inspect() {
        return String.valueOf(value);
    }

    @Override
    public HashKey hashKey() {
        return new HashKey(getType(), value);
    }
}
