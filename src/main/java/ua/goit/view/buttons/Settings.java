package ua.goit.view.buttons;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ua.goit.view.MenuBlock;

@AllArgsConstructor
@Getter
public enum Settings implements MenuBlock {

    SETTINGS("Настройки","/settings");

    private final String text;
    private final String command;

}
