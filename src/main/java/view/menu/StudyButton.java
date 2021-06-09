package view.menu;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StudyButton implements MenuBlock{

    private final String text;
    private final String command;
}
