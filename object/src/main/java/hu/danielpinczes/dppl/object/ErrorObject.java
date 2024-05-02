package hu.danielpinczes.dppl.object;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ErrorObject implements DpplObject {

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