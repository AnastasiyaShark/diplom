package ru.netology.diplom.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.netology.diplom.model.FileI;

import java.util.Optional;


@Repository
public interface FileRepository extends JpaRepository<FileI, Integer> {

    Optional<FileI> findFileByGeneratedName(String name);

    FileI findFileIByGeneratedName(String name);

    @Transactional
    void deleteFileByGeneratedName(String name);

}
