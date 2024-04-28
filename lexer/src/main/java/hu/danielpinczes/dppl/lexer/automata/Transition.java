package hu.danielpinczes.dppl.lexer.automata;

/**
 * Transition in finite state machine.
 */
public class Transition {

    private final String rule;
    private final State next;

    /**
     * Ctor.
     * @param rule Rule that a character has to meet
     *  in order to get to the next state.
     * @param next Next state.
     */
    public Transition(String rule, State next) {
        this.rule = rule;
        this.next = next;
    }

    public State state() {
        return this.next;
    }

    public boolean matchesRule(CharSequence c) {
        return this.rule.equalsIgnoreCase(String.valueOf(c));
    }
}
