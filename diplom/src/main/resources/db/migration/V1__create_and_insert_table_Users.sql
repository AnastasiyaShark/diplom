DROP TABLE IF EXISTS Users;
CREATE TABLE Users
(
    id       serial primary key  not null ,
    login    varchar(10) UNIQUE,
    password varchar(100)
);
CREATE EXTENSION pgcrypto;
INSERT INTO Users (login, password) VALUES ('User1',crypt('password1',gen_salt('bf')));
INSERT INTO Users (login, password) VALUES ('User2',crypt('password2',gen_salt('bf')));
INSERT INTO Users (login, password) VALUES ('User3',crypt('password3',gen_salt('bf')));
INSERT INTO Users (login, password) VALUES ('User4',crypt('password4',gen_salt('bf')));
INSERT INTO Users (login, password) VALUES ('User5',crypt('password5',gen_salt('bf')));


-- Когда установлено ограничение UNIQUE, каждый раз,
-- когда вы вставляете новую строку, оно проверяет, есть ли уже значение в таблице.