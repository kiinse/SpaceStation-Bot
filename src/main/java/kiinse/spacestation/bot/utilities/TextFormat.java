package kiinse.spacestation.bot.utilities;

public class TextFormat {

    public enum Emojis {
        SUCCESS("✅"),
        ERROR("❌"),
        QUESTION("❔"),
        WARNING("⚠️"),
        CALENDAR("📅"),
        SETTINGS("⚙"),
        SCHEDULE("📰"),
        CHANGES("📝"),
        HOME("🏠"),
        MAIL("✉"),
        GROUP("👥"),
        ID("🆔"),
        TM("™"),
        MESSAGE("💬"),
        DONE("\nБот инициализирован!\n\nСоздатель бота: kiinse\nGitHub: https://github.com/kiinse");

        private String value;

        public String getValue() {
            return value;
        }

        Emojis(String value) {
            this.value = value;
        }
    }

    public static String EmojiBoolean(boolean bool) {
        return bool ? Emojis.SUCCESS.getValue() : Emojis.ERROR.getValue();
    }
}
