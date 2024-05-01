package hu.danielpinczes.dppl.object;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class HashPair {

    private final Object key;
    private final Object value;
}