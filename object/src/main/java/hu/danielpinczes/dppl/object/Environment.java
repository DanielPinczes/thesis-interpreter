package hu.danielpinczes.dppl.object;

import java.util.HashMap;
import java.util.Map;

public class Environment {
    private Map<String, Object> store;
    private Environment outer;

    public Environment() {
        this.store = new HashMap<>();
        this.outer = null;
    }

    public Environment(Environment outer) {
        this.store = new HashMap<>();
        this.outer = outer;
    }

    public Object get(String name) {
        Object value = store.get(name);
        if (value == null && outer != null) {
            return outer.get(name);
        }
        return value;
    }

    public Object set(String name, Object value) {
        store.put(name, value);
        return value;
    }

    public static Environment newEnclosedEnvironment(Environment outer) {
        return new Environment(outer);
    }
}