package view.menu;

public enum Start implements MenuBlock {

    REGISTRATION("Регистрация", "/registration");

    private String text;
    private String command;

     Start(String text, String command) {
        this.text = text;
        this.command = command;
    }

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public String getCommand() {
        return this.command;
    }
}
