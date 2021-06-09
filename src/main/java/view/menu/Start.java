package view.menu;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Start implements MenuBlock {

    START("Старт", "/start");

    private final String text;
    private final String command;

}
