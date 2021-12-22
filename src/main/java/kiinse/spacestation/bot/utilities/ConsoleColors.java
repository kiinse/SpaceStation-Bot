package kiinse.spacestation.bot.utilities;

public class ConsoleColors {

    public enum Color {
        RED,
        GREEN,
        YELLOW
    }

    public static String color(Color color, String text){
        return switch (color) {
            case RED -> String.format("\u001B[31m%s\u001B[0m", text);
            case GREEN -> String.format("\u001B[32m%s\u001B[0m", text);
            case YELLOW -> String.format("\\u001b[33m%s\u001B[0m", text);
        };
    }
}