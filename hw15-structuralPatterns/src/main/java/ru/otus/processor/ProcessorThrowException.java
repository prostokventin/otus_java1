package ru.otus.processor;

import ru.otus.model.Message;

import java.time.LocalDateTime;

public class ProcessorThrowException implements Processor {

    private LocalDateTime dateTime;

    public ProcessorThrowException(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public Message process(Message message) {
        return null;
    }
}
