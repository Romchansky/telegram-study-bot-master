package view.menu;

public enum Registration implements MenuBlock {

    REGISTRATION("Регистрация", "/registration");

    private String text;
    private String command;

    Registration(String text, String command) {
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
