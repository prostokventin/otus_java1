package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;

import java.util.*;

public class HistoryListener implements Listener, HistoryReader {

    List<Message> messageList = new ArrayList<>();

    @Override
    public void onUpdated(Message msg) {
        messageList.add(msg.copy());
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return messageList.stream().filter(msg -> msg.getId() == id).findFirst();
    }
}
