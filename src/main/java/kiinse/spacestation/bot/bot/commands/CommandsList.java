package kiinse.spacestation.bot.bot.commands;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.util.HashMap;

@Slf4j
public class CommandsList {

    public interface Function {
        void run(Update update);
    }

    private static final HashMap<String, Function> commandMap = new HashMap<>();

    public void initialize() {
        var cmd = new Commands();
        commandMap.clear();
        try {
            commandMap.put("start", cmd::startButton);
            commandMap.put("домой🏠", cmd::startButton);
            commandMap.put("настройки⚙", cmd::settingsButton);
            commandMap.put("создатькарточкуигрока🆔", cmd::generatePlayerCard);
            commandMap.put("создатькарточкуапокалипсиса🆔", cmd::generateApocalypseCard);
        } catch (Exception e) {
            log.warn("Произошла ошибка в инициализации команд HashMap! {}", e.getMessage());
        }
    }

    public void runCommandMapFunction(String value, Update update) {
        commandMap.get(value).run(update);
    }

    public boolean hasCommandMapValue(String value) {
        return commandMap.containsKey(value);
    }

}

