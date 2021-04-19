package ru.netology.diplom.security;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginForm {

    private String login;
    private String password;


    @JsonCreator
    public LoginForm(@JsonProperty("login") String login,
                     @JsonProperty("password") String password) {
        this.login = login;
        this.password = password;
    }

}
