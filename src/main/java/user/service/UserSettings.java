package user.service;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalTime;


@Slf4j
@Setter
@Getter
@ToString
public class UserSettings {
    private @Setter(AccessLevel.NONE) Long chatId;
    private String email;
    private String groupNumber;
    private LocalTime time;

    public UserSettings(Long chatId) {
        this.chatId = chatId;
        time = LocalTime.of(9, 0);
        log.info("new User : " + chatId.toString() + " is created. Set  ChatId");
    }
}
