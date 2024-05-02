package hu.danielpinczes.dppl.object;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@Getter
@RequiredArgsConstructor
public class HashKey {

    private final ObjectType type;
    private final long value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HashKey hashKey = (HashKey) o;
        return value == hashKey.value && type == hashKey.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value);
    }
}