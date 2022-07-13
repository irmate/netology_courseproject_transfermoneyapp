package ru.netology.TransferMoneyApp.repositorys;

import lombok.Data;
import org.springframework.stereotype.Repository;
import ru.netology.TransferMoneyApp.exceptions.TransactionNotValidException;
import ru.netology.TransferMoneyApp.schemas.data.TransferRequest;
import ru.netology.TransferMoneyApp.schemas.data.Transaction;
import ru.netology.TransferMoneyApp.services.CardService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Data
public class AppRepository {
    private Map<String, Transaction> transactionMap;
    private CardService cardService;

    public AppRepository(CardService cardService) {
        transactionMap = new ConcurrentHashMap();
        this.cardService = cardService;
    }

    public String create(TransferRequest transferRequest) {
        if (cardService.validCard(transferRequest)) {
            var transaction = new Transaction(transferRequest);
            var operationId = transaction.getOperationId();
            transactionMap.put(operationId, transaction);
            return operationId;
        } else {
            throw new TransactionNotValidException();
        }
    }

    public void transferMoney(String operationId) {
        var transaction = transactionMap.get(operationId);
        cardService.transfer(transaction);
    }
}