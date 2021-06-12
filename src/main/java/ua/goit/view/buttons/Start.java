package ua.goit.view.buttons;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ua.goit.view.MenuBlock;

@AllArgsConstructor
@Getter
public enum Start implements MenuBlock {

    REGISTRATION("Registration", "/registration");

    private final String text;
    private final String command;

}