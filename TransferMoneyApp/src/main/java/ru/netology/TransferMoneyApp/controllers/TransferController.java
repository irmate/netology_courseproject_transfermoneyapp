package ru.netology.TransferMoneyApp.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.netology.TransferMoneyApp.schemas.data.ConfirmRequest;
import ru.netology.TransferMoneyApp.schemas.data.TransferRequest;
import ru.netology.TransferMoneyApp.schemas.sucsess.SuccessConfirmation;
import ru.netology.TransferMoneyApp.services.TransferServiceImpl;
import ru.netology.TransferMoneyApp.schemas.sucsess.SuccessTransfer;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
public class TransferController {
    TransferServiceImpl service;

    public TransferController(TransferServiceImpl service) {
        this.service = service;
    }

    @CrossOrigin(origins = "http://localhost:3000/")
    @PostMapping("/transfer")
    public ResponseEntity<SuccessTransfer> transferMoneyCardToCard(@Valid @RequestBody TransferRequest transferRequest) {
        var successTransfer = service.transferMoneyCardToCard(transferRequest);
        return new ResponseEntity<>(successTransfer, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000/")
    @PostMapping("/confirmOperation")
    public ResponseEntity<SuccessConfirmation> confirmOperation(@RequestBody ConfirmRequest confirmRequest) {
        var successConfirmation = service.confirmOperation();
        return new ResponseEntity<>(successConfirmation, HttpStatus.OK);
    }
}