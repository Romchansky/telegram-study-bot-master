package ua.goit.service;

import lombok.extern.slf4j.Slf4j;
import ua.goit.model.User;
import ua.goit.repository.BaseRepository;
import ua.goit.repository.RepositoryFactory;
import ua.goit.view.buttons.NotificationTime;
import java.time.LocalTime;
import java.util.Optional;
import ua.goit.model.StudyBlock;
import ua.goit.model.TaskBlock;

@Slf4j
public class UserStudyServiceImpl implements UserStudyService {

    private final NotificationService notificationService;
    private final BaseRepository<User, Long> userRepository;    
    private final BaseRepository<StudyBlock, String> studyRepository;

    public UserStudyServiceImpl() {
        this.userRepository = RepositoryFactory.of(User.class);
        this.studyRepository = RepositoryFactory.of(StudyBlock.class);
        this.notificationService = NotificationService.of();
    }
    
    @Override
    public Integer nextQuestion(Long chatId, boolean increment) {
        User user = userRepository.getOne(chatId);
        Integer numberOfQuestion = user.getCurrentStudyState().getOrDefault(user.getCurrentLearningLanguage(), 0);
        if (increment) numberOfQuestion++;
        user.getCurrentStudyState().put(user.getCurrentLearningLanguage(), numberOfQuestion);
        userRepository.save(user);
        return numberOfQuestion;
    }

    @Override
    public void saveCurrentLearningLanguage(Long chatId, String name) {
        User user = userRepository.getOne(chatId);
        user.setCurrentLearningLanguage(name);
        userRepository.save(user);
    }

    @Override
    public Integer changeNotificationTime(Long chatId, String notifyTime) {
        User user = userRepository.getOne(chatId);
        if (notifyTime == null) user.setTime(null);
        else user.setTime(LocalTime.of(NotificationTime.time.get(notifyTime), 0));
        notificationService.scheduleNotification(chatId, user.getTime());
        return userRepository.save(user).getTime().getHour();
    }
    
    @Override
    public Optional<TaskBlock> findStudyBlockByName(Long chatId) {
        User user = userRepository.getOne(chatId);
        Integer questionNumber = user.getCurrentStudyState().getOrDefault(user.getCurrentLearningLanguage(), 1);// потому что индекс 0 берет шапку таблицы
        try {
            String name = userRepository.getOne(chatId).getCurrentLearningLanguage();
            return Optional.of(studyRepository.getOne(name).getQuestionsLists().get(questionNumber));
        } catch (IndexOutOfBoundsException ex) {
            return Optional.empty();
        }
    }
    

}
