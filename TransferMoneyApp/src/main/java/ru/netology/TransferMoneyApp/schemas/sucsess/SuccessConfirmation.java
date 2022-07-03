package ru.netology.TransferMoneyApp.schemas.sucsess;

import lombok.Data;

import java.util.Random;

@Data
public class SuccessConfirmation {
    private String operationId;

    public SuccessConfirmation() {
        operationId = String.valueOf(new Random().nextInt(1000000));
    }
}