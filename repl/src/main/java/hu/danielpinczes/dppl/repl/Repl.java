package hu.danielpinczes.dppl.repl;

import hu.danielpinczes.dppl.lexer.Lexer;
import hu.danielpinczes.dppl.lexer.token.Token;
import hu.danielpinczes.dppl.lexer.token.TokenType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Repl {

    private static final String PROMPT = ">> ";

    public static void main(String[] args) {
        new Repl().start();
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
//           TODO Handle with custom exception
            e.printStackTrace();
        }
    }
}