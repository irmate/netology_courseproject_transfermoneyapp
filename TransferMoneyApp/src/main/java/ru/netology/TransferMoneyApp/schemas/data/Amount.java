package ru.netology.TransferMoneyApp.schemas.data;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

@Data
public class Amount {
    @Positive
    @Min(1)
    int value;
    String currency;
}