package ua.goit.model;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ua.goit.controller.TelegramMessageSender;
import static java.time.temporal.ChronoUnit.SECONDS;

@Data
@EqualsAndHashCode(exclude = {"lastNotificationAt", "isNotificationDisabled"})
public class UserSettings implements BaseEntity<Long> {

    private final Long id;//chatId
    private Boolean isNotificationDisabled;
    private LocalDateTime lastNotificationAt;
    private TelegramMessageSender telegramController;

    public UserSettings(Long chatId, Boolean isNotificationDisabled, TelegramMessageSender telegramController) {
        this.id = chatId;
        this.isNotificationDisabled = isNotificationDisabled;
        this.lastNotificationAt = LocalDateTime.now();
        this.telegramController = telegramController;
    }

    public boolean sendNotification(long periodSec) {
        if (!isNotificationDisabled && lastNotificationAt.until(LocalDateTime.now(), SECONDS) >= periodSec) {
            lastNotificationAt = LocalDateTime.now();
            return true;
        }
        return false;
    }
}
