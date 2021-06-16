package ua.goit.model;

import java.util.List;
import lombok.Value;

@Value
public class StudyBlock implements BaseEntity<String> {

    private String id; ///studyBlockName
    private List<TaskBlock> questionsLists;

    public StudyBlock(String studyBlockName, List<TaskBlock> questionsLists) {
        this.id = studyBlockName;
        this.questionsLists = questionsLists;
    }
}
