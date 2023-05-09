package ru.otus.processor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;
import ru.otus.processor.homework.ProcessorSwitchFields;
import ru.otus.processor.homework.ProcessorThrowException;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ProcessorThrowExceptionTest {

    @Test
    @DisplayName("Тестируем выбрасывание исключения в четную секунду")
    void throwExceptionTest() {
        //given
        var message = new Message.Builder(1L).build();

        //when
        var processor1 = new ProcessorThrowException(() -> LocalDateTime.of(2020, 1, 1, 0, 0, 0));

        //then
        Assertions.assertThrows(RuntimeException.class, () -> {
            processor1.process(message);
        });

        //when
        var processor2 = new ProcessorThrowException(() -> LocalDateTime.of(2020, 1, 1, 0, 0, 1));

        //then
        Assertions.assertDoesNotThrow(() -> {
            processor2.process(message);
        });

    }

}
