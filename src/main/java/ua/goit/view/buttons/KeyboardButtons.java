package ua.goit.view.buttons;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ua.goit.view.MenuBlock;

@AllArgsConstructor
@Getter
public enum KeyboardButtons implements MenuBlock {

    YES("Да","/yes"),
    NO("Нет","/no"),
    NEXT("Далее","/next"),
    SETTINGS("Настройки","/settings");

    private final String text;
    private final String command;

}
