package ru.netology.TransferMoneyApp.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.netology.TransferMoneyApp.exceptions.ConfirmTransactionNotValidException;
import ru.netology.TransferMoneyApp.exceptions.TransactionNotValidException;
import ru.netology.TransferMoneyApp.schemas.errors.ErrorInputData;
import ru.netology.TransferMoneyApp.schemas.errors.ErrorTransfer;

@RestControllerAdvice
@Slf4j
public class ErrorHandlingControllerAdvice {
    @ExceptionHandler({MethodArgumentNotValidException.class, TransactionNotValidException.class, ConfirmTransactionNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorInputData handleMethodArgumentNotValidException() {
        log.error("Полученные данные не валидны");
        return new ErrorInputData();
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorTransfer handleRuntimeException(RuntimeException e) {
        log.error("Ошибка на сервере: {}", e.getStackTrace());
        return new ErrorTransfer();
    }
}