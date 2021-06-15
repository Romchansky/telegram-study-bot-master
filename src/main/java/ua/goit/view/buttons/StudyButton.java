package ua.goit.view.buttons;

import lombok.Value;

@Value
public class StudyButton implements MenuBlock {

    private final String text;
    private final String command;
}
