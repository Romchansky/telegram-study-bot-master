package ua.goit.view.buttons;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ua.goit.view.MenuBlock;

@AllArgsConstructor
@Getter
public enum AcceptAndDecline implements MenuBlock {

    YES("Да","/yes"),
    NO("Нет","/no");

    private final String text;
    private final String command;

}
