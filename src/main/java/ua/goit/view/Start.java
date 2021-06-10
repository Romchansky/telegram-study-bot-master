package ua.goit.view;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Start implements MenuBlock {

    REGISTRATION("Registration", "/registration");

    private final String text;
    private final String command;

}