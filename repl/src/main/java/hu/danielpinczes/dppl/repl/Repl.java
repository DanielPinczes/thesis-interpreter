package hu.danielpinczes.dppl.repl;

import hu.danielpinczes.dppl.ast.Program;
import hu.danielpinczes.dppl.lexer.Lexer;
import hu.danielpinczes.dppl.parser.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.List;

public class Repl {

    private static final String PROMPT = ">> ";

    public static void start() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintStream out = System.out;

        while (true) {
            out.print(PROMPT);

            String line;
            try {
                line = reader.readLine();
                if (line == null || line.equalsIgnoreCase("exit")) {
                    break;
                }
            } catch (IOException e) {
                out.println("Failed to read input: " + e.getMessage());
                continue;
            }

            Lexer lexer = new Lexer(line);
            Parser parser = new Parser(lexer);

            Program program = parser.parseProgram();
            if (!parser.getErrors().isEmpty()) {
                printParserErrors(out, parser.getErrors());
                continue;
            }

            out.println(program.toString());
        }
    }

    private static void printParserErrors(PrintStream out, List<String> errors) {
        out.println("Parsing error found!\n parser errors:");
        for (String msg : errors) {
            out.println("\t" + msg);
        }
    }

    public static void main(String[] args) {
        start();
    }
}