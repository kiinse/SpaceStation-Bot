package kiinse.spacestation.bot.utilities.antispam;

import kiinse.spacestation.bot.utilities.BotUtils;

import java.util.Date;
import java.util.HashMap;

public class AntiSpam {

    private final BotUtils bot = new BotUtils();

    public enum Settings {
        MESSAGE
    }

    private static HashMap<Long, Long> antiSpam = new HashMap<>();

    private static HashMap<Long, Integer> banCount = new HashMap<>();

    public boolean check(Long chat, Settings settings) {
        if (! antiSpam.containsKey(chat)) {
            antiSpam.put(chat, new Date().getTime()-1000);
            banCount.put(chat, 0);
        }


        if ((new Date().getTime() - antiSpam.get(chat)) >= 1000) {
            antiSpam.replace(chat, new Date().getTime());
            banCount.replace(chat, 0);
            return true;
        } else {
            antiSpam.replace(chat, new Date().getTime());
            banCount.replace(chat, banCount.get(chat)+1);
            if (settings.equals(Settings.MESSAGE)) {
                new Thread(() ->
                        bot.sendMessage(
                                chat.toString(),
                                "⚠️Вы пишите слишком часто!",
                                null,
                                null
                        )
                ).start();
            }
            return false;
        }
    }

}
