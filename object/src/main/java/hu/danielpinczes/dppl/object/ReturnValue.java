package hu.danielpinczes.dppl.object;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReturnValue implements Object {

    private final Object value;

    @Override
    public ObjectType getType() {
        return ObjectType.RETURN_VALUE_OBJ;
    }

    @Override
    public String inspect() {
        return value.inspect();
    }
}