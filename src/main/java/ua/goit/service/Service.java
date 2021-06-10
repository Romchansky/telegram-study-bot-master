package ua.goit.service;

import lombok.extern.slf4j.Slf4j;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
public class Service {

    private final Map<String, Consumer> commands = new HashMap<>();

    public Service(Consumer<Update> start) {
        commands.put("/start", start);
    }

    public void call(String command, Object chatInfo) {
        commands.get(command).accept(chatInfo);
    }

    public void sendText(Long chatId) {
        call("/next_", chatId);
        log.info("The info for " + chatId.toString() + "is sent");
    }

    public void sendText(List<Long> chatIdUsers) {
        chatIdUsers.forEach(this::sendText);
    }

}
