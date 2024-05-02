package hu.danielpinczes.dppl.object;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class HashObject implements DpplObject {

    private final Map<HashKey, HashPair> pairs;

    @Override
    public ObjectType getType() {
        return ObjectType.HASH_OBJ;
    }

    @Override
    public String inspect() {
        var pairsString = pairs.values().stream()
                .map(e -> e.getKey().inspect() + ": " + e.getValue().inspect())
                .collect(Collectors.joining(", "));
        return "{" + pairsString + "}";
    }
}
