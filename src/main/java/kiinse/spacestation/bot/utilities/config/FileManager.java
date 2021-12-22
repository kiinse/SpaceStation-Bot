package kiinse.spacestation.bot.utilities.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class FileManager {

    public static InputStream accessFile(String resource) {
        InputStream input = FileManager.class.getResourceAsStream(File.separator + "resources" + File.separator + resource);
        if (input == null) {
            input = FileManager.class.getClassLoader().getResourceAsStream(resource);
        }
        return input;
    }

    public static boolean copyFile(InputStream sourceFile, File destFile) {
        try {
            if (! destFile.exists()) {
                FileUtils.copyInputStreamToFile(sourceFile, destFile);
                return true;
            }
        } catch (IOException e) {
            log.warn("Произошла ошибка при копировании файлов: {}", e.getMessage());
        }
        return false;
    }
}
