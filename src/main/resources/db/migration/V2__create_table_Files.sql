DROP TABLE IF EXISTS Files;
CREATE TABLE Files
(
    id             serial primary key not null,
    original_name  varchar(100),
    generated_name varchar(100),
    path           varchar(200),
    size           int,
    users_login    varchar(10)        not null,
    foreign key (users_login) REFERENCES Users (login)
);