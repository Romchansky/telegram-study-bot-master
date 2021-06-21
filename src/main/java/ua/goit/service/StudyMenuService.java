package ua.goit.service;

import java.util.List;
import java.util.stream.Collectors;

import ua.goit.model.StudyBlock;
import ua.goit.repository.BaseRepository;
import ua.goit.repository.RepositoryFactory;
import ua.goit.view.buttons.KeyboardButtons;
import ua.goit.view.buttons.MenuBlock;
import ua.goit.view.buttons.StudyButton;

public abstract class StudyMenuService {

    private final BaseRepository<StudyBlock, String> studyRepository;

    public StudyMenuService() {
        this.studyRepository = RepositoryFactory.of(StudyBlock.class);
    }

    protected MenuBlock[] getStudyMenu() {
        final List<String> blockNames = studyRepository.findAll().stream()
                .map(StudyBlock::getId)
                .collect(Collectors.toList());
        MenuBlock[] studyMenu = new MenuBlock[blockNames.size() + 1];
        for (int i = 0; i < blockNames.size(); i++) {
            studyMenu[i] = new StudyButton(blockNames.get(i), "/study_" + blockNames.get(i));
        }
        studyMenu[blockNames.size()] = KeyboardButtons.SETTINGS;
        return studyMenu;
    }
}
