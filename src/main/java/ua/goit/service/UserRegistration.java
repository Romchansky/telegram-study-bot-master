package ua.goit.service;

import ua.goit.controller.TelegramMessageSender;

public interface UserRegistration {

     void execute(Long chatId, String text, TelegramMessageSender controller);

    static UserRegistration of() {
        return new UserRegistrationImpl();
    }

}
