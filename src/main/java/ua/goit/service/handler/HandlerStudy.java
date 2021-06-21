package ua.goit.service.handler;

import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import ua.goit.controller.TelegramMessageSender;
import ua.goit.model.TaskBlock;

import static ua.goit.view.buttons.KeyboardButtons.NEXT;

@Slf4j
public class HandlerStudy extends TelegramCommandHandler {

    public HandlerStudy(TelegramCommandHandler handler) {
        super(handler);
    }

    @Override
    protected void apply(Long chatId, String callbackQuery, TelegramMessageSender controller) {

        userStudyService.saveCurrentLearningLanguage(chatId, callbackQuery.substring(7));
        Optional<TaskBlock> taskCurrentQuestion = userStudyService.findStudyBlockByName(chatId);
        controller.sendNew(chatId, taskCurrentQuestion.get().toString(), 1, NEXT);
    }

    @Override
    protected boolean isApplicable(String callbackQuery) {
        return callbackQuery!=null && callbackQuery.startsWith("/study_");
    }
}
