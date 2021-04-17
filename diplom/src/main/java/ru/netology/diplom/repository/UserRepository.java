package ru.netology.diplom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.netology.diplom.model.User;

import java.util.Optional;

//@Repository
//public interface UserRepository extends JpaRepository {
//    Optional findByUsername(String username);
//}

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findUserByLogin(String login);
}