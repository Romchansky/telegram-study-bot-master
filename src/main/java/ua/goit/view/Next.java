package ua.goit.view;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Next implements MenuBlock {

    NEXT("Далее","/next");

    private final String text;
    private final String command;


}
