package ua.goit.service;

import ua.goit.controller.GoogleSheetsController;
import ua.goit.model.StudyBlock;
import ua.goit.repository.BaseRepository;
import ua.goit.repository.RepositoryFactory;



import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class UpdateGoogleSheets {

    private final GoogleSheetsController googleSheetsController;
    private final BaseRepository<StudyBlock, String> repository;

    public UpdateGoogleSheets() {
        this.googleSheetsController = new GoogleSheetsController();
        this.repository = RepositoryFactory.of(StudyBlock.class);
        startScheduler();
    }

    private void startScheduler() {
        scheduleAtFixedRate(()-> repository.saveAll(googleSheetsController.read()), 24 );
    }

    private void scheduleAtFixedRate(Runnable command, Integer delayHours) {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(command, 0, delayHours, TimeUnit.HOURS);
    }
}

