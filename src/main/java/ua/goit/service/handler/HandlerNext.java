package ua.goit.service.handler;

import java.util.Optional;
import ua.goit.controller.TelegramMessageSender;
import ua.goit.model.TaskBlock;
import static ua.goit.view.buttons.KeyboardButtons.NEXT;

public class HandlerNext extends TelegramCommandHandler {

    public HandlerNext(TelegramCommandHandler handler) {
        super(handler);
    }
    
    @Override
    public void apply(Long chatId, String callbackQuery, TelegramMessageSender controller) {
        userStudyService.nextQuestion(chatId, true);
        Optional<TaskBlock> taskNextQuestion = userStudyService.findStudyBlockByName(chatId);
        if (taskNextQuestion.isPresent()) controller.sendNew(chatId, taskNextQuestion.get().toString(), 1, NEXT);
        else controller.sendNew(chatId, "Ты закончил блок обучения. Выбери другой курс обучения", 3, getStudyMenu());
    }
    
    @Override
    protected boolean isApplicable(String callbackQuery) {
        return "/next".equals(callbackQuery);
    }

}
