package hu.danielpinczes.dppl.object;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ErrorObject implements Object {

    private final String message;

    @Override
    public ObjectType getType() {
        return ObjectType.ERROR_OBJ;
    }

    @Override
    public String inspect() {
        return "ERROR: " + message;
    }
}