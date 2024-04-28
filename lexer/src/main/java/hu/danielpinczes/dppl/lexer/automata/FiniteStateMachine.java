package hu.danielpinczes.dppl.lexer.automata;

/**
 * Default implementation of a finite state machine.
 * This class is immutable and thread-safe.
 */
public class FiniteStateMachine {


    /**
     * Current state.
     */
    private final State current;

    /**
     * Ctor.
     * @param initial Initial state of this machine.
     */
    public FiniteStateMachine(final State initial) {
        this.current = initial;
    }

    public FiniteStateMachine createNextState(final CharSequence c) {
        return new FiniteStateMachine(this.current.getNextState(c));
    }

    public boolean isFinalState() {
        return this.current.isFinal();
    }
}
