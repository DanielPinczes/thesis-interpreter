package hu.danielpinczes.dppl.repl;

import hu.danielpinczes.dppl.ast.Program;
import hu.danielpinczes.dppl.evaluator.Evaluator;
import hu.danielpinczes.dppl.lexer.Lexer;
import hu.danielpinczes.dppl.lexer.token.TokenType;
import hu.danielpinczes.dppl.object.Environment;
import hu.danielpinczes.dppl.parser.Parser;

import java.io.*;
import java.util.List;

public class ReplForLexer {

    private static final String PROMPT = ">> ";

    public static void main(String[] args) {
        new ReplForLexer().start();
    }

    public void start() {
        try (var reader = new BufferedReader(new InputStreamReader(System.in));
             var writer = new PrintWriter(System.out, true)) {
            String line;
            while (true) {
                writer.print(PROMPT);
                writer.flush();
                line = reader.readLine();
                if (line == null || line.equalsIgnoreCase("exit")) {
                    break;
                }

                var lexer = new Lexer(line);
                for (var token = lexer.nextToken(); token.type() != TokenType.EOF; token = lexer.nextToken()) {
                    writer.println(token);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}