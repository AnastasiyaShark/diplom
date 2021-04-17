package ru.netology.diplom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.netology.diplom.repository.UserRepository;
import ru.netology.diplom.security.JwtResponse;
import ru.netology.diplom.security.JwtProvider;
import ru.netology.diplom.security.LoginForm;



@RestController
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;


    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtProvider jwtProvider;

    @CrossOrigin(origins = "*")
    @PostMapping("/login")
    public JwtResponse authenticateUser( @RequestBody LoginForm loginRequest) {
        System.out.println(loginRequest.getLogin()+ " "+ loginRequest.getPassword());
        System.out.println("HERE1");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getLogin(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        System.out.println("HERE254125");
        return new JwtResponse(jwt);
    }
    @CrossOrigin(origins = "*")
    @GetMapping ("/enter")
    public JwtResponse authenticateUser(  @RequestParam ("login") String login,
                                          @RequestParam ("pass") String pass) {
        System.out.println(login + " " + pass);

        return new JwtResponse("hg");
    }
}
