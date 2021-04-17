package ru.netology.diplom.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.netology.diplom.model.User;
import ru.netology.diplom.repository.UserRepository;
import ru.netology.diplom.security.JwtAuthTokenFilter;

import java.util.Optional;

//Интерфейс UserDetailsService имеет метод загрузки пользователя по имени(login) пользователя и возвращает объект UserDetails,
// который Spring Security может использовать для аутентификации и проверки.
//ищем пр login
@Service
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        System.out.println(username);
        logger.info(username);
        User user = userRepository.findUserByLogin(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User Not Found with -> username or email : " + username)
                );
logger.info("jkhjkhkS" + user);
        return UserPrinciple.build(user);
    }




//    @Autowired
//    UserRepository userRepository;
//
//
//
//    @Override
//    @Transactional
//    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
//        User user = userRepository.findUserByLogin(login)
//                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with login: " + login));
//        return UserPrinciple.build(user);
//    }
}
