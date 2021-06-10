package ua.goit.view.buttons;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ua.goit.view.MenuBlock;

@AllArgsConstructor
@Getter
public class ButtonStudyBlock implements MenuBlock {

    private String text;
    private String command;

}
