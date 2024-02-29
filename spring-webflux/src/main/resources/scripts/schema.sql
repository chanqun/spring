DROP TABLE IF EXISTS book;

create table book(
    id bigint not null auto_increment,
    name varchar(50),
    price int,
    primary key (id)
);
