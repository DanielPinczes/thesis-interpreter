package hu.danielpinczes.dppl.parser;

public class TraceHelper {
    private static int traceLevel = 0;
    private static final String traceIdentPlaceholder = "\t";

    private static String identLevel() {
        return traceIdentPlaceholder.repeat(traceLevel - 1);
    }

    private static void tracePrint(String fs) {
        System.out.printf("%s%s%n", identLevel(), fs);
    }

    public static void incIdent() {
        traceLevel++;
    }

    public static void decIdent() {
        traceLevel--;
    }

    public static String trace(String msg) {
        incIdent();
        tracePrint("BEGIN " + msg);
        return msg;
    }

    public static void untrace(String msg) {
        tracePrint("END " + msg);
        decIdent();
    }
}