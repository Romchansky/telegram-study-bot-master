package ua.goit;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ua.goit.controller.GoogleSheetsController;
import ua.goit.controller.TelegramController;
import ua.goit.service.UpdateGoogleSheetsService;

public class Application {

    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(new TelegramController());
        new UpdateGoogleSheetsService(new GoogleSheetsController()).startScheduler();
    }
}
