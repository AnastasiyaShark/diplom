package ru.netology.diplom.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.netology.diplom.model.File;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;


@Repository
public interface FileRepository extends JpaRepository<File, Integer> {

    Optional<File> findFileByGeneratedName(String name);

    @Transactional
    void deleteFileByGeneratedName(String name);

}
