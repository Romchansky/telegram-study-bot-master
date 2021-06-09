package user.service;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@EqualsAndHashCode(exclude = {"studyBlockName", "currentProgress" })
@Getter
@Setter
@Slf4j
public class StudyBlockState {

    private  Long chatId;
    private String studyBlockName;
    private int currentProgress;

    public StudyBlockState(Long chatId) {
        this.chatId = chatId;
    }

    public StudyBlockState(){}


}
