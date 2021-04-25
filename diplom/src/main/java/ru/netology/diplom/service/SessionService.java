package ru.netology.diplom.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.netology.diplom.model.Session;
import ru.netology.diplom.repository.SessionRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;


    public String getLoginByToken(String token) {
        return sessionRepository.getLoginByToken(token);
    }

    public void saveSession(Session session) {
        sessionRepository.saveSession(session);
    }

    public boolean checkSessionRepository(Session session) {
        return sessionRepository.checkSessionRepository(session);
    }

    public void deleteSession(Session session) {
        sessionRepository.deleteSession(session);
    }

    public List<Session> getAll() {
        return sessionRepository.getSessionsRepository();
    }

    public Session getSessionByToken(String token) {
        return sessionRepository.getSessionByToken(token);
    }

    public String getUserNameByToken(String token) {
        return sessionRepository.getUserNameByToken(token);
    }


}
