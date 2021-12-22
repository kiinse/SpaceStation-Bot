package kiinse.spacestation.bot.bot.commands;

import kiinse.spacestation.api.external.MySQLUtils;
import kiinse.spacestation.bot.bot.factories.KeyboardsFactory;
import kiinse.spacestation.bot.bot.handlers.CallBackDataHandler;
import kiinse.spacestation.bot.utilities.BotUtils;
import kiinse.spacestation.bot.utilities.config.Configuration;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@Slf4j
public class CallBackDataList {

    private static final BotUtils bot = new BotUtils();
    private static final KeyboardsFactory keyboards = new KeyboardsFactory();
    private static final Commands cmd = new Commands();
    private static final Configuration config = new Configuration();
    private static final CallBackDataHandler callBackData = new CallBackDataHandler();

    public interface Function {
        void run(String chat, int messageID, String messageText);
    }

    private static final HashMap<String, Function> callBackMap = new HashMap<>();

    public void initialize() {
        callBackMap.clear();
        try {

            callBackMap.put("Изменить логин", (chat, messageID, messageText) -> {
                MySQLUtils.setUnspecified(chat);
                bot.editMessageText(
                        chat,
                        messageID,
                        "<b>▬▬▬▬ Регистрация ▬▬▬▬</b>\nПожалуйста, введи логин, который будешь использовать ッ",
                        null
                );
            });

            callBackMap.put("Завершить", (chat, messageID, messageText) -> callBackData.editToSettings(chat, messageID, null));

            callBackMap.put("ПодтвердитьЛогин", (chat, messageID, messageText) -> {
                var login = messageText.split("\n")[3];
                MySQLUtils.updateLogin(chat, login);
                bot.editMessageText(
                        chat,
                        messageID,
                        "<b>▬▬▬▬ Успешно ▬▬▬▬</b>\nТеперь твой логин: " + MySQLUtils.getLogin(chat) + "\n\n",
                        null
                );
                bot.sendMessage(
                        chat,
                        String.format("<b>▬▬▬▬ %s ▬▬▬▬</b>\n" +
                                "Привет, ты попал к игровому боту SpaceStationGame!\n\n → Используй клавиатуру для навигации\n\nСоздатель: @%s", MySQLUtils.getLogin(chat), config.getProperty("admin")),
                        keyboards.Keyboard("Создать карточку игрока🆔", "Создать карточку апокалипсиса🆔", "Настройки⚙"),
                        null
                );
            });

            callBackMap.put("ОтказатьЛогин", (chat, messageID, messageText) -> {
                bot.editMessageText(
                        chat,
                        messageID,
                        "<b>▬▬▬▬ Регистрация ▬▬▬▬</b>\nПожалуйста, введи логин, который будешь использовать ッ",
                        null
                );
            });

        } catch (Exception e) {
            log.warn("Произошла ошибка в инициализации CallBackData HashMap! {}", e.getMessage());
        }
    }

    public void runCallBackMapFunction(String value, String chat, int messageID, String messageText) {
        callBackMap.get(value).run(chat, messageID, messageText);
    }

    public boolean hasCallBackMapValue(String value) {
        return callBackMap.containsKey(value);
    }


}
