package hu.danielpinczes.dppl.lexer.automata;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FiniteStateMachine {
    
    private final State current;
    public FiniteStateMachine createNextState(final CharSequence c) {
        return new FiniteStateMachine(this.current.getNextState(c));
    }

    public boolean isFinalState() {
        return this.current.isFinal();
    }
}
