package ru.netology.TransferMoneyApp.services;

import ru.netology.TransferMoneyApp.schemas.data.TransferRequest;
import ru.netology.TransferMoneyApp.schemas.sucsess.SuccessConfirmation;
import ru.netology.TransferMoneyApp.schemas.sucsess.SuccessTransfer;

public interface TransferService {
    SuccessTransfer transferMoneyCardToCard(TransferRequest transferRequest);
    SuccessConfirmation confirmOperation();
}