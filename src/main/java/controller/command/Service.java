package controller.command;

import controller.TelegramStudyBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Service {
    private static Service service;
    private static TelegramStudyBot studyBot;

    private Service(TelegramStudyBot telegramStudyBot) {
        studyBot = telegramStudyBot;
    }

    public static Service getInstance(TelegramStudyBot telegramStudyBot){
        if (Objects.isNull(service)) {
            service= new Service(telegramStudyBot);
        }
        return service;
    }

    public String getInputData(Update update, boolean checkWithUserText) {
            Boolean readUserText = checkWithUserText && update.hasMessage() && update.getMessage().hasText();
            return (readUserText) ? update.getMessage().getText() : update.getCallbackQuery().getData();

    }

    public <T> Long getChatId(T chatInfo) {
        Long chatId = null;
        if (chatInfo instanceof Update) {
            Update update = (Update) chatInfo;
            Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
            chatId = message.getChatId();
        } else if (chatInfo instanceof Long) {
            chatId = (Long) chatInfo;
        }
        return chatId;
    }

    public String getInputData(Update update) {
        return getInputData(update, false);
    }

    public <T> void sendNew(T update, String text, ReplyKeyboard markup) {
        Long chatId = getChatId(update);
        if (Objects.nonNull(chatId)) {
            sendNewMessage(chatId, text, markup);
        }
    }

    public void sendInstead(Update update, String text, InlineKeyboardMarkup markup) {
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            sendEditMessage(callbackQuery, text, markup);
        }
    }

    private <V extends Serializable, T extends BotApiMethod<V>> void executeSendMessage(T sendMessage) {
        try {
            studyBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendNewMessage(Long chatId, String text, ReplyKeyboard markup) {
        String id = chatId.toString();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(id);
        sendMessage.setParseMode(ParseMode.HTML);
        if (Objects.nonNull(markup)) {
            sendMessage.setReplyMarkup(markup);
        }
        executeSendMessage(sendMessage);
    }

    private void sendEditMessage(CallbackQuery callbackQuery, String text, InlineKeyboardMarkup markup) {
        String chatId = callbackQuery.getMessage().getChatId().toString();
        Integer messageId = callbackQuery.getMessage().getMessageId();
        String inlineMessageId = callbackQuery.getInlineMessageId();
        if (Objects.isNull(markup)) {
            markup = new InlineKeyboardMarkup(new ArrayList<>());
        }
        if (Objects.isNull(text)) {
            sendEditMarkupMessage(chatId, messageId, inlineMessageId, markup);
        } else if (text.isBlank()) {
            deleteMessage(chatId, messageId);
        } else {
            sendEditTextMessage(chatId, messageId, inlineMessageId, text, markup);
        }
    }

    private void sendEditMarkupMessage(String chatId, Integer messageId,
                                       String inlineMessageId, InlineKeyboardMarkup markup) {
        EditMessageReplyMarkup editMessage = EditMessageReplyMarkup.builder()
                .chatId(chatId)
                .messageId(messageId)
                .inlineMessageId(inlineMessageId)
                .replyMarkup(markup)
                .build();
        executeSendMessage(editMessage);
    }

    private void deleteMessage(String chatId, Integer messageId) {
        DeleteMessage deleteMessage = DeleteMessage.builder()
                .chatId(chatId)
                .messageId(messageId)
                .build();
        executeSendMessage(deleteMessage);
    }

    private void sendEditTextMessage(String chatId, Integer messageId,
                                     String inlineMessageId, String text, InlineKeyboardMarkup markup) {
        EditMessageText editMessage = EditMessageText.builder()
                .text(text)
                .chatId(chatId)
                .messageId(messageId)
                .inlineMessageId(inlineMessageId)
                .replyMarkup(markup)
                .build();
        executeSendMessage(editMessage);
    }

}
