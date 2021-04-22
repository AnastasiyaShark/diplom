package ru.netology.diplom.exeption;

public class InternalServerError extends RuntimeException {
    public InternalServerError(String msg) {
        super(msg);
    }
}
