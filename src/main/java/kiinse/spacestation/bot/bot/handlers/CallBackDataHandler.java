package kiinse.spacestation.bot.bot.handlers;

import kiinse.spacestation.api.external.MySQLUtils;
import kiinse.spacestation.bot.bot.commands.CallBackDataList;
import kiinse.spacestation.bot.utilities.BotUtils;
import kiinse.spacestation.bot.utilities.config.Configuration;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public class CallBackDataHandler {

    private static final BotUtils bot = new BotUtils();
    private static final Configuration config = new Configuration();
    private static final CallBackDataList callBackDataList = new CallBackDataList();

    public void callBackDataHandler(Update update) {
        var chat = update.getCallbackQuery().getMessage().getChatId().toString();
        var text = update.getCallbackQuery().getMessage().getText();
        if (MySQLUtils.hasUserByChat(chat) && !MySQLUtils.isUnspecified(chat)) {
            var messageID = update.getCallbackQuery().getMessage().getMessageId();
            if (callBackDataList.hasCallBackMapValue(update.getCallbackQuery().getData())) {
                callBackDataList.runCallBackMapFunction(update.getCallbackQuery().getData(), chat, messageID, text);
            }
        }
    }

    public void editToSettings(String chat, int message_id, InlineKeyboardMarkup keyboard) {
        bot.editMessageText(
                chat,
                message_id,
                String.format("▬▬▬▬ Настройки ▬▬▬▬\n" +
                        "\n" +
                        " →Логин: %s\n" +
                        "\n" +
                        "♢Версия бота: %s\n" +
                        "♢Версия API: %s",MySQLUtils.getLogin(chat), config.getVersion(Configuration.Version.BOT), config.getVersion(Configuration.Version.API)),
                keyboard
        );
    }
}
