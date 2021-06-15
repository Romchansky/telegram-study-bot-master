package ua.goit.service;

import ua.goit.controller.TelegramMessageSender;
import ua.goit.model.UserSettings;
import ua.goit.util.PropertiesLoader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import ua.goit.repository.BaseRepository;
import ua.goit.repository.RepositoryFactory;
import static ua.goit.view.buttons.KeyboardButtons.NO;
import static ua.goit.view.buttons.KeyboardButtons.YES;

public class NotificationServiceImpl implements NotificationService {

    private final String notification = "Привет! Готов продолжить свое обучение ?";
    private final String regularNotification = "Продолжаем ?";
    private final long notificationPeriodSec;
    private final long regularNotificationPeriodSec;
    private final long notificationNoActivity;
    
    private final BaseRepository<UserSettings, Long> repository;
    private final Map<Long, ScheduledExecutorService> executors = new ConcurrentHashMap<>();
    
    private static final NotificationServiceImpl INSTANCE = new NotificationServiceImpl();

    public static NotificationServiceImpl of() {
        return INSTANCE;
    }

    private NotificationServiceImpl() {
        this.repository = RepositoryFactory.of(UserSettings.class);
        this.notificationPeriodSec = Long.parseLong(PropertiesLoader.getProperty("user.notificationPeriodSec.time"));
        this.regularNotificationPeriodSec = Long.parseLong(PropertiesLoader.getProperty("user.regularNotificationPeriodSec.time"));
        this.notificationNoActivity = Long.parseLong(PropertiesLoader.getProperty("user.notificationNoActivity.time"));
        startTimer();
    }

    @Override
    public synchronized void scheduleNotification(Long chatId, LocalTime time) {
        UserSettings userSetting = repository.getOne(chatId);
        if (executors.containsKey(chatId)) executors.get(chatId).shutdown();
        if (time == null) return;
        LocalDateTime delayTime = LocalDateTime.of(LocalDate.now(), time);
        if (LocalDateTime.now().isAfter(delayTime)) delayTime = delayTime.plusDays(1);
        ScheduledExecutorService executor = scheduleAtFixedRate(()
                -> userSetting.getTelegramController().sendNew(userSetting.getId(), notification),
                LocalDateTime.now().until(delayTime, ChronoUnit.SECONDS), notificationPeriodSec);
        executors.put(chatId, executor);
    }

    private void startTimer() {
        scheduleAtFixedRate(()
                -> repository.findAll().stream()
                        .filter(userSetting -> userSetting.sendNotification(notificationNoActivity))
                        .forEach(userSetting -> userSetting.getTelegramController().sendNew(userSetting.getId(), regularNotification, 2, YES, NO)),
                0, regularNotificationPeriodSec);
    }

    private ScheduledExecutorService scheduleAtFixedRate(Runnable command, long delaySec, long periodSec) {
        final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(command, delaySec, periodSec, TimeUnit.SECONDS);
        return executor;
    }

    @Override
    public void addChatId(Long chatId, Boolean isNotificationDisabled, TelegramMessageSender telegramController) {
        repository.save(new UserSettings(chatId, isNotificationDisabled, telegramController));
    }
    
}
