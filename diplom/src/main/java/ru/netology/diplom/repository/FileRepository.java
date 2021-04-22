package ru.netology.diplom.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.netology.diplom.model.File;


@Repository
public interface FileRepository extends JpaRepository<File, Integer> {


}
