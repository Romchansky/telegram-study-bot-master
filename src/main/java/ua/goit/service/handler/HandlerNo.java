package ua.goit.service.handler;

import ua.goit.controller.TelegramMessageSender;

public class HandlerNo extends TelegramCommandHandler {

    public HandlerNo(TelegramCommandHandler handler) {
        super(handler);
    }
    
    @Override
    public void apply(Long chatId, String callbackQuery, TelegramMessageSender controller) {
        notificationService.addChatId(chatId, true, controller);
        controller.sendNew(chatId, "Ждем Вашего скорейшего возвращения!");
    }

    @Override
    protected boolean isApplicable(String callbackQuery) {
        return "/no".equals(callbackQuery);
    }
}
