package jocketengine.utils;

/**
 * Logger simples e sem dependências, com níveis e marca de tempo.
 * <pre>{@code
 * Logger.info("Engine", "Iniciando...");
 * Logger.setLevel(Logger.Level.WARN); // silencia DEBUG e INFO
 * }</pre>
 *
 * @author Eddch
 */
public final class Logger {

    /** Níveis de severidade, do mais ao menos verboso. */
    public enum Level {
        DEBUG, INFO, WARN, ERROR, OFF
    }

    private static Level level = Level.INFO;

    private Logger() {
    }

    /** Define o nível mínimo exibido. Mensagens abaixo dele são ignoradas. */
    public static void setLevel(Level newLevel) {
        level = newLevel;
    }

    public static void debug(String tag, String message) {
        log(Level.DEBUG, tag, message);
    }

    public static void info(String tag, String message) {
        log(Level.INFO, tag, message);
    }

    public static void warn(String tag, String message) {
        log(Level.WARN, tag, message);
    }

    public static void error(String tag, String message) {
        log(Level.ERROR, tag, message);
    }

    private static void log(Level messageLevel, String tag, String message) {
        if (messageLevel.ordinal() < level.ordinal() || level == Level.OFF) {
            return;
        }
        java.io.PrintStream out = messageLevel == Level.ERROR ? System.err : System.out;
        out.printf("[%tT] %-5s %-12s %s%n", System.currentTimeMillis(), messageLevel, tag, message);
    }
}
