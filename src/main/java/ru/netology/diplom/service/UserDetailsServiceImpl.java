package ru.netology.diplom.service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.netology.diplom.model.User;
import ru.netology.diplom.repository.UserRepository;

//Интерфейс UserDetailsService имеет метод загрузки пользователя по имени(login) пользователя и возвращает объект UserDetails,
// который Spring Security может использовать для аутентификации и проверки.
//ищем пр login
@Service
@Component

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userRepository.findUserByLogin(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User Not Found with -> login : " + username)
                );
        return UserPrinciple.build(user);
    }

}
