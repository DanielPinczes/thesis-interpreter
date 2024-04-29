package hu.danielpinczes.dppl.object;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
class Hash implements Object {

    private final Map<HashKey, HashPair> pairs;

    public Hash() {
        this.pairs = new HashMap<>();
    }

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
