package ua.goit.controller;

import ua.goit.view.MenuBlock;

public interface TelegramMessageSender {
    void sendNew(Long chatId, String text, int column, MenuBlock... menuBlock);
}