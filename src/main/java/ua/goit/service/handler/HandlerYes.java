package ua.goit.service.handler;

import java.util.Optional;
import ua.goit.controller.TelegramMessageSender;
import ua.goit.model.TaskBlock;
import static ua.goit.view.buttons.KeyboardButtons.NEXT;

public class HandlerYes extends TelegramCommandHandler {

    public HandlerYes(TelegramCommandHandler handler) {
        super(handler);
    }
    
    @Override
    protected void apply(Long chatId, String callbackQuery, TelegramMessageSender controller) {
        notificationService.addChatId(chatId, false, controller);
        userStudyService.nextQuestion(chatId, false);
        Optional<TaskBlock> taskNextQuestionYes = userStudyService.findStudyBlockByName(chatId);
        if (taskNextQuestionYes.isPresent()) controller.sendNew(chatId, taskNextQuestionYes.get().toString(), 1, NEXT);
        else controller.sendNew(chatId, "Ты закончил блок обучения. Выбери другой курс обучения", 3, getStudyMenu());
    }

    @Override
    protected boolean isApplicable(String callbackQuery) {
        return "/yes".equals(callbackQuery);
    }

}
