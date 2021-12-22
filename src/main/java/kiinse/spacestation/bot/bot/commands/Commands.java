package kiinse.spacestation.bot.bot.commands;

import kiinse.spacestation.api.external.MySQLUtils;
import kiinse.spacestation.api.game.Cards;
import kiinse.spacestation.bot.bot.factories.KeyboardsFactory;
import kiinse.spacestation.bot.utilities.BotUtils;
import kiinse.spacestation.bot.utilities.TextFormat;
import kiinse.spacestation.bot.utilities.config.Configuration;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Commands {

    private static final Configuration config = new Configuration();
    private static final KeyboardsFactory keyboards = new KeyboardsFactory();
    private static final BotUtils bot = new BotUtils();
    private static final Cards cards = new Cards();

    public void addUser(String chat, Update update) {
        bot.sendMessage(
                chat,
                String.format("<b>▬▬▬▬ %s ▬▬▬▬</b>\n" +
                        "Привет, ты попал к игровому боту SpaceStationGame!\nСоздатель: @%s", update.getMessage().getFrom().getFirstName(), config.getProperty("admin")),
                null,
                null
        );
        MySQLUtils.addUser(chat, update.getMessage().getFrom().getUserName() != null ? update.getMessage().getFrom().getUserName() : "НЕ УКАЗАНО");
        bot.sendMessage(
                chat,
                "<b>▬▬▬▬ Регистрация ▬▬▬▬</b>\nПожалуйста, введи логин, который будешь использовать ッ",
                null,
                null
        );
    }

    public void settingsButton(Update update) {
        var chat = update.getMessage().getChatId().toString();
        bot.sendMessage(
                chat,
                String.format("▬▬▬▬ Настройки ▬▬▬▬\n" +
                        "\n" +
                        " → Логин: %s\n" +
                        "\n" +
                        "♢Версия бота: %s\n" +
                        "♢Версия API: %s",MySQLUtils.getLogin(chat), config.getVersion(Configuration.Version.BOT), config.getVersion(Configuration.Version.API)),
                null,
                keyboards.settings());
    }

    public void setGroup(Update update) {
        var chat = update.getMessage().getChatId().toString();
        bot.sendMessage(
                chat,
                "<b>▬▬▬▬ Регистрация ▬▬▬▬</b>\nВаш новый логин:\n\n" + update.getMessage().getText() + "\n\n → Вы согласны?",
                null,
                keyboards.inlineConstructor("Да" + TextFormat.Emojis.SUCCESS.getValue(), "ПодтвердитьЛогин", "Нет" + TextFormat.Emojis.ERROR.getValue(), "ОтказатьЛогин")
        );
    }

    public void startButton(Update update) {
        var chat = update.getMessage().getChatId().toString();
        bot.sendMessage(
                chat,
                String.format("<b>▬▬▬▬ %s ▬▬▬▬</b>\n" +
                        "Привет, ты попал к игровому боту SpaceStationGame!\n\n → Используй клавиатуру для навигации\n\nСоздатель: @%s", MySQLUtils.getLogin(chat), config.getProperty("admin")),
                keyboards.Keyboard("Создать карточку игрока🆔", "Создать карточку апокалипсиса🆔", "Настройки⚙"),
                null
        );
    }

    public void generatePlayerCard(Update update) {
        var chat = update.getMessage().getChatId().toString();
        bot.sendMessage(
                chat,
                cards.formatPlayerCard(MySQLUtils.getLogin(chat), cards.generatePlayerCardJson()),
                null,
                null
        );
    }

    public void generateApocalypseCard(Update update) {
        var chat = update.getMessage().getChatId().toString();
        bot.sendMessage(
                chat,
                "<b>▬▬▬▬ Ошибка ▬▬▬▬</b>\n" + TextFormat.Emojis.WARNING.getValue() + "К сожалению данная функция ещё в стадии разработки...",
                null,
                null
        );
    }
}
