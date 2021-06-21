package ua.goit.service.handler;

import ua.goit.controller.TelegramMessageSender;

public class HandlerException extends TelegramCommandHandler {
    
    public HandlerException() {
        super(null);
    }

    @Override
    public void apply(Long chatId, String callbackQuery, TelegramMessageSender controller) {
        throw new RuntimeException("Command '" + callbackQuery + "' not found");
    }

    @Override
    protected boolean isApplicable(String callbackQuery) {
        return true;
    }
    
}
