package user.service;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class UserSettingsStorage implements Storage {

    public static final UserSettingsStorage STORAGE = new UserSettingsStorage();
    private final ConcurrentHashMap<Long, UserSettings> userSettingsStorage = new ConcurrentHashMap<>();

    public UserSettingsStorage() {

    }

    public UserSettingsStorage getStorage() {
        return STORAGE;
    }

    @Override
    public void putUserSettings(UserSettings userSettings) {
        Long chatId = userSettings.getChatId();
        userSettingsStorage.putIfAbsent(chatId, userSettings);
        log.debug("User " + chatId.toString() + " was created and stored", userSettings);
    }

    @Override
    public UserSettings getUserByChatId(Long chatId) {
        return userSettingsStorage.get(chatId);
    }
}
