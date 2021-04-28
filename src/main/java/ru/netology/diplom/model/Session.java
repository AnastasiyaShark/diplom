package ru.netology.diplom.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Session {

    private String login;
    private String token;

    public Session(String login, String token) {
        this.login = login;
        this.token = token;
    }

    @Override
    public String toString() {
        return "Session{" +
                "login='" + login + '\'' +
                ", token=" + token +
                '}';
    }

}
