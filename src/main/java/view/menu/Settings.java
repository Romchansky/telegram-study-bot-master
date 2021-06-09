package view.menu;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Settings implements MenuBlock {

    SETTINGS("Настройки","/settings");

    private final String text;
    private final String command;

}
