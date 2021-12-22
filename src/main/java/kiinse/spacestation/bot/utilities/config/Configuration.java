package kiinse.spacestation.bot.utilities.config;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Slf4j
public class Configuration {

    public enum Version {
        BOT,
        API
    }

    public boolean export() {
        return BotFileManager.copyFile(BotFileManager.accessFile("config.properties"), new File("SpaceStationFiles" + File.separator + "config.properties"));
    }

    public String getProperty(String key){
        try {
            var property = new Properties();
            property.load(new FileInputStream("SpaceStationFiles/config.properties"));
            return property.getProperty(key);
        } catch (IOException e) {
            log.error("Произошла ошибка при получении property: {}", e.getMessage());
        }
        return null;
    }

    public String getVersion(Version version){
        try {
            var info = BotFileManager.accessFile("info.properties");
            var property = new Properties();
            property.load(info);
            switch (version) {
                case BOT -> {
                    return property.getProperty("bot.version");
                }
                case API -> {
                    return property.getProperty("api.version");
                }
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при получении версии: {}", e.getMessage());
        }
        return null;
    }
}
