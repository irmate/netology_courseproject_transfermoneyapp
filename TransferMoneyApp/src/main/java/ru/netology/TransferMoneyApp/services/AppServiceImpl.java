package ru.netology.TransferMoneyApp.services;

import org.springframework.stereotype.Service;
import ru.netology.TransferMoneyApp.exceptions.ConfirmTransactionNotValidException;
import ru.netology.TransferMoneyApp.repositorys.AppRepository;
import ru.netology.TransferMoneyApp.schemas.data.ConfirmRequest;
import ru.netology.TransferMoneyApp.schemas.data.TransferRequest;
import ru.netology.TransferMoneyApp.schemas.succsess.SuccessConfirmation;
import ru.netology.TransferMoneyApp.schemas.succsess.SuccessTransfer;

@Service
public class AppServiceImpl implements AppService {
    private final String temporaryConfirmationCode = "0000";
    private final AppRepository appRepository;

    public AppServiceImpl(AppRepository appRepository) {
        this.appRepository = appRepository;
    }

    @Override
    public SuccessTransfer transferMoneyCardToCard(TransferRequest transferRequest) {
        var operationId = appRepository.create(transferRequest);
        var successTransfer = new SuccessTransfer();
        successTransfer.setOperationId(operationId);
        return successTransfer;
    }

    @Override
    public SuccessConfirmation confirmOperation(ConfirmRequest confirmRequest) {
        var operationId = confirmRequest.getOperationId();
        var operationCode = confirmRequest.getCode();
        if (!operationCode.equals(temporaryConfirmationCode)) {
            throw new ConfirmTransactionNotValidException();
        }
        appRepository.transferMoney(operationId);
        var successConfirmation = new SuccessConfirmation();
        successConfirmation.setOperationId(operationId);
        return successConfirmation;
    }
}