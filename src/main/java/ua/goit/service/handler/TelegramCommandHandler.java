package ua.goit.service.handler;

import ua.goit.controller.TelegramMessageSender;
import ua.goit.service.NotificationService;
import ua.goit.service.StudyMenuService;
import ua.goit.service.UserStudyService;

public abstract class TelegramCommandHandler extends StudyMenuService {

    protected final NotificationService notificationService;
    protected final UserStudyService userStudyService;
    
    private final TelegramCommandHandler handler;

    public TelegramCommandHandler(TelegramCommandHandler handler) {
        this.notificationService = NotificationService.of();
        this.userStudyService = UserStudyService.of();
        this.handler = handler;
    }

    protected abstract void apply(Long chatId, String callbackQuery, TelegramMessageSender controller);

    protected abstract boolean isApplicable(String callbackQuery);

    public final void handle(Long chatId, String callbackQuery, TelegramMessageSender controller) {
        if (isApplicable(callbackQuery)) apply(chatId, callbackQuery, controller);
        else handler.handle(chatId, callbackQuery, controller);
    }

    
    
//    public static void main(String[] args) {
//        TelegramCommandHandler handler = TelegramCommandHandler.of();
//        handler.handle(1L, "", controller);
//    }
    
    
    public static TelegramCommandHandler of() {
        return new HandlerYes(new HandlerNo(new HandlerSettings(new HandlerStudy(
                new HandlerNext(new HandlerNotifyOff(new HandlerNotify(new HandlerException())))))));
    }

}
