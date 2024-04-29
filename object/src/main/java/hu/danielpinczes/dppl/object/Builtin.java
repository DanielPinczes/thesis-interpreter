package hu.danielpinczes.dppl.object;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Builtin implements Object {

    private final BuiltinFunction fn;

    @Override
    public ObjectType getType() {
        return ObjectType.BUILTIN_OBJ;
    }

    @Override
    public String inspect() {
        return "builtin function";
    }
}