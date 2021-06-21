package ua.goit.service.handler;

import ua.goit.controller.TelegramMessageSender;

public class HandlerNotifyOff extends TelegramCommandHandler {

    public HandlerNotifyOff(TelegramCommandHandler handler) {
        super(handler);
    }
    
    @Override
    public void apply(Long chatId, String callbackQuery, TelegramMessageSender controller) {
        userStudyService.changeNotificationTime(chatId, null);
    }

    @Override
    protected boolean isApplicable(String callbackQuery) {
        return "/notifyoff".equals(callbackQuery);
    }
}
