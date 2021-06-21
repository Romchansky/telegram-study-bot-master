package ua.goit.service.handler;

import ua.goit.controller.TelegramMessageSender;

public class HandlerNotify extends TelegramCommandHandler {

    public HandlerNotify(TelegramCommandHandler handler) {
        super(handler);
    }
    
    @Override
    public void apply(Long chatId, String callbackQuery, TelegramMessageSender controller) {
        Integer hour = userStudyService.changeNotificationTime(chatId, callbackQuery);
        controller.sendNew(chatId, "Уведомление установлено на " + hour + ":00 часов");
    }

    @Override
    protected boolean isApplicable(String callbackQuery) {
        try {
            return "/notify".equals(callbackQuery.substring(0, 7));
        } catch (StringIndexOutOfBoundsException e) {
            return false;
        }
    }
    
}