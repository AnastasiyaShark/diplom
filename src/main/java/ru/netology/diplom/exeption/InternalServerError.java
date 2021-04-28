package ru.netology.diplom.exeption;

//500
public class InternalServerError extends RuntimeException {
    public InternalServerError(String msg) {
        super(msg);
    }
}
