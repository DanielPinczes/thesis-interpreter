package hu.danielpinczes.dppl.lexer.automata;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Transition {

    private final String rule;
    private final State next;

    public State state() {
        return this.next;
    }

    public boolean matchesRule(CharSequence c) {
        return this.rule.equalsIgnoreCase(String.valueOf(c));
    }
}
