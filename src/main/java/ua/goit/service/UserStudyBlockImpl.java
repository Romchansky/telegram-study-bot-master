package ua.goit.service;

import lombok.extern.slf4j.Slf4j;
import ua.goit.controller.TelegramMessageSender;
import ua.goit.model.TaskBlock;
import ua.goit.view.buttons.NotificationTime;
import java.util.Optional;
import static ua.goit.view.buttons.KeyboardButtons.NEXT;

@Slf4j
public class UserStudyBlockImpl extends StudyMenuService implements UserStudyBlock {

    private final NotificationService notificationService = NotificationService.of();
    private final UserStudyService userStudyService = UserStudyService.of();

    @Override
    public void execute(Long chatId, String callbackQuery, TelegramMessageSender controller) {

        log.info("Command : " + callbackQuery);

        String name = null;

        if (callbackQuery.contains("_")) {
            name = callbackQuery.substring(7);
            callbackQuery = callbackQuery.substring(0, 7);
        }

        switch (callbackQuery) {
            case "/yes":
                notificationService.addChatId(chatId, false, controller);
                userStudyService.nextQuestion(chatId, false);
                Optional<TaskBlock> taskNextQuestionYes = userStudyService.findStudyBlockByName(chatId);
                if (taskNextQuestionYes.isPresent()) {
                    controller.sendNew(chatId, taskNextQuestionYes.get().toString(), 1, NEXT);
                } else {
                    controller.sendNew(chatId, "Ты закончил блок обучения. Выбери другой курс обучения", 3, getStudyMenu());
                }
                break;
            case "/no":
                notificationService.addChatId(chatId, true, controller);
                controller.sendNew(chatId, "Ждем Вашего скорейшего возвращения!");
                break;
            case "/settings":
                controller.sendNewWithReply(chatId, "Выбери удобное время для оповещений", 3,
                        NotificationTime.values());
                notificationService.addChatId(chatId, false, controller);
                break;
            case "/study_":
                userStudyService.saveCurrentLearningLanguage(chatId, name);
                Optional<TaskBlock> taskCurrentQuestion = userStudyService.findStudyBlockByName(chatId);
                controller.sendNew(chatId, taskCurrentQuestion.get().toString(), 1, NEXT);
                break;
            case "/next":
                userStudyService.nextQuestion(chatId, true);
                Optional<TaskBlock> taskNextQuestion = userStudyService.findStudyBlockByName(chatId);
                if (taskNextQuestion.isPresent()) {
                    controller.sendNew(chatId, taskNextQuestion.get().toString(), 1, NEXT);
                } else {
                    controller.sendNew(chatId, "Ты закончил блок обучения. Выбери другой курс обучения", 3, getStudyMenu());
                }
                break;
            case "/notifyoff":
                userStudyService.changeNotificationTime(chatId, null);
            default:
                if (callbackQuery.startsWith("/notify")) {
                    Integer hour = userStudyService.changeNotificationTime(chatId, callbackQuery);
                    controller.sendNew(chatId, "Уведомление установлено на " + hour);
                } else {
                    throw new RuntimeException("Command not found");
                }
        }
    }

}
