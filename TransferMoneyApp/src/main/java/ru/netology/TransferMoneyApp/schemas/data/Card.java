package ru.netology.TransferMoneyApp.schemas.data;

import lombok.Data;

@Data
public class Card {
    String cardNumber;
    String cardValidDate;
    String cardCVV;
    Amount amount;

    public Card(String cardNumber, String cardValidDate, String cardCVV, Amount amount) {
        this.cardNumber = cardNumber;
        this.cardValidDate = cardValidDate;
        this.cardCVV = cardCVV;
        this.amount = amount;
    }
}