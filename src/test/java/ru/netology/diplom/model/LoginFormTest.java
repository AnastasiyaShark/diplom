package ru.netology.diplom.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class LoginFormTest {

    String login = "User1";
    String password = "password1";

    @Test
    public void testDeSerializingWithJsonCreatorLoginForm() throws IOException {
        String jsonString = String.format("{\"login\": \"%s\", \"password\": \"%s\"}", login,password);

        ObjectMapper mapper = new ObjectMapper();
        LoginForm loginForm = mapper.readValue(jsonString, LoginForm.class);
        assertThat(loginForm.getLogin(), equalTo(login));
        assertThat(loginForm.getPassword(), equalTo(password));
    }

}
