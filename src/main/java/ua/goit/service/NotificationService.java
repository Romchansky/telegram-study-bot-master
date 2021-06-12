package ua.goit.service;

import ua.goit.controller.TelegramMessageSender;

public interface NotificationService {
    void addChatId(Long chaId, TelegramMessageSender telegramController);

    static NotificationService of() {
        return new NotificationServiceImpl();
    }
}