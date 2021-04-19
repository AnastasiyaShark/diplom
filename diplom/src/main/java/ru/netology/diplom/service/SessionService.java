package ru.netology.diplom.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.netology.diplom.model.Session;
import ru.netology.diplom.repository.SessionRepository;
import ru.netology.diplom.security.JwtResponse;

import java.util.List;

@Component
public class SessionService {

    @Autowired
    SessionRepository sessionRepository;

    public void saveSession(Session session) {
        sessionRepository.saveSession(session);
    }

    public boolean checkSessionRepository(Session session) {
       return sessionRepository.checkSessionRepository(session);
    }

    public void deleteSession (Session session){
        sessionRepository.deleteSession(session);
    }

    public List<Session> getAll (){
       return sessionRepository.getSessionsRepository();
    }


    public Session getSessionByToken(String token) {
       return sessionRepository.getSessionByToken(token);
    }
}
