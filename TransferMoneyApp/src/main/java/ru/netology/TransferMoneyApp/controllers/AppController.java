package ru.netology.TransferMoneyApp.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.netology.TransferMoneyApp.schemas.data.ConfirmRequest;
import ru.netology.TransferMoneyApp.schemas.data.TransferRequest;
import ru.netology.TransferMoneyApp.schemas.succsess.SuccessConfirmation;
import ru.netology.TransferMoneyApp.services.AppServiceImpl;
import ru.netology.TransferMoneyApp.schemas.succsess.SuccessTransfer;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
public class AppController {
    private final String ORIGIN = "http://localhost:3000/";
    private final AppServiceImpl service;

    public AppController(AppServiceImpl service) {
        this.service = service;
    }

    @CrossOrigin(origins = ORIGIN)
    @PostMapping("/transfer")
    public ResponseEntity<SuccessTransfer> transferMoneyCardToCard(@Valid @RequestBody TransferRequest transferRequest) {
        var successTransfer = service.transferMoneyCardToCard(transferRequest);
        return new ResponseEntity<>(successTransfer, HttpStatus.OK);
    }

    @CrossOrigin(origins = ORIGIN)
    @PostMapping("/confirmOperation")
    public ResponseEntity<SuccessConfirmation> confirmOperation(@RequestBody ConfirmRequest confirmRequest) {
        var successConfirmation = service.confirmOperation(confirmRequest);
        return new ResponseEntity<>(successConfirmation, HttpStatus.OK);
    }
}