package ru.netology.diplom.repository;

import org.springframework.stereotype.Component;
import ru.netology.diplom.model.Session;

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


    public String getLoginByToken(String token) {
        for (Session session : sessionsRepository) {
            if (session.getToken().equals(token)) {
                return session.getLogin();
            }
        }
        return null;
    }

    public Session getSessionByToken(String token) {
        for (Session session : sessionsRepository) {
            if (session.getToken().equals(token)) {
                return session;
            }
        }
        return null;
    }

    public String getUserNameByToken(String token) {
        for (Session session : sessionsRepository) {
            if (session.getToken().equals(token)) {
                return session.getLogin();
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
