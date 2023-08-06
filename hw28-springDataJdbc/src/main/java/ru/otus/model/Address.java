package ru.otus.model;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Table("address")
public class Address {

    @Id
    private final Long id;

    @Nonnull
    private final String street;

    @Nonnull
    private final Long clientId;

    @PersistenceCreator
    private Address(Long id, String street, Long clientId) {
        this.id = id;
        this.street = street;
        this.clientId = clientId;
    }

    public Address(String street) {
        this(null, street, null);
    }

}
