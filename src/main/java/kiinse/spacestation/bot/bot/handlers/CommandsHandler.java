package kiinse.spacestation.bot.bot.handlers;

import kiinse.spacestation.api.external.MySQLUtils;
import kiinse.spacestation.bot.bot.commands.Commands;
import kiinse.spacestation.bot.bot.commands.CommandsList;
import org.telegram.telegrambots.meta.api.objects.Update;

public class CommandsHandler {

    private static final CommandsList hashMaps = new CommandsList();
    private static final Commands cmd = new Commands();

    public void Command(Update update, String chat, String text) {
        if (! MySQLUtils.hasUserByChat(chat)) {
            cmd.addUser(chat, update);
        } else if (MySQLUtils.isUnspecified(chat)) {
            cmd.setGroup(update);
        } else {
            HashMapCommandsHandler(
                    update,
                    text.toLowerCase().replace("@spacestationgame_bot", "").replace("/", "").replace(" ", ""));
        }
    }

    public void HashMapCommandsHandler(Update update, String command) {
        if (hashMaps.hasCommandMapValue(command)) {
            hashMaps.runCommandMapFunction(command, update);
        }
    }
}
