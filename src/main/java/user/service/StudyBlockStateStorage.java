package user.service;

import java.util.concurrent.ConcurrentHashMap;

public class StudyBlockStateStorage {
    private static final StudyBlockStateStorage STORAGE = new StudyBlockStateStorage();
    private final ConcurrentHashMap<Long, StudyBlockState> studyBlockStateStorage = new ConcurrentHashMap<>();

    public StudyBlockStateStorage getStorage() {
        return STORAGE;
    }

    public void putUserStudyBlockState(StudyBlockState studyBlockState) {
        Long chatId = studyBlockState.getChatId();
        studyBlockStateStorage.putIfAbsent(chatId, studyBlockState);
    }

    public StudyBlockState getUserStudyBlockStateByChatId(Long chatId) {
        return studyBlockStateStorage.get(chatId);
    }

    public void updateUserStudyBlockState(Long chatId, int currentProgress) {
        studyBlockStateStorage.computeIfPresent(chatId, (keyLong, studyBlockState) -> {
            studyBlockState.setCurrentProgress(currentProgress);
            return studyBlockState;
        });
    }

    public String getUserStudyBlockStateName(Long chatId) {
        return studyBlockStateStorage.get(chatId).getStudyBlockName();
    }
}
