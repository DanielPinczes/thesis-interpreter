package hu.danielpinczes.dppl.object;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
class Array implements Object {

    private final List<Object> elements;

    @Override
    public ObjectType getType() {
        return ObjectType.ARRAY_OBJ;
    }

    @Override
    public String inspect() {
        var elems = elements.stream()
                .map(Object::inspect)
                .collect(Collectors.joining(", "));
        return "[" + elems + "]";
    }
}