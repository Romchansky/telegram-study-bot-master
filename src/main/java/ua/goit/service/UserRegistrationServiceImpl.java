package ua.goit.service;

import java.util.Optional;
import ua.goit.controller.TelegramMessageSender;
import ua.goit.model.User;
import ua.goit.repository.BaseRepository;
import ua.goit.repository.RepositoryFactory;
import lombok.extern.slf4j.Slf4j;
import ua.goit.util.MailValidator;
import ua.goit.util.Validator;

@Slf4j
public class UserRegistrationServiceImpl extends StudyMenuService implements UserRegistrationService {

    private final Validator mailValidator;
    private final BaseRepository<User, Long> repository;
    private final NotificationService notificationService;

    public UserRegistrationServiceImpl() {
        this.mailValidator = new MailValidator();
        this.repository = RepositoryFactory.of(User.class);
        this.notificationService = NotificationService.of();
    }

    @Override
    public void execute(Long chatId, String text, TelegramMessageSender controller) {

        //add new user
        Optional<User> userOptional = repository.findById(chatId);
        if (!userOptional.isPresent()) {
            repository.save(new User(chatId));
            controller.sendNew(chatId, "Добро пожаловать! Для начала работы с ботом введите своё имя : ");
            return;
        }

        final User user = userOptional.get();

        //registaration
        Optional<String> nextRegistrationMessage = registration(user, text);
        repository.save(user);
        if (nextRegistrationMessage.isPresent()) {
            controller.sendNew(user.getId(), nextRegistrationMessage.get());
            return;
        }
        notificationService.addChatId(chatId, false, controller);
        notificationService.scheduleNotification(chatId, user.getTime());
        controller.sendNew(chatId, "Приветствуем тебя студент!"
                + " Этот бот поможет тебе подготовиться к техническим собеседованиям по вебразработке, "
                + "но прежде тебе нужно выбрать блок изучения", 1, getStudyMenu());

        //deligate to study service or method
    }

    private Optional<String> registration(User user, String text) {

        if (user.getName() == null) {
            user.setName(text);
            return Optional.of("Теперь введи адрес электронной почты : ");
        }

        if (user.getEmail() == null) {
            if (!mailValidator.valid(text)) {
                return Optional.of("Введи адрес электронной почты еще раз : ");
            }
            user.setEmail(text);
            return Optional.of("Теперь введи номер группы : ");
        }

        if (user.getGroupNumber() == null) {
            user.setGroupNumber(text);
        }

        log.info("Registration successfully completed : " + user);
        return Optional.empty();
    }
}
