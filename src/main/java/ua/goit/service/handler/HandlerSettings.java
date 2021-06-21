package ua.goit.service.handler;

import ua.goit.controller.TelegramMessageSender;
import ua.goit.view.buttons.NotificationTime;

public class HandlerSettings extends TelegramCommandHandler {

    public HandlerSettings(TelegramCommandHandler handler) {
        super(handler);
    }

    @Override
    protected void apply(Long chatId, String callbackQuery, TelegramMessageSender controller) {
        controller.sendNewWithReply(chatId, "Выбери удобное время для оповещений", 3, NotificationTime.values());
        notificationService.addChatId(chatId, false, controller);
    }
    
    @Override
    protected boolean isApplicable(String callbackQuery) {
        return "/settings".equals(callbackQuery);
    }
    
}
