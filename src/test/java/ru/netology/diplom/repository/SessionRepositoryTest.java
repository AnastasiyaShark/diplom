package ru.netology.diplom.repository;

import org.junit.Test;


import ru.netology.diplom.model.Session;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

public class SessionRepositoryTest {


    Session session = new Session("User2",
            "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJVc2VyMiJ9.VEgjm0RsXKBEmxxh6rZ5tFaHDZCTsddIhwofdkr4C4hy5g43t1MWHCCwQZ21woIkzQuus66dpMs_jImF5CeuVQ");


    @Test
    public void testCheckSessionRepository() {
        SessionRepository sessionRepository = new SessionRepository();
        sessionRepository.saveSession(session);
        assertFalse(sessionRepository.checkSessionRepository(session));
        assertTrue(sessionRepository.checkSessionRepository(new Session("null", "null")));
    }

    @Test
    public void testGetLoginByToken() {
        SessionRepository sessionRepository = new SessionRepository();
        sessionRepository.saveSession(session);
        assertThat(sessionRepository.getLoginByToken(session.getToken()), equalTo(session.getLogin()));
    }

    @Test
    public void testGetLoginByTokenReturnNull() {
        SessionRepository sessionRepository = new SessionRepository();
        assertNull(sessionRepository.getLoginByToken(session.getToken()));
    }

    @Test
    public void testGetSessionByToken() {
        SessionRepository sessionRepository = new SessionRepository();
        sessionRepository.saveSession(session);
        assertThat(sessionRepository.getSessionByToken(session.getToken()), equalTo(session));
    }

    @Test
    public void testGetSessionByTokenReturnNull() {
        SessionRepository sessionRepository = new SessionRepository();
        assertNull(sessionRepository.getSessionByToken(session.getToken()));
    }


    @Test
    public void testGetUserNameByToken() {
        SessionRepository sessionRepository = new SessionRepository();
        sessionRepository.saveSession(session);
        assertThat(sessionRepository.getUserNameByToken(session.getToken()), equalTo(session.getLogin()));
    }

    @Test
    public void testGetUserNameByTokenReturnNull() {
        SessionRepository sessionRepository = new SessionRepository();

        assertNull(sessionRepository.getUserNameByToken(session.getToken()));
    }


}
