package user.service;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalTime;
@Setter@Slf4j@Getter
public class UserSettings {
    private @Setter(AccessLevel.NONE) Long chatId;
    private String userName;
    private String email;
    private String groupNumber;
    private LocalTime time;

    public UserSettings(Long chatId) {
        this.chatId = chatId;
        time = LocalTime.of(9, 0);
        log.info("UserSettings for" + chatId.toString() + " ware created.");
    }
}
