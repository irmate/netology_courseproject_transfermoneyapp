package ru.netology.TransferMoneyApp.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.netology.TransferMoneyApp.schemas.data.TransferRequest;
import ru.netology.TransferMoneyApp.schemas.sucsess.SuccessConfirmation;
import ru.netology.TransferMoneyApp.schemas.sucsess.SuccessTransfer;

@Service
@Slf4j
public class TransferServiceImpl implements TransferService{

    @Override
    public SuccessTransfer transferMoneyCardToCard(TransferRequest transferRequest) {
        log.info("Перевод выполнен успешно! номер карты списания: {} номер карты зачисления: {} зачислено: {} {} размер комиссия за операцию: {} {}",
                transferRequest.getCardFromNumber(), transferRequest.getCardToNumber(), transferRequest.getAmount().getValue(),
                transferRequest.getAmount().getCurrency(), transferRequest.getAmount().getValue() * 0.001, transferRequest.getAmount().getCurrency());
        return new SuccessTransfer();
    }

    @Override
    public SuccessConfirmation confirmOperation() {
        return new SuccessConfirmation();
    }
}