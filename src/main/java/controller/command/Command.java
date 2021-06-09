package controller.command;


import java.util.function.Consumer;

public class Command<T> implements Runnable {
    private final Consumer<T> action;
    private T chatInfo;

    public Command(Consumer<T> action) {
        this.action = action;
        this.chatInfo = null;
    }
    protected Command<T> Clone(Command<T> command){
        return new Command<>(command.action);
    }

    void setChatInfo(T chatInfo) {
        this.chatInfo = chatInfo;
    }

    @Override
    public void run() {
        action.accept(chatInfo);
    }
}
