package controller;

import java.util.List;

public interface Controller {

    void sendText(Long chatId);

    void sendText(List<Long> ChatId);

}
