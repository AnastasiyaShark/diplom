package ru.netology.diplom.controller;

import lombok.RequiredArgsConstructor;
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
import ru.netology.diplom.service.AuthService;
import ru.netology.diplom.service.FilesStorageService;
import ru.netology.diplom.service.SessionService;


import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class AuthController {

private final AuthService authService;

    @PostMapping("/login")
    public JwtResponse authenticateUser(@RequestBody LoginForm loginRequest) {
       return authService.authenticateUser(loginRequest);
    }


    @PostMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request) {
        return authService.logout(request);

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
