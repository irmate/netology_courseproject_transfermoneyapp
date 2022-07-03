package ru.netology.TransferMoneyApp;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import ru.netology.TransferMoneyApp.schemas.data.Amount;
import ru.netology.TransferMoneyApp.schemas.data.TransferRequest;
import ru.netology.TransferMoneyApp.schemas.sucsess.SuccessConfirmation;
import ru.netology.TransferMoneyApp.schemas.sucsess.SuccessTransfer;
import ru.netology.TransferMoneyApp.services.TransferService;
import ru.netology.TransferMoneyApp.services.TransferServiceImpl;
import ru.netology.TransferMoneyApp.utils.ValidDataCheckerImpl;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.*;

class TransferMoneyApplicationUnitTests {

    private static TransferService TSsuite;
    private static ValidDataCheckerImpl VDCsuite;


    @BeforeAll
    static void init() {
        TSsuite = new TransferServiceImpl();
        VDCsuite = new ValidDataCheckerImpl();
    }

    @Test
    public void transferMoneyCardToCardTest() {
        TransferRequest testTransferRequest = new TransferRequest();
        testTransferRequest.setCardFromNumber("1111111111111111");
        testTransferRequest.setCardFromValidTill("09/24");
        testTransferRequest.setCardFromCVV("232");
        testTransferRequest.setCardToNumber("2222222222222222");
        testTransferRequest.setAmount(new Amount());
        testTransferRequest.getAmount().setValue(4000);
        testTransferRequest.getAmount().setCurrency("RUR");

        var testResult = TSsuite.transferMoneyCardToCard(testTransferRequest);

        assertNotNull(testResult);
        assertEquals(testResult.getClass(), SuccessTransfer.class);
        assertNotNull(testResult.getOperationId());

    }

    @Test
    public void transferMoneyCardToCardExceptionTest() {
        assertThrows(NullPointerException.class, () -> TSsuite.transferMoneyCardToCard(null));
        assertThrows(ClassCastException.class, () -> TSsuite.transferMoneyCardToCard((TransferRequest) new Object()));
    }

    @Test
    public void confirmOperationTest() {
        var testResult = TSsuite.confirmOperation();

        assertNotNull(testResult);
        assertEquals(testResult.getClass(), SuccessConfirmation.class);
        assertNotNull(testResult.getOperationId());
    }

    @ParameterizedTest
    @CsvSource({"12/, 54/34, 09/45"})
    void isValidTest(String expected1, String expected2, String expected3) {
        ConstraintValidatorContext context = Mockito.mock(ConstraintValidatorContext.class);

        assertFalse(VDCsuite.isValid(expected1, context));
        assertFalse(VDCsuite.isValid(expected2, context));
        assertTrue(VDCsuite.isValid(expected3, context));
    }
}