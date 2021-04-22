package ru.netology.diplom.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "Files")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    //путь к файлу на сервере
    private String path;
    private String usersLogin;


    public File(Integer id, String name, String path,String usersLogin) {//
        this.id = id;
        this.name = name;
        this.path = path;
        this.usersLogin = usersLogin;

    }

    public File() {
    }


    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", usersLogin='" + usersLogin + '\'' +
                '}';
    }
}
