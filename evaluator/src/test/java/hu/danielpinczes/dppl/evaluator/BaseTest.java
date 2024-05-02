package hu.danielpinczes.dppl.evaluator;

import hu.danielpinczes.dppl.ast.Program;
import hu.danielpinczes.dppl.lexer.Lexer;
import hu.danielpinczes.dppl.object.DpplObject;
import hu.danielpinczes.dppl.object.Environment;
import hu.danielpinczes.dppl.parser.Parser;

public abstract class BaseTest  {

    public abstract void testAll();

    protected DpplObject testEval(String input) {
        Lexer lexer = new Lexer(input);
        Parser parser = new Parser(lexer);
        Program program = parser.parseProgram();
        Environment env = new Environment();
        Evaluator evaluator = new Evaluator();
        return evaluator.eval(program, env);
    }
}
