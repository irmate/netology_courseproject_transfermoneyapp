package ru.netology.TransferMoneyApp.schemas.data;

import lombok.Data;

@Data
public class ConfirmRequest {
    private String operationId;
    private String code;
}