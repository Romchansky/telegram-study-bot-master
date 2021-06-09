package controller.command;

import controller.TelegramStudyBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import user.UserProcessor;
import user.service.StudyBlockState;
import user.service.StudyBlockStateStorage;
import user.service.UserSettings;
import user.service.UserSettingsStorage;
import view.Keyboard;
import view.menu.*;

import java.time.LocalTime;
import java.util.Objects;
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

//        String email = service.getInputData(chatInfo);
//        boolean emailIsValid = settingsStorage.validateEmail(email);
//        if (emailIsValid) {
//            service.sendNew(chatInfo, "Совершенно верно!");
//
//            service.sendNew(chatInfo, "Введите номер группы! ");
//        } else {
//            service.sendNew(chatInfo, "Вы ошиблись, попробуйте ещё раз!");
//        }

    };


//    private MenuBlock[] getInitialMenu(){
//
//        List<String> blockNames = studyBlockService.getStudyBlockNAmes();
//        MenuBlock[] menu = new MenuBlock[blockNames.size() + 1];
//
//        for (int i  = 0; i < blockNames.size(); i++) {
//            menu[i] = new StudyButton(blockNames.get(i), "/next_", blockNames.get(i));
//        }
//        menu[blockNames.size()] = Start.SETTINGS;
//    return menu;
//    }


    protected Consumer<Update> next = (chatInfo) -> {
        UserSettings user = processor.getUser(chatInfo);
        Long chatId = user.getChatId();

        String input = processor.getButtonValue(chatInfo, "/next_");
        if (!input.isEmpty()) {
            StudyBlockState studyState = new StudyBlockState(chatId);
            studyState.setCurrentProgress(0);
            studyState.setStudyBlockName(input);
            studyBlockStateStorage.putUserStudyBlockState(studyState);
        }
    };


    protected Consumer<Update> sets = (chatInfo) -> {
        String text = "Настройки";
        MenuBlock[] menuBlocks = Settings.values();
        InlineKeyboardMarkup markup = Keyboard.inlineWithOptional(menuBlocks, 2, OptionalButtons.CLOSE_SETS);
        service.sendNew(chatInfo, text, markup);
    };

    protected Consumer<Update> notifyTime = (chatInfo) -> {
        UserSettings user = processor.getUser(chatInfo);
        String input = service.getInputData(chatInfo, true);
        String text;
        ReplyKeyboardMarkup markup = null;
        if (input.equals("/notify")) {
            text = "Выберете из предложенных вариантов: ";
            MenuBlock[] menu = NotificationTime.values();
            markup = Keyboard.reply(menu, 9);
        } else {
            processor.pushNotify(user, input);
            LocalTime userSet = user.getTime();
            text = (Objects.nonNull(userSet)) ?
                    String.format("Установлено ежедневное оповещение в %d:00",
                            userSet.getHour()) :
                    "Ежедневное оповещение отключено";
        }
        service.sendNew(chatInfo, text, markup);
    };

    protected Consumer<Update> closeTable = (chatInfo) -> {
        String text = "Настройки";
        MenuBlock[] menu = Settings.values();
        InlineKeyboardMarkup markup = Keyboard.inlineWithOptional(menu, 2, OptionalButtons.CLOSE_SETS);
        service.sendInstead(chatInfo, text, markup);
    };

    protected Consumer<Update> closeSettings = (chatInfo) -> {
        service.sendInstead(chatInfo, " ", null);
    };

}
