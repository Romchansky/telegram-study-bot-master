package ua.goit.controller;

import java.util.List;
import ua.goit.model.StudyBlock;

@FunctionalInterface
public interface StudyBlockReader {

    List<StudyBlock> read();

}
