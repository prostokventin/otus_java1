alter table client
add column address_id bigint;

alter table if exists client add constraint address_rk foreign key (address_id) references address;
alter table if exists phone add constraint client_fk foreign key (client_id) references client;
