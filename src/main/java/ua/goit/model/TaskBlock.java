package ua.goit.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskBlock {

    public String question;
    public String answer;
    public String video;

    @Override
    public String toString() {
        return "TaskBlock \n" +
                "Question: " + question + "\n" +
                "Answer: " + answer + "\n" +
                "VideoLink: " + video;
    }
}