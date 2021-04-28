package ru.netology.diplom.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.netology.diplom.exeption.ErrorInputData;
import ru.netology.diplom.model.JwtResponse;
import ru.netology.diplom.model.LoginForm;
import ru.netology.diplom.model.Session;
import ru.netology.diplom.repository.UserRepository;
import ru.netology.diplom.security.JwtAuthTokenFilter;
import ru.netology.diplom.security.JwtProvider;

import javax.servlet.http.HttpServletRequest;
@Component
@Service
@RequiredArgsConstructor
public class AuthService {

    private final SessionService sessionService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtProvider jwtProvider;
    private final JwtAuthTokenFilter jwtAuthTokenFilter;
    private final FilesStorageService storageService;


    public JwtResponse authenticateUser(LoginForm loginRequest) {
        //конвертирует user'a
        System.out.println("2");
        System.out.println(loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getLogin(),
                        loginRequest.getPassword()
                )
        );
        System.out.println("3");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //получаем токен
        String jwt = jwtProvider.generateJwtToken(authentication);

        Session newSession = new Session(loginRequest.getLogin(), jwt);

        //проверяем,есть ли уже такая сессия (login and token)
        if (!sessionService.checkSessionRepository(newSession)) {
            throw new ErrorInputData(String.format(
                    "Невозможно войти! Сессия для пользователя с login = %s уже существует", loginRequest.getLogin()));
        }
        //сохраняем сессию
        sessionService.saveSession(newSession);
        storageService.chekAndCreateFolder();
        return new JwtResponse(jwt);
    }


    public ResponseEntity logout(HttpServletRequest request) {
        String authHeader = request.getHeader("auth-token");
        String newAuthHeader = authHeader.replace("Bearer ", "");
        sessionService.deleteSession(sessionService.getSessionByToken(newAuthHeader));
        return ResponseEntity.ok().body("Success logout");

    }
}
