package hu.danielpinczes.dppl.antlr;

import hu.danielpinczes.dppl.antlr.DpplGrammarLexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class AntlrParserPerformanceTest {

    @Test
    public void testANTLRParserPerformance() throws IOException {

        CharStream input = CharStreams.fromFileName("src/test/resources/performanceTestInput.dppl");
        // Call your ANTLR parser here
        var lexer = new DpplGrammarLexer(input);
        var tokens = new CommonTokenStream(lexer);
        var parser = new hu.danielpinczes.dppl.antlr.DpplGrammarParser(tokens);

        long startTime = System.nanoTime();
        var program = parser.program();
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("ANTLR Parser Duration: " + duration + " nanoseconds");
    }
}
