package ru.netology.diplom.repository;

import org.springframework.stereotype.Component;
import ru.netology.diplom.model.Session;
import ru.netology.diplom.security.JwtResponse;

import java.util.List;
import java.util.Vector;

@Component
public class SessionRepository {

    private final List<Session> sessionsRepository;

    public SessionRepository() {
        this.sessionsRepository = new Vector<>();
    }

    public List<Session> getSessionsRepository() {
        return sessionsRepository;
    }

    public void saveSession(Session session) {
        sessionsRepository.add(session);
    }

    public boolean checkSessionRepository(Session session) {
        for (Session session1 : sessionsRepository) {
            if (session1.getToken().equals(session.getToken()) || session1.getLogin().equals(session.getLogin())) {
                return false;
            }
        }
        return true;
    }

    public void deleteSession(Session session) {
        sessionsRepository.remove(session);
    }

    public boolean checkSessionByToken(String token) {
        for (Session session : sessionsRepository) {
            if (session.getToken().equals(token)) {
                return true;
            }
        }
        return false;
    }


    public Session getSessionByToken(String token) {
        for (Session session : sessionsRepository) {
            if (session.getToken().equals(token)) {
                return session;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "SessionRepository{" +
                "sessionsRepository=" + sessionsRepository +
                '}';
    }


}
