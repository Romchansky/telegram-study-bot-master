package ua.goit.model;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import ua.goit.util.PropertiesLoader;

@Data
public class User implements BaseEntity<Long> {

    private Long id; //chatId
    private String name;
    private String email;
    private String groupNumber;
    private String currentLearningLanguage;
    private LocalTime time;
    private Map<String, Integer> currentStudyState;

    public User(Long chatId) {
        this.currentStudyState = new HashMap<>();
        this.time = LocalTime.parse(PropertiesLoader.getProperty("user.notification.time"));
        this.id = chatId;
    }
}
