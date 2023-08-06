package ru.otus.model;

import jakarta.annotation.Nonnull;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Table("phone")
public class Phone {

    @Id
    private final Long id;

    @Nonnull
    private final String number;

    @Nonnull
    private final Long clientId;

    @PersistenceCreator
    private Phone(Long id, String number, Long clientId) {
        this.id = id;
        this.number = number;
        this.clientId = clientId;
    }

    public Phone(String number) {
        this(null, number, null);
    }

}
