package user.service;

import org.telegram.telegrambots.meta.api.objects.User;

public interface Storage {

     void putUserSettings(UserSettings userSettings);
     UserSettings getUserByChatId(Long chatId);
     boolean validateEmail(String email);
}
