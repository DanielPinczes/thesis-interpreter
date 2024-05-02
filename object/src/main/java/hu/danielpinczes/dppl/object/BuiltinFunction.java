package hu.danielpinczes.dppl.object;

import java.util.List;

@FunctionalInterface
public interface BuiltinFunction {

    DpplObject apply(List<DpplObject> args);

}
