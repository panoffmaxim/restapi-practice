create table hibernate_sequence
(
    next_val bigint
) engine=MyISAM;

insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );

CREATE TABLE client (
                      id BIGINT NOT NULL AUTO_INCREMENT,
                      clientName varchar(64) not null unique,
                      phone varchar(64) not null,
                      PRIMARY KEY (id)
) engine=MyISAM;

CREATE TABLE orders (
                      id BIGINT NOT NULL AUTO_INCREMENT,
                      clientName varchar(64) not null,
                      deliveryInf varchar(64) not null,
                      completed BOOLEAN NOT NULL,
                      client_id BIGINT,
                      FOREIGN KEY (`client_id`) REFERENCES `client` (`id`),
                      PRIMARY KEY (id)
) engine=MyISAM;