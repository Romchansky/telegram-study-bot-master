package controller.command;

import controller.TelegramStudyBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import user.service.UserSettings;
import view.Keyboard;
import view.menu.MenuBlock;
import view.menu.Start;

import java.util.function.Consumer;

public class Actions {
    private static Service service;
    private static UserProcessor processor;
    private static UserSettings settings;

    public Actions(TelegramStudyBot telegramStudyBot) {
        service = Service.getInstance(telegramStudyBot);
        processor = UserProcessor.getInstance(service);
    }

    protected Consumer<Update> start = (chatInfo) -> {
        UserSettings user = processor.getUser(chatInfo);
        String text = "Добро пожаловать! Для начала работы зарегестрируйтесь: ";
        MenuBlock[] menu = Start.values();
        System.out.println(user.toString());
        InlineKeyboardMarkup markup = Keyboard.inline(menu, 1);
        service.sendNew(chatInfo, text, markup);
    };


    protected Consumer<Update> registration = (chatInfo) -> {

        String input = processor.getButtonValue(chatInfo, "/registration");
        String text = "Введите электронную почту: ";
        service.sendNew(chatInfo, text);
    };
}
