package ru.netology.TransferMoneyApp.schemas.sucsess;

import lombok.Data;

import java.util.Random;

@Data
public class SuccessTransfer {
    private String operationId;

    public SuccessTransfer(){
        operationId = String.valueOf(new Random().nextInt(1000000));
    }
}