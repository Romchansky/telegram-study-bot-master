package ua.goit.service;

import ua.goit.model.TaskBlock;

import java.util.Optional;


public interface StudyService {

    Optional<TaskBlock> findStudyBlockByName(Long chatId, Integer questionNumber);

    static StudyService of() {
        return new StudyServiceImpl();
    }
}

