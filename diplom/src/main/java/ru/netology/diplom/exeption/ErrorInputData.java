package ru.netology.diplom.exeption;

public class ErrorInputData extends RuntimeException {
//400
    public ErrorInputData(String msg) {
        super(msg);

    }
}
