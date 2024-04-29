package hu.danielpinczes.dppl.object;

import java.util.List;

@FunctionalInterface
public interface BuiltinFunction {

    Object apply(List<Object> args);

}
