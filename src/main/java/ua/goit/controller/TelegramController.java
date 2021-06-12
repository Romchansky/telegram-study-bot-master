package ua.goit.controller;

import lombok.extern.slf4j.Slf4j;
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
import ua.goit.service.NotificationService;
import ua.goit.util.MailValidator;
import ua.goit.util.PropertiesLoader;
import ua.goit.view.Keyboard;
import ua.goit.view.MenuBlock;
import ua.goit.view.buttons.KeyboardButtons;


@Slf4j
public class TelegramController extends TelegramLongPollingBot  implements TelegramMessageSender{

    private MailValidator mailValidator;

    private final NotificationService notificationService = NotificationService.of();

    @Override
    public void sendNew(Long chatId, String text, int column, MenuBlock ... menuBlock) {
        if (Objects.nonNull(chatId)) sendNewMessage(chatId, text, Keyboard.inline(menuBlock, column));
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



    boolean isValid = false;

    @Override
    public void onUpdateReceived(Update update) {
        log.info("OnUpdateReceived : " + update);

        String inputText = update.getMessage().getText();

        if (inputText.startsWith("/start")) {
            callStartMessage(update);
        } else if (update.hasMessage() && update.getMessage().hasText()) {
           log.info("is valid : " + isValid);
            if(isValid == false) {
                isValid = callValidEmail(update);
            }else{
                callWriteGroup(update);
            }
        } else if (update.hasCallbackQuery()) {
            handleCallbackQueryUpdate(update);
        }

//        if (update.hasMessage() && update.getMessage().hasText() && ("/start".equals(update.getMessage().getText()))) {
//            callStartMessage(update);

    }

    private void callStartMessage(Update update) {
        sendNewMessage(update.getMessage().getChatId(),
                "Добро пожаловать! Для начала работы с ботом введите электронную почту ", null);
    }

    //класс который следит за состоянием бота и меняет
    private boolean callValidEmail(Update update) {
        MailValidator validator = new MailValidator();
        String messageText = update.getMessage().getText();
        boolean valid = validator.valid(messageText);
        if (valid) {
            log.info("valid is complete: " + valid);
            return true;
        } else {
            log.info("valid is not valid : " + valid);
            sendNewMessage(update.getMessage().getChatId(), "Введите почту ещё раз: ", null);
            validator.valid(messageText);
            return false;
        }
    }

    private void callWriteGroup(Update update) {
        sendNewMessage(update.getMessage().getChatId(), "Введите номер группы: ", null);
        String nameGroup = update.getMessage().getText();
        System.out.println(nameGroup);
        sendNewMessage(update.getMessage().getChatId(), "\"Приветствуем тебя студент!" +
                " Этот бот поможет тебе подготовиться к техническим собеседованиям по вебразработке, " +
                "но прежде тебе нужно выбрать блок изучения", Keyboard.inlineDynamic());
    }

    //не работают команды по условию
    private void handleCallbackQueryUpdate(Update update) {

        String callbackQuery = update.getCallbackQuery().getData();
        String text = update.getCallbackQuery().getMessage().getText();
        System.out.println(callbackQuery + " " + text);

        switch (callbackQuery) {
//            case "Далее":
//                new User().getId();
//                break;
            case "js":

                // new <hnml> StudyBlock().getCourse();
                break;
            case "JavaScript":
                // new  User().createNewUser();
                break;
            case "React":
                // new User().createNewUser();
                break;
        }
        Long chatId = update.getCallbackQuery().getFrom().getId();

    }



}






