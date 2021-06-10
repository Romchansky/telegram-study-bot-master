package ua.goit.view;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StudyButton implements MenuBlock {

    private final String text;
    private final String command;
}
