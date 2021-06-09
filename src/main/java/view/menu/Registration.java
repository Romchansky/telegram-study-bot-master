package view.menu;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Registration implements MenuBlock{

    REGISTRATION("Регистрация", "/registration");

    private final String text;
    private final String command;

}
