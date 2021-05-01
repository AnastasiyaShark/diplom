package ru.netology.diplom;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import ru.netology.diplom.model.*;
import ru.netology.diplom.repository.SessionRepository;
import ru.netology.diplom.security.JwtProvider;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;
import static org.junit.Assert.assertNull;

public class UnitTests {


    private static final String login = "User2";
    private static final String password = "password2";
    private static final String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJVc2VyMiJ9.VEgjm0RsXKBEmxxh6rZ5tFaHDZCT" +
            "sddIhwofdkr4C4hy5g43t1MWHCCwQZ21woIkzQuus66dpMs_jImF5CeuVQ";
    private static final Session session = new Session(login, token);
    private static final String fileName = "image.jpg";
    private static final int size = 1256;

    @Test
    public void testDeSerializingWithJsonCreatorJwtResponse() throws IOException {
        String jsonString = String.format("{\"auth-token\": \"%s\"}", token);

        ObjectMapper mapper = new ObjectMapper();
        JwtResponse jwtResponse = mapper.readValue(jsonString, JwtResponse.class);
        assertThat(jwtResponse.getToken(), equalTo(token));
    }

    @Test
    public void testValidateJwtToken() {
        JwtProvider jwtProvider = new JwtProvider();
        assertTrue(jwtProvider.validateJwtToken(token));

    }

    @Test
    public void testNotValidateJwtToken() {
        JwtProvider jwtProvider = new JwtProvider();
        assertFalse(jwtProvider.validateJwtToken(token + "not"));

    }

    @Test
    public void testGetUserNameFromJwtToken() {
        JwtProvider jwtProvider = new JwtProvider();
        assertThat(jwtProvider.getUserNameFromJwtToken(token), equalTo("User2"));

    }


    @Test
    public void testDeSerializingWithJsonCreatorListResponse() throws IOException {
        String jsonString = String.format("{\"filename\": \"%s\", \"size\": \"%d\"}", fileName, size);

        ObjectMapper mapper = new ObjectMapper();
        ListResponse listResponse = mapper.readValue(jsonString, ListResponse.class);
        assertThat(listResponse.getFileName(), equalTo(fileName));
        assertThat(listResponse.getSize(), equalTo(size));
    }


    @Test
    public void testDeSerializingWithJsonCreatorLoginForm() throws IOException {
        String jsonString = String.format("{\"login\": \"%s\", \"password\": \"%s\"}", login, password);

        ObjectMapper mapper = new ObjectMapper();
        LoginForm loginForm = mapper.readValue(jsonString, LoginForm.class);
        assertThat(loginForm.getLogin(), equalTo(login));
        assertThat(loginForm.getPassword(), equalTo(password));
    }


    @org.junit.Test
    public void testCheckSessionRepository() {
        SessionRepository sessionRepository = new SessionRepository();
        sessionRepository.saveSession(session);
        assertFalse(sessionRepository.checkSessionRepository(session));
        assertTrue(sessionRepository.checkSessionRepository(new Session("null", "null")));
    }

    @org.junit.Test
    public void testGetLoginByToken() {
        SessionRepository sessionRepository = new SessionRepository();
        sessionRepository.saveSession(session);
        assertThat(sessionRepository.getLoginByToken(session.getToken()), equalTo(session.getLogin()));
    }

    @org.junit.Test
    public void testGetLoginByTokenReturnNull() {
        SessionRepository sessionRepository = new SessionRepository();
        assertNull(sessionRepository.getLoginByToken(session.getToken()));
    }

    @org.junit.Test
    public void testGetSessionByToken() {
        SessionRepository sessionRepository = new SessionRepository();
        sessionRepository.saveSession(session);
        assertThat(sessionRepository.getSessionByToken(session.getToken()), equalTo(session));
    }

    @org.junit.Test
    public void testGetSessionByTokenReturnNull() {
        SessionRepository sessionRepository = new SessionRepository();
        assertNull(sessionRepository.getSessionByToken(session.getToken()));
    }


    @org.junit.Test
    public void testGetUserNameByToken() {
        SessionRepository sessionRepository = new SessionRepository();
        sessionRepository.saveSession(session);
        assertThat(sessionRepository.getUserNameByToken(session.getToken()), equalTo(session.getLogin()));
    }

    @org.junit.Test
    public void testGetUserNameByTokenReturnNull() {
        SessionRepository sessionRepository = new SessionRepository();

        assertNull(sessionRepository.getUserNameByToken(session.getToken()));
    }


    @Test
    public void testDeSerializingWithJsonCreatorRequestNewName() throws IOException {
        String jsonString = String.format("{\"filename\": \"%s\"}", fileName);

        ObjectMapper mapper = new ObjectMapper();
        RequestNewName requestNewName = mapper.readValue(jsonString, RequestNewName.class);
        assertThat(requestNewName.getFilename(), equalTo(fileName));
    }

}
