package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

import java.time.LocalDateTime;

public class ProcessorThrowException implements Processor {

    private final DateTimeProvider dateTimeProvider;

    public ProcessorThrowException(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public Message process(Message message) {
        if (dateTimeProvider.getDate().getSecond() % 2 == 0) throw new RuntimeException("исключение в четную секунду");
        return message;
    }
}
