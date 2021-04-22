DROP TABLE IF EXISTS Files;
CREATE TABLE Files
(
    id          serial primary key not null,
    name        varchar(100),
    path        varchar(200),
    users_login varchar(10) not null ,
    foreign key (users_login) REFERENCES Users (login)
);