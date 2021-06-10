package ua.goit.service;

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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import ua.goit.util.PropertiesLoader;
import ua.goit.view.Keyboard;
import ua.goit.view.buttons.*;


public class TelegramStudyBot extends TelegramLongPollingBot {

    private Service service;

    public TelegramStudyBot() {
        service = new Service(chatInfo -> {

            sendNew(chatInfo, "Добро пожаловать! Для начала работы с ботом введите электронную почту",
                    Keyboard.inline(Start.values(), 2));

//            String inputText = handleMessageUpdate(chatInfo);
//            sendNew(chatInfo, inputText, null);


            sendNew(chatInfo, "Введите номер группы", null);
            sendNew(chatInfo, "Приветствуем тебя студент!\n" +
                            " Этот бот поможет тебе подготовиться к техническим собеседованиям по вебразработке," +
                            " но прежде тебе нужно выбрать блок изучения\"",
                    Keyboard.inlineWithOptional(OptionalButtons.values(), 2, OptionalButtons.STUDYBLOCK_1));

            sendNew(chatInfo, "Введите время оповещений", Keyboard.reply(NotificationTime.values(), 3));
            sendNew(chatInfo, "Для перехода к следующему вопросу нажмите кнопку далее", Keyboard.inline(Next.values(), 2));
            sendNew(chatInfo, "Готов ли ты продолжить обучение?", Keyboard.inline(YesNo.values(), 2));

        });
    }

    public String getInputData(Update update, boolean checkWithUserText) {
        Boolean readUserText = checkWithUserText && update.hasMessage() && update.getMessage().hasText();
        return (readUserText) ? update.getMessage().getText() : update.getCallbackQuery().getData();

    }

    public <T> Long getChatId(T chatInfo) {
        if (chatInfo instanceof Update) {
            Update update = (Update) chatInfo;
            Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
            return message.getChatId();
        }
        if (chatInfo instanceof Long) return (Long) chatInfo;
        return null;
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

    @SneakyThrows
    private <V extends Serializable, T extends BotApiMethod<V>> void executeSendMessage(T sendMessage) {
        execute(sendMessage);
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
        } else if (text.isEmpty()) {
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

    @Override
    public String getBotUsername() {
        return PropertiesLoader.getProperty("telegram.bot.name");
    }

    @Override
    public String getBotToken() {
        return PropertiesLoader.getProperty("telegram.bot.token");
    }

    @Override
    public void onUpdateReceived(Update update) {
        String input = null;
        if (update.hasMessage() && update.getMessage().hasText()) {
            input = update.getMessage().getText();
        } else if (update.hasCallbackQuery()) {
            input = update.getCallbackQuery().getData();
        }
        service.call(input, update);
    }

}
