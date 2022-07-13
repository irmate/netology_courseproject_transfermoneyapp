package ru.netology.TransferMoneyApp.services;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.netology.TransferMoneyApp.schemas.data.Amount;
import ru.netology.TransferMoneyApp.schemas.data.Card;
import ru.netology.TransferMoneyApp.schemas.data.TransferRequest;
import ru.netology.TransferMoneyApp.schemas.data.Transaction;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Data
@Slf4j
public class CardService {
    private final double COMMISSION_VALUE = 0.001;
    private final Map<String, Card> cardMap;

    public CardService() {
        cardMap = new ConcurrentHashMap<>();
        addCard("2222222222222222", "12/27", "123", 5000, "RUB");
        addCard("3333333333333333", "09/44", "657", 2000, "RUB");
    }

    public void addCard(String cardNumber, String cardValidDate, String cardCVV, int amountValue, String amountCurrency) {
        var amount = new Amount();
        amount.setValue(amountValue);
        amount.setCurrency(amountCurrency);
        cardMap.put(cardNumber, new Card(cardNumber, cardValidDate, cardCVV, amount));
    }

    public boolean validCard(TransferRequest transferRequest) {
        var cardFromNumber = transferRequest.getCardFromNumber();
        var cardToNumber = transferRequest.getCardToNumber();
        var cardFrom = cardMap.get(cardFromNumber);
        return
                cardFrom != null &&
                        !cardFromNumber.equals(cardToNumber) &&
                        cardMap.containsKey(cardToNumber) &&
                        cardFrom.getAmount().getValue() >= transferRequest.getAmount().getValue();
    }

    public void transfer(Transaction transaction) {
        if (transaction != null) {
            var cardFromNumber = transaction.getCardFromNumber();
            var cardToNumber = transaction.getCardToNumber();
            var transferValue = transaction.getAmount().getValue();
            var transferValueAfterCommission = transferValue * COMMISSION_VALUE;
            var cardFromAmount = cardMap.get(cardFromNumber).getAmount();
            var cardToAmount = cardMap.get(cardToNumber).getAmount();

            cardFromAmount.setValue((int) (cardFromAmount.getValue() - transferValueAfterCommission));
            cardToAmount.setValue(cardToAmount.getValue() + transferValue);

            log.info("Перевод выполнен успешно! номер карты списания: {} номер карты зачисления: {} зачислено: {} {} размер комиссия за операцию: {} {}",
                    cardFromNumber, cardToNumber, transferValue, transaction.getAmount().getCurrency(),
                    transferValueAfterCommission, transaction.getAmount().getCurrency());
        }
    }
}