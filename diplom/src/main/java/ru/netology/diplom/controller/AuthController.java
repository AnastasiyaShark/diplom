package ru.netology.diplom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.bind.annotation.*;

import ru.netology.diplom.exeption.ErrorInputData;
import ru.netology.diplom.exeption.ErrorUnauthorized;
import ru.netology.diplom.model.Session;
import ru.netology.diplom.repository.UserRepository;
import ru.netology.diplom.security.JwtAuthTokenFilter;
import ru.netology.diplom.model.JwtResponse;
import ru.netology.diplom.security.JwtProvider;
import ru.netology.diplom.model.LoginForm;
import ru.netology.diplom.service.SessionService;


import javax.servlet.http.HttpServletRequest;


@RestController
public class AuthController {

    @Autowired
    SessionService sessionService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;


    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    JwtAuthTokenFilter jwtAuthTokenFilter;


    @PostMapping("/login")
    public JwtResponse authenticateUser(@RequestBody LoginForm loginRequest) {
        //конвертирует user'a
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getLogin(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //получаем токен
        String jwt = jwtProvider.generateJwtToken(authentication);

        Session newSession = new Session(loginRequest.getLogin(), jwt);

        //проверяем,есть ли уже такая сессия (login and token)
        if (!sessionService.checkSessionRepository(newSession)) {
           throw new ErrorInputData(String.format(
                   "Невозможно войти! Сессия для пользователя с login = %s уже существует",loginRequest.getLogin()));
        }
        //сохраняем сессию
        sessionService.saveSession(newSession);
        System.out.println(sessionService.getAll());
        return new JwtResponse(jwt);
    }



    @PostMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request) {
        String authHeader = request.getHeader("auth-token");
        String newAuthHeader =  authHeader.replace("Bearer ", "");
        sessionService.deleteSession(sessionService.getSessionByToken(newAuthHeader));
        System.out.println(sessionService.getAll());
        return ResponseEntity.ok().body("Success logout");

    }


    @ExceptionHandler(ErrorInputData.class)
    public ResponseEntity<String> errorInputDataHandler(ErrorInputData e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ErrorUnauthorized.class)
    public ResponseEntity<String> errorUnauthorized(ErrorUnauthorized e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }




}
