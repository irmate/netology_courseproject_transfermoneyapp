package ru.netology.TransferMoneyApp.schemas.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.netology.TransferMoneyApp.utils.ValidDataChecker;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class TransferRequest {
    @NotBlank
    @NotEmpty
    @Size(min = 16)
    private String cardFromNumber;
    @ValidDataChecker
    private String cardFromValidTill;
    @NotBlank
    @NotEmpty
    @Size(min = 3)
    private String cardFromCVV;
    @NotBlank
    @NotEmpty
    @Size(min = 16)
    private String cardToNumber;
    @JsonProperty
    @Valid
    private Amount amount;
}