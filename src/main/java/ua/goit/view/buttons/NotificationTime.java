package ua.goit.view.buttons;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public enum NotificationTime implements MenuBlock {

    TIME_9("9", "/notify9"),
    TIME_10("10", "/notify10"),
    TIME_11("11", "/notify11"),
    TIME_12("12", "/notify12"),
    TIME_13("13", "/notify13"),
    TIME_14("14", "/notify14"),
    TIME_15("15", "/notify15"),
    TIME_16("16", "/notify16"),
    TIME_17("17", "/notify17"),
    TIME_18("18", "/notify18"),
    TIME_OFF("Отключить уведомления", "/notifyoff");

    private final String text;
    private final String command;
    public final static Map<String, Integer> time = Arrays.stream(NotificationTime.values())
            .filter(notificationTime -> !notificationTime.getCommand().equals("/notifyoff"))
            .collect(Collectors.toMap(k -> k.getCommand(), v -> Integer.valueOf(v.getText())));
}
