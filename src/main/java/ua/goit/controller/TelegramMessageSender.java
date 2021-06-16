package ua.goit.controller;

import ua.goit.view.buttons.MenuBlock;

public interface TelegramMessageSender {

    void sendNew(Long chatId, String text, Integer column, MenuBlock... menuBlock);

    void sendNew(Long chatId, String text);

    void sendNewWithReply(Long chatId, String text, Integer column, MenuBlock... menuBlock);

}
