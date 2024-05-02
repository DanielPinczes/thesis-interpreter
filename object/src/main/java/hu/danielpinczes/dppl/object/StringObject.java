package hu.danielpinczes.dppl.object;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@Getter
@RequiredArgsConstructor
public class StringObject implements DpplObject, Hashable {

    private final String value;

    @Override
    public ObjectType getType() {
        return ObjectType.STRING_OBJ;
    }

    @Override
    public String inspect() {
        return value;
    }

        @Override
    public HashKey hashKey() {
        long h = 0;
        for (char c : value.toCharArray()) {
            h = 31 * h + c;
        }
        return new HashKey(getType(), h);
    }
}
