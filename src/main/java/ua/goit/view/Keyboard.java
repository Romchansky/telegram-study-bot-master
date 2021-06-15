package ua.goit.view;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ua.goit.view.buttons.MenuBlock;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.function.Supplier;

public class Keyboard {
    
    private static final Function<MenuBlock, InlineKeyboardButton> inlineButton = menuItem -> {
        InlineKeyboardButton button = new InlineKeyboardButton(menuItem.getText());
        button.setCallbackData(menuItem.getCommand());
        return button;
    };

    private static final Function<MenuBlock, KeyboardButton> replyButton = menuItem -> {
        String buttonName = menuItem.getText();
        return new KeyboardButton(buttonName);
    };

    public static InlineKeyboardMarkup inline(int columns, MenuBlock... menu) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = createKeyboard(menu, columns, inlineButton, ArrayList::new);
        markup.setKeyboard(keyboard);
        return markup;
    }

    public static ReplyKeyboardMarkup reply(int columns, MenuBlock...menu) {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = createKeyboard(menu, columns, replyButton, KeyboardRow::new);
        markup.setResizeKeyboard(true);
        markup.setOneTimeKeyboard(true);
        markup.setKeyboard(keyboard);
        return markup;
    }

    private static <T, R extends List<T>> List<R> createKeyboard(MenuBlock[] menu, int columns,
            Function<MenuBlock, T> buttonMapper, Supplier<R> rowsMapper) {
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
    
}
