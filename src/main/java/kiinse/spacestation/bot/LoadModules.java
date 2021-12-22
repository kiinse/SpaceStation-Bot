package kiinse.spacestation.bot;
import kiinse.spacestation.api.external.MySQL;
import kiinse.spacestation.api.files.FileManager;
import kiinse.spacestation.bot.bot.Bot;
import kiinse.spacestation.bot.bot.commands.CallBackDataList;
import kiinse.spacestation.bot.bot.commands.CommandsList;
import kiinse.spacestation.bot.utilities.ConsoleColors;
import kiinse.spacestation.bot.utilities.TextFormat;
import kiinse.spacestation.bot.utilities.config.Configuration;
import lombok.extern.slf4j.Slf4j;
import me.tongfei.progressbar.ProgressBar;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
public class LoadModules {

    private final CommandsList commandsList = new CommandsList();
    private final CallBackDataList callBackDataList = new CallBackDataList();
    private final Configuration config = new Configuration();
    private final FileManager fileManager = new FileManager();

    public void loadClasses(ProgressBar progress) {
        if (config.export()) {
            log.error("Создан новый файл конфигурации. Пожалуйста, внесите в него значения.");
            System.exit(0);
        }
        progress.step();
        fileManager.export();
        progress.step();
        MySQL.connect(
                config.getProperty("db.host"),
                config.getProperty("db.port"),
                config.getProperty("db.login"),
                config.getProperty("db.password"),
                config.getProperty("db.name")
        );
        progress.step();
        commandsList.initialize();
        progress.step();
        callBackDataList.initialize();
        progress.step();
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new Bot());
            progress.step();
            try { Thread.sleep(2500); } catch (InterruptedException ignored) {}
            log.info("Бот запущен!");
            System.out.println(ConsoleColors.color(ConsoleColors.Color.GREEN, TextFormat.Emojis.DONE.getValue()));
        } catch (TelegramApiException e) {
            e.printStackTrace();
            log.error("Не удалось инициализировать бота. Производится завершение программы.");
            System.out.println(ConsoleColors.color(ConsoleColors.Color.RED, "Не удалось инициализировать бота. Производится завершение программы."));
            System.exit(1);
        }
    }

}
