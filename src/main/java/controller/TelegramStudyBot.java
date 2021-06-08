package controller;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TelegramStudyBot extends TelegramLongPollingBot {

    private final Commandor commandor;

    public TelegramStudyBot() {
        this.commandor = Commandor.getInstance(this);

    }

    @Override
    public String getBotUsername() {
        return new PropertiesLoader().getProperty("botName");
    }

    @Override
    public String getBotToken() {
        return new PropertiesLoader().getProperty("botToken");
    }


    @Override
    public void onUpdateReceived(Update update) {
        String input = null;
        if(update.hasMessage() && update.getMessage().hasText()) {
            input = update.getMessage().getText();
        } else if (update.hasCallbackQuery()) {
            input = update.getCallbackQuery().getData();
        }
        commandor.call(input, update);
    }
}
