package ru.netology.TransferMoneyApp.services;

import ru.netology.TransferMoneyApp.schemas.data.ConfirmRequest;
import ru.netology.TransferMoneyApp.schemas.data.TransferRequest;
import ru.netology.TransferMoneyApp.schemas.succsess.SuccessConfirmation;
import ru.netology.TransferMoneyApp.schemas.succsess.SuccessTransfer;

public interface AppService {
    SuccessTransfer transferMoneyCardToCard(TransferRequest transferRequest);
    SuccessConfirmation confirmOperation(ConfirmRequest confirmRequest);
}