package ua.goit.view.buttons;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ua.goit.view.MenuBlock;

@AllArgsConstructor
@Getter
public enum SelectCourse implements MenuBlock {

    STUDYBLOCK_1("Javascript", "/close"),
    STUDYBLOCK_2("HTML/CSS", "/close_settings"),
    STUDYBLOCK_3("REACT", "/close_settings");


    private final String text;
    private final String command;
}
