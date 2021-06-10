package ua.goit.model;

import java.time.LocalTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.goit.util.PropertiesLoader;

@Data
@NoArgsConstructor
public class UserSettings {

    private Long chatId;
    private String name;
    private String email;
    private String groupNumber;
    private LocalTime time;

    public UserSettings(Long chatId) {
        String [] userNotificationTime = PropertiesLoader.getProperty("user.notification.time").split(":");
        this.time = LocalTime.of(Integer.valueOf(userNotificationTime[0]), Integer.valueOf(userNotificationTime[1]));
        this.chatId = chatId;
    }
}
