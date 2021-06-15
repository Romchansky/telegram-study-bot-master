package ua.goit.service;

import java.util.Optional;
import ua.goit.model.TaskBlock;

public interface UserStudyService {
    
    Integer nextQuestion(Long chatId, boolean increment);

    void saveCurrentLearningLanguage(Long chaId, String name);

    Integer changeNotificationTime(Long chatId, String notifyTime);
    
    Optional<TaskBlock> findStudyBlockByName(Long chatId);

    static UserStudyService of() {
        return new UserStudyServiceImpl();
    }
}
