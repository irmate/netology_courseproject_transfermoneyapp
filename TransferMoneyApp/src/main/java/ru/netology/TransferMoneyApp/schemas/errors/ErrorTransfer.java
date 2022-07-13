package ru.netology.TransferMoneyApp.schemas.errors;

import lombok.Data;

import java.util.Random;

@Data
public class ErrorTransfer {
    private String message;
    private int id;

    public ErrorTransfer() {
        message = "Попробуйте осуществить перевод еще раз";
        id = new Random().nextInt(1000000);
    }
}