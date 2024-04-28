package hu.danielpinczes.dppl.lexer.automata;

import java.util.ArrayList;
import java.util.List;

/**
 * State in a finite state machine.
 */
public class State {

    private final List<Transition> transitions;
    private final boolean isFinal;

    public State() {
        this(false);
    }

    public State(final boolean isFinal) {
        this.transitions = new ArrayList<>();
        this.isFinal = isFinal;
    }

    public State getNextState(final CharSequence c) {
        return transitions
                .stream()
                .filter(t -> t.matchesRule(c))
                .map(Transition::state)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Input not accepted: " + c));
    }

    public boolean isFinal() {
        return this.isFinal;
    }

    public State addTransition(Transition tr) {
        this.transitions.add(tr);
        return this;
    }
}
