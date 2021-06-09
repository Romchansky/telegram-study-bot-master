package controller.command;

import controller.TelegramStudyBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import user.service.StudyBlockState;
import user.service.StudyBlockStateStorage;
import user.service.UserSettings;
import user.service.UserSettingsStorage;
import view.Keyboard;
import view.menu.MenuBlock;
import view.menu.Start;

import java.util.List;
import java.util.function.Consumer;

public class Actions {

    private static Service service;
    private static UserProcessor processor;
    private static UserSettingsStorage settingsStorage;
    private static StudyBlockStateStorage studyBlockStateStorage = StudyBlockStateStorage.getStorage();


    public Actions(TelegramStudyBot telegramStudyBot) {
        service = Service.getInstance(telegramStudyBot);
        processor = UserProcessor.getInstance(service);

    }

    protected Consumer<Update> start = (chatInfo) -> {
        UserSettings user = processor.getUser(chatInfo);
        String text = "Добро пожаловать! \n Для начала работы, тебе нужно зарегестрироваться ";
        MenuBlock[] menu = Start.values();
        System.out.println(user.toString()); // вывод юзера
        InlineKeyboardMarkup markup = Keyboard.inline(menu, 1);
        service.sendNew(chatInfo, text, markup);
    };


    protected Consumer<Update> registration = (chatInfo) -> {
        processor.getUser(chatInfo);
        processor.getButtonValue(chatInfo, "/registration");
        String text = "Введите электронную почту: ";
        service.sendNew(chatInfo, text);
    };
}
