package ru.netology.diplom.exeption;

//400
public class ErrorInputData extends RuntimeException {

    public ErrorInputData(String msg) {
        super(msg);

    }
}
