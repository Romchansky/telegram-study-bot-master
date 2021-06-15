package ua.goit.service;

import ua.goit.model.StudyBlock;
import ua.goit.repository.BaseRepository;
import ua.goit.repository.RepositoryFactory;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import ua.goit.controller.StudyBlockReader;

public class UpdateGoogleSheetsService {

    private final StudyBlockReader reader;
    private final BaseRepository<StudyBlock, String> repository;

    public UpdateGoogleSheetsService(StudyBlockReader reader) {
        this.reader = reader;
        this.repository = RepositoryFactory.of(StudyBlock.class);
    }

    public void startScheduler() {
        Executors.newSingleThreadScheduledExecutor().
                scheduleAtFixedRate(()->repository.saveAll(reader.read()), 
                0, 24, TimeUnit.HOURS);
    }
    
}
