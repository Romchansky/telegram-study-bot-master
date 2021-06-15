package ua.goit.service;

import ua.goit.controller.GoogleSheetsController;
import ua.goit.model.StudyBlock;
import ua.goit.model.TaskBlock;
import ua.goit.model.User;
import ua.goit.repository.BaseRepository;
import ua.goit.repository.RepositoryFactory;
import ua.goit.util.PropertiesLoader;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class StudyServiceImpl implements StudyService {

    private final GoogleSheetsController googleSheetsController = new GoogleSheetsController();
    private final BaseRepository<StudyBlock, String> repository;
    private final BaseRepository<User, Long> userRepository;

    public StudyServiceImpl() {
        repository = RepositoryFactory.of(StudyBlock.class);
        userRepository = RepositoryFactory.of(User.class);
        updateStudyBlock();
    }

    private void updateStudyBlock() {
        repository.saveAll(googleSheetsController.read());
    }

    public List<String> getStudyBlockNames() {
        return repository.findAll().stream().map(StudyBlock::getId)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TaskBlock> findStudyBlockByName(Long chatId, Integer questionNumber) {
        try {
            String name = userRepository.findById(chatId).get().getCurrentLearningLanguage();
            return repository.findById(name).map(studyBlock ->
                    Optional.of(studyBlock.getQuestionsLists().get(questionNumber)))
                    .orElseThrow(() -> new RuntimeException("Taskblock with " + name + " not exist"));
        } catch (IndexOutOfBoundsException ex) {
            return Optional.empty();
        }
    }
}