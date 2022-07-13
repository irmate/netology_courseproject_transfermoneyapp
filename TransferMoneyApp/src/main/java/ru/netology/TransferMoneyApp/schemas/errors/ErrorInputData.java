package ru.netology.TransferMoneyApp.schemas.errors;

import lombok.Data;

import java.util.Random;

@Data
public class ErrorInputData {
    private String message;
    private int id;

    public ErrorInputData() {
        message = "Ошибка ввода данных";
        id = new Random().nextInt(100000);
    }
}