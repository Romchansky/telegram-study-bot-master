package view;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import view.menu.MenuBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class Keyboard {
    public static InlineKeyboardMarkup inline(MenuBlock[] menu, int columns) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = createKeyboard(menu, columns, inlineButton, ArrayList::new);
        markup.setKeyboard(keyboard);
        return markup;
    }

    public static InlineKeyboardMarkup inlineWithOptional(MenuBlock[] baseMenu, int columns, MenuBlock optionalButton) {
        InlineKeyboardMarkup markup = inline(baseMenu, columns);
        List<List<InlineKeyboardButton>> keyboard = markup.getKeyboard();
        List<InlineKeyboardButton> row = optionalButton(optionalButton);
        keyboard.add(row);
        markup.setKeyboard(keyboard);
        return markup;
    }

    public static ReplyKeyboardMarkup reply(MenuBlock[] menu, int columns) {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = createKeyboard(menu, columns, replyButton, KeyboardRow::new);
        markup.setResizeKeyboard(true);
        markup.setOneTimeKeyboard(true);
        markup.setKeyboard(keyboard);
        return markup;
    }

    private static <T, R extends List<T>> List<R> createKeyboard(MenuBlock[] menu, int columns,
                                                                 Function<MenuBlock, T> buttonMapper,
                                                                 Supplier<R> rowsMapper) {
        List<R> markupRows = new ArrayList<>();
        R row = rowsMapper.get();
        if (columns <= 0) columns = 1;
        for (MenuBlock item : menu) {
            if (row.size() >= columns) {
                markupRows.add(row);
                row = rowsMapper.get();
            }
            T button = buttonMapper.apply(item);
            row.add(button);
        }
        markupRows.add(row);
        return markupRows;
    }

    private static List<InlineKeyboardButton> optionalButton(MenuBlock menuItem) {
        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton button = inlineButton.apply(menuItem);
        row.add(button);
        return row;
    }

    private static final Function<MenuBlock, InlineKeyboardButton> inlineButton = menuItem -> {
        String buttonName = menuItem.getText();
        String buttonCommand = menuItem.getCommand();
        InlineKeyboardButton button = new InlineKeyboardButton(buttonName);
        button.setCallbackData(buttonCommand);
        return button;
    };

    private static final Function<MenuBlock, KeyboardButton> replyButton = menuItem -> {
        String buttonName = menuItem.getText();
        return new KeyboardButton(buttonName);
    };
}
