package view.menu;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OptionalButtons implements MenuBlock {

    CLOSE("Закрыть", "/close"),
    CLOSE_SETS("Закрыть", "/close_settings");

    private final String text;
    private final String command;
}
