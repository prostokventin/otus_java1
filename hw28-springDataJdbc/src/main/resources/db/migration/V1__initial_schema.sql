create table client
(
    id   bigserial not null primary key,
    name varchar(50)
);

create table address
(
    id bigserial not null primary key,
    street varchar(255) not null,
    client_id bigint not null references Client (id)
);

create table phone
(
    id bigserial not null primary key,
    number varchar(20) not null,
    client_id bigint not null references Client (id)
);


