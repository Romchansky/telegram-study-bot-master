package view.menu;

public class ButtonStudyBlock implements MenuBlock{

    private String text;
    private String command;

    public ButtonStudyBlock{
    }

    public ButtonStudyBlock(String text, String command) {
        this.text = text;
        this.command = command;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getCommand() {
        return command;
    }
}
