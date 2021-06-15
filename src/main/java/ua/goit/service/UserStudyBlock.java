package ua.goit.service;

import ua.goit.controller.TelegramMessageSender;

public interface UserStudyBlock {

    public void execute(Long chatId, String callbackQuery, TelegramMessageSender controller);

    static UserStudyBlock of() {
        return new UserStudyBlockImpl();
    }

}
