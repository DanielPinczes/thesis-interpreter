package hu.danielpinczes.dppl.object;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class ArrayObject implements DpplObject {

    private final List<DpplObject> elements;

    @Override
    public ObjectType getType() {
        return ObjectType.ARRAY_OBJ;
    }

    @Override
    public String inspect() {
        var elems = elements.stream()
                .map(DpplObject::inspect)
                .collect(Collectors.joining(", "));
        return "[" + elems + "]";
    }
}