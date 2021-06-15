package ua.goit.service;

import ua.goit.controller.TelegramMessageSender;

public interface UserRegistrationService {

    void execute(Long chatId, String text, TelegramMessageSender controller);

    static UserRegistrationService of() {
        return new UserRegistrationServiceImpl();
    }

}
