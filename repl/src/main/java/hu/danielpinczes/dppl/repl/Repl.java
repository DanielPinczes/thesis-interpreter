package hu.danielpinczes.dppl.repl;

import hu.danielpinczes.dppl.ast.Program;
import hu.danielpinczes.dppl.evaluator.Evaluator;
import hu.danielpinczes.dppl.lexer.Lexer;
import hu.danielpinczes.dppl.object.Environment;
import hu.danielpinczes.dppl.parser.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.List;

public class Repl {

    private static final String PROMPT = ">> ";

    private static final String LOGO = ",------.  ,------. ,------. ,--.    \n" +
            "|  .-.  \\ |  .--. '|  .--. '|  |    \n" +
            "|  |  \\  :|  '--' ||  '--' ||  |    \n" +
            "|  '--'  /|  | --' |  | --' |  '--. \n" +
            "`-------' `--'     `--'     `-----' ";


    public static void start() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintStream out = System.out;
        Environment env = new Environment();

        while (true) {
            out.print(PROMPT);

            String line;
            try {
                line = reader.readLine();
            } catch (IOException e) {
                return;
            }

            if (line == null || line.equalsIgnoreCase("exit")) {
                return;
            }

            Lexer l = new Lexer(line);
            Parser p = new Parser(l);
            Program program = p.parseProgram();
            Evaluator evaluator = new Evaluator();

            if (!p.getErrors().isEmpty()) {
                printParserErrors(out, p.getErrors());
                continue;
            }

            var evaluated = evaluator.eval(program, env);
            if (evaluated != null) {
                out.println(evaluated.inspect());
            }
        }
    }

    private static void printParserErrors(PrintStream out, List<String> errors) {
        out.println(LOGO);
        out.println("Woops! We ran into some monkey business here!\n parser errors:");
        for (String msg : errors) {
            out.println("\t" + msg);
        }
    }

    public static void main(String[] args) {
        start();
    }
}