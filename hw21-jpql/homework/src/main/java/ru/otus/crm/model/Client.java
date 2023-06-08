package ru.otus.crm.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "client")
public class Client implements Cloneable {

    @Id
    @SequenceGenerator(name = "client_gen", sequenceName = "client_seq",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_gen")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Phone> phones;


    public Client(Long id, String name, Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;

        var addressCloned = address.clone();
        addressCloned.setClient(this);
        this.address = addressCloned;

        var phonesCloned = List.copyOf(phones);
        phonesCloned.forEach(phone -> phone.setClient(this));
        this.phones = phonesCloned;
    }

    @Override
    public Client clone() {
        List<Phone> clonedPhones = new ArrayList<>();
        phones.stream().forEach(phone -> clonedPhones.add(phone.clone()));
        return new Client(this.id, this.name, this.address.clone(), clonedPhones);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", phones=" + phones +
                '}';
    }
}
