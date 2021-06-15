package ua.goit.service;

import java.util.Optional;
import ua.goit.controller.TelegramMessageSender;
import ua.goit.model.User;
import lombok.extern.slf4j.Slf4j;
import ua.goit.repository.BaseRepository;
import ua.goit.repository.RepositoryFactory;
import ua.goit.util.MailValidator;
import ua.goit.util.Validator;

@Slf4j
public class UserRegistrationImpl extends StudyMenuService implements UserRegistration {

    private final BaseRepository<User, Long> users;
    private final Validator mailValidator;

    public UserRegistrationImpl() {
        this.mailValidator = new MailValidator();
        this.users = RepositoryFactory.of(User.class);
    }

    @Override
    public void execute(Long chatId, String text, TelegramMessageSender controller) {

        //add new user
        if (!users.existsById(chatId)) {
            users.save(new User(chatId));
            controller.sendNew(chatId, "Добро пожаловать! Для начала работы с ботом введите своё имя : ");
            return;
        }

        final User user = users.getOne(chatId);

        //registaration
        Optional<String> nextRegistrationMessage = registration(user, text);
        users.save(user);
        if (nextRegistrationMessage.isPresent()) {
            controller.sendNew(user.getId(), nextRegistrationMessage.get());
            return;
        }

        controller.sendNew(chatId, "Приветствуем тебя студент!"
                + " Этот бот поможет тебе подготовиться к техническим собеседованиям по вебразработке, "
                + "но прежде тебе нужно выбрать блок изучения", 1, getStudyMenu());

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
