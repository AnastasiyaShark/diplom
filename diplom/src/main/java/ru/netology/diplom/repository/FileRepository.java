package ru.netology.diplom.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.netology.diplom.model.File;

import java.util.List;


@Repository
public interface FileRepository extends JpaRepository<File, Integer> {

    @Override
    List<File> findAll();
}
