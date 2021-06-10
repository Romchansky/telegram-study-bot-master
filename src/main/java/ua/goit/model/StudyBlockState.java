package ua.goit.model;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudyBlockState implements BaseEntity<Long>{

    public Long id;
    public String name;
    public List<TaskBlock> questionsLists;

    public StudyBlockState(String name, List<TaskBlock> questionsLists) {
        this.name = name;
        this.questionsLists = questionsLists;
    }

    @Override
    public String toString() {
        return "StudyBlock \n" +
                "Name - " + name + "\n" +
                "QuestionsLists - " + questionsLists;
    }

}