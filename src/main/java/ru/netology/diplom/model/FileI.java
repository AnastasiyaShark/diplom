package ru.netology.diplom.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "Files")
public class FileI {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String originalName;
    private String generatedName;
    //путь к файлу на сервере
    private String path;
    private int size;
    private String usersLogin;


    public FileI(Integer id, String originalName, String generatedName, String path, int size, String usersLogin) {
        this.id = id;
        this.originalName = originalName;
        this.generatedName = generatedName;
        this.path = path;
        this.size = size;
        this.usersLogin = usersLogin;
    }

    public FileI() {
    }

    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", originalName='" + originalName + '\'' +
                ", generatedName='" + generatedName + '\'' +
                ", path='" + path + '\'' +
                ", size=" + size +
                ", usersLogin='" + usersLogin + '\'' +
                '}';
    }
}
