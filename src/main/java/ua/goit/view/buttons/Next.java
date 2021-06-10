package ua.goit.view.buttons;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ua.goit.view.MenuBlock;

@AllArgsConstructor
@Getter
public enum Next implements MenuBlock {

    NEXT("Далее","/next");

    private final String text;
    private final String command;


}
