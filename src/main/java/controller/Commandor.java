package controller;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@Slf4j
public class Commandor extends Actions implements Controller{
    private static Commandor commandor;
    private static final Map<String, Consumer> commands = new HashMap<>();
    private static final ExecutorService executor = Executors.newFixedThreadPool(10);
    private static final String GET_EXCHANGE_COMMAND = "/info";


   private Commandor(TelegramStudyBot telegramStudyBot) {
        super(telegramStudyBot);
        this.register(start, "/start");
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
        Consumer<T> action = commands.get(command);
        Command <T> method = new Command<>(action);
        method.setChatInfo(chatInfo);
        executor.execute(method);

    }

    @Override
    public void sendText(Long chatId) {
        call(GET_EXCHANGE_COMMAND, chatId);
        log.info("The info for " + chatId.toString() + "is sent");
    }

    @Override
    public void sendText(List<Long> chatIdUsers) {
       chatIdUsers.forEach(chatId -> {
           call(GET_EXCHANGE_COMMAND, chatId);
           log.info("The info for " + chatId.toString() + "is sent");
       });
    }
}