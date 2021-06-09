import utils.PropertiesLoader;
import controller.TelegramStudyBot;
import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Application {

    @SneakyThrows
    public static void main(String[] args) {

       new PropertiesLoader().loadPropertiesFile("application.properties");

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(new TelegramStudyBot());

    }
}
