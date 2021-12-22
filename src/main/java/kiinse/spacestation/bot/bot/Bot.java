package kiinse.spacestation.bot.bot;

import kiinse.spacestation.api.external.MySQLUtils;
import kiinse.spacestation.bot.bot.handlers.CallBackDataHandler;
import kiinse.spacestation.bot.bot.handlers.CommandsHandler;
import kiinse.spacestation.bot.utilities.BotUtils;
import kiinse.spacestation.bot.utilities.antispam.AntiSpam;
import kiinse.spacestation.bot.utilities.config.Configuration;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Bot extends TelegramLongPollingBot {

    private static final Configuration config = new Configuration();
    private static final CommandsHandler commands = new CommandsHandler();
    private static final CallBackDataHandler callBackData = new CallBackDataHandler();
    private static final BotUtils bot = new BotUtils();
    private static final AntiSpam antiSpam = new AntiSpam();

    @Override
    public void onUpdateReceived(Update update) {
        var chat = update.hasCallbackQuery() ? update.getCallbackQuery().getMessage().getChatId() : update.getMessage().getChatId();
        var username = update.hasCallbackQuery() ? update.getCallbackQuery().getFrom().getUserName() : update.getMessage().getFrom().getUserName();
        if (antiSpam.check(chat, AntiSpam.Settings.MESSAGE)) {
            if (update.hasMessage()) {
                new Thread(() -> commands.Command(update, update.getMessage().getChatId().toString(), update.getMessage().getText())).start();
                new Thread(() -> {
                    var finalUserName = username != null ? username : "НЕ УКАЗАНО";
                    if (MySQLUtils.hasUserByChat(chat.toString()) && !MySQLUtils.getUsername(chat.toString()).equals(finalUserName)) {
                        MySQLUtils.updateUserName(chat.toString(), finalUserName);
                    }
                }).start();
            } else {
                new Thread(() -> callBackData.callBackDataHandler(update)).start();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return config.getProperty("bot.name");
    }

    @Override
    public String getBotToken() {
        return config.getProperty("bot.token");
    }
}


