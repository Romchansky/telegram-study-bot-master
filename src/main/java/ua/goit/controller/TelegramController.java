package ua.goit.controller;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.io.Serializable;
import java.util.Objects;

import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import ua.goit.service.UserStudyBlock;
import ua.goit.util.PropertiesLoader;
import ua.goit.service.UserRegistrationService;
import ua.goit.view.Keyboard;
import ua.goit.view.buttons.MenuBlock;

@Slf4j
public class TelegramController extends TelegramLongPollingBot implements TelegramMessageSender {

    private final UserRegistrationService userRegistration;
    private final UserStudyBlock userStudyBlock;

    public TelegramController() {
        this.userStudyBlock = UserStudyBlock.of();
        this.userRegistration = UserRegistrationService.of();
    }

    @Override
    public String getBotUsername() {
        return PropertiesLoader.getProperty("telegram.bot.name");
    }

    @Override
    public String getBotToken() {
        return PropertiesLoader.getProperty("telegram.bot.token");
    }

    @SneakyThrows
    private <V extends Serializable, T extends BotApiMethod<V>> V executeSendMessage(T sendMessage) {
        return super.execute(sendMessage);
    }

    @Override
    public void sendNew(Long chatId, String text, Integer column, MenuBlock... menuBlock) {
        if (Objects.nonNull(chatId)) {
            sendNewMessage(chatId, text, Keyboard.inline(column, menuBlock));
        }
    }

    @Override
    public void sendNew(Long chatId, String text) {
        if (Objects.nonNull(chatId)) {
            sendNewMessage(chatId, text, null);
        }
    }

    @Override
    public void sendNewWithReply(Long chatId, String text, Integer column, MenuBlock... menuBlock) {
        if (Objects.nonNull(chatId)) {
            sendNewMessage(chatId, text, Keyboard.reply(3, menuBlock));
        }
    }

    private Message sendNewMessage(Long chatId, String text, ReplyKeyboard markup) {
        String id = chatId.toString();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(id);
        sendMessage.setParseMode(ParseMode.HTML);
        if (Objects.nonNull(markup)) sendMessage.setReplyMarkup(markup);
        return executeSendMessage(sendMessage);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().getText().startsWith("/start")) {
            userRegistration.execute(update.getMessage().getChatId(), null, this);
        } else if (update.hasMessage() && update.getMessage().hasText()) {
            userRegistration.execute(update.getMessage().getChatId(), update.getMessage().getText(), this);
        }
        if (update.hasCallbackQuery()) {
            userStudyBlock.execute(update.getCallbackQuery().getFrom().getId(), update.getCallbackQuery().getData(), this);

        }
    }
}
