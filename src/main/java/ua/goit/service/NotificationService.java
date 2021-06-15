package ua.goit.service;

import ua.goit.controller.TelegramMessageSender;
import java.time.LocalTime;

public interface NotificationService {

    void addChatId(Long chaId, Boolean isNotificationDisabled, TelegramMessageSender telegramController);

    void scheduleNotification(Long chatId, LocalTime time);

    static NotificationService of() {
        return NotificationServiceImpl.of();
    }
}
