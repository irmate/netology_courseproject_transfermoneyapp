package ru.netology.TransferMoneyApp.schemas.data;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Random;

@Data
public class Transaction {
    private String operationId;
    private String cardFromNumber;
    private String cardToNumber;
    private Amount amount;
    private LocalDateTime localDateTime;

    public Transaction(TransferRequest transferRequest) {
        operationId = String.valueOf(new Random().nextInt(1000000));
        cardFromNumber = transferRequest.getCardFromNumber();
        cardToNumber = transferRequest.getCardToNumber();
        amount = transferRequest.getAmount();
        localDateTime = LocalDateTime.now();
    }
}