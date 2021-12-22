package kiinse.spacestation.bot;

import me.tongfei.progressbar.ProgressBar;

public class Main {
    public static void main(String[] args) {
        try (var progress = new ProgressBar("Запуск...", 6)) {
            new LoadModules().loadClasses(progress);
        }
    }
}
