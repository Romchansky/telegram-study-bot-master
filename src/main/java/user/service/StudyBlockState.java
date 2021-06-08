package user.service;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Getter
@Setter
@Slf4j
public class StudyBlockState {
    private @Setter(AccessLevel.NONE)
    Long chatId;
    private String studyBlockName;
    private int currentProgress;

    public StudyBlockState(Long chatId) {
        this.chatId = chatId;
    }

    public StudyBlockState(){}



    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        StudyBlockState x = (StudyBlockState) obj;
        return chatId.equals(x.chatId);
    }
}
