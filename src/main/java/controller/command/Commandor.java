package controller.command;

import controller.Controller;
import controller.TelegramStudyBot;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@Slf4j
public class Commandor extends Actions implements Controller {
    private static Commandor commandor;
    private static final Map<String, Consumer> commands = new HashMap<>();
    private static final ExecutorService executor = Executors.newFixedThreadPool(10);
    private static final String GET_NEXT_COMMAND = "/next_";


    private Commandor(TelegramStudyBot telegramStudyBot) {
        super(telegramStudyBot);
        this.register(start, "/start");
        this.register(registration, "/registration");
        this.register(next, GET_NEXT_COMMAND);
        this.register(sets, "/settings_");
        this.register(closeTable, "/close");
        this.register(closeSettings, "/close_settings");
        this.register(notifyTime, "/notify_",
                "9",
                "10",
                "11",
                "12",
                "13",
                "14",
                "15",
                "16",
                "17",
                "Отключить уведомления");
    }

    public static Commandor getInstance(TelegramStudyBot studyBot) {
        if (Objects.isNull(commandor)) {
            commandor = new Commandor(studyBot);
        }
        return commandor;
    }

    public <T> void register(Consumer<T> action, String... inputCommands) {
        if (Objects.nonNull(inputCommands) && inputCommands.length > 0)
            for (String command : inputCommands) {
                commands.put(command, action);
            }
    }

    public <T> void call(String command, T chatInfo) {
        if (command.startsWith("/next")) {
            command = "next";
        }

//        if (command.startsWith("/register")) {
//            command = "/register";
//        }

        //написать условие на то на какой стадии регистрация и если она закончена то сделать скип и перейти на другую команду
        // то есть регистрация это должно быть
        Consumer<T> action = commands.get(command);
        Command <T> method = new Command<>(action);
        method.setChatInfo(chatInfo);
        executor.execute(method);

    }

    @Override
    public void sendText(Long chatId) {
        call(GET_NEXT_COMMAND, chatId);
        log.info("The info for " + chatId.toString() + "is sent");
    }

    @Override
    public void sendText(List<Long> chatIdUsers) {
        chatIdUsers.forEach(chatId -> {
            call(GET_NEXT_COMMAND, chatId);
            log.info("The info for " + chatId.toString() + "is sent");
        });
    }
}
