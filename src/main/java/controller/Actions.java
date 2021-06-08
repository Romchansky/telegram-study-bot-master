package controller;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import view.Keyboard;
import view.menu.MenuBlock;
import view.menu.Start;

import java.util.function.Consumer;

public class Actions {
    private static Service service;

    public Actions(TelegramStudyBot telegramStudyBot) {
        service = Service.getInstance(telegramStudyBot);
    }

    protected Consumer<Update> start = (chatInfo) -> {
       // process.getUser(chatInfo);
        String text = "Добро пожаловать! Для начала работы с ботом введите электронную почту: ";
        MenuBlock[] menu = Start.values();
        InlineKeyboardMarkup markup = Keyboard.inline(menu, 2);
        service.sendNew(chatInfo, text, markup);
    };
}
