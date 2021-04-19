package ru.netology.diplom.exeption;
//401
public class ErrorUnauthorized extends RuntimeException{

    public ErrorUnauthorized(String msg) {
        super(msg);
    }
}
