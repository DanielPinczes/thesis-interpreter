package hu.danielpinczes.dppl.object;

import java.util.HashMap;
import java.util.Map;

public class Environment {
    private Map<String, DpplObject> store;
    private Environment outer;

    public Environment() {
        this.store = new HashMap<>();
        this.outer = null;
    }

    public Environment(Environment outer) {
        this.store = new HashMap<>();
        this.outer = outer;
    }

    public DpplObject get(String name) {
        DpplObject value = store.get(name);
        if (value == null && outer != null) {
            return outer.get(name);
        }
        return value;
    }

    public DpplObject set(String name, DpplObject value) {
        store.put(name, value);
        return value;
    }

    public static Environment newEnclosedEnvironment(Environment outer) {
        return new Environment(outer);
    }
}