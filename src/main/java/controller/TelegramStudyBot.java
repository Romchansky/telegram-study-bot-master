package controller;

import controller.command.Commandor;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import utils.PropertiesLoader;

public class TelegramStudyBot extends TelegramLongPollingBot {

    private final Commandor commandor;
    private final PropertiesLoader propertiesLoader = new PropertiesLoader();

    public TelegramStudyBot() {
        this.commandor = Commandor.getInstance(this); //&&&
    }

    @Override
    public String getBotUsername() {
        return propertiesLoader.getProperty("telegram.bot.name");
    }

    @Override
    public String getBotToken() {
        return propertiesLoader.getProperty("telegram.bot.token");
    }


    @Override
    public void onUpdateReceived(Update update) {
        String input = null;
        if (update.hasMessage() && update.getMessage().hasText()) {
            input = update.getMessage().getText();
        } else if (update.hasCallbackQuery()) {
            input = update.getCallbackQuery().getData();
        }
        commandor.call(input, update);
    }
}
