create table payments
(
    id            bigint not null auto_increment,
    amount_value  decimal(38, 2),
    currency_code varchar(255),
    note          varchar(255),
    primary key (id)
) engine=InnoDB
