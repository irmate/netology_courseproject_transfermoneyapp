package ru.netology.TransferMoneyApp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import ru.netology.TransferMoneyApp.exceptions.ConfirmTransactionNotValidException;
import ru.netology.TransferMoneyApp.exceptions.TransactionNotValidException;
import ru.netology.TransferMoneyApp.repositorys.AppRepository;
import ru.netology.TransferMoneyApp.schemas.data.Amount;
import ru.netology.TransferMoneyApp.schemas.data.ConfirmRequest;
import ru.netology.TransferMoneyApp.schemas.data.Transaction;
import ru.netology.TransferMoneyApp.schemas.data.TransferRequest;
import ru.netology.TransferMoneyApp.schemas.succsess.SuccessConfirmation;
import ru.netology.TransferMoneyApp.schemas.succsess.SuccessTransfer;
import ru.netology.TransferMoneyApp.services.AppService;
import ru.netology.TransferMoneyApp.services.AppServiceImpl;
import ru.netology.TransferMoneyApp.services.CardService;
import ru.netology.TransferMoneyApp.utils.ValidDataCheckerImpl;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class TransferMoneyApplicationUnitTests {

    private static AppService appService;
    private static ValidDataCheckerImpl validDataChecker;
    private static AppRepository appRepository;
    private static CardService cardService;

    @Test
    public void appServiceTransferMoneyCardToCardTest() {
        appRepository = Mockito.mock(AppRepository.class);
        appService = new AppServiceImpl(appRepository);

        TransferRequest testTransferRequest = new TransferRequest();
        testTransferRequest.setCardFromNumber("1111111111111111");
        testTransferRequest.setCardFromValidTill("09/24");
        testTransferRequest.setCardFromCVV("232");
        testTransferRequest.setCardToNumber("2222222222222222");
        testTransferRequest.setAmount(new Amount());
        testTransferRequest.getAmount().setValue(4000);
        testTransferRequest.getAmount().setCurrency("RUR");

        var expectedOperationId = "123456789";

        Mockito.when(appRepository.create(testTransferRequest))
                .thenReturn(expectedOperationId);

        var testResult = appService.transferMoneyCardToCard(testTransferRequest);

        assertEquals(testResult.getClass(), SuccessTransfer.class);
        assertEquals(testResult.getOperationId(), expectedOperationId);
    }

    @Test
    public void appServiceTransferMoneyCardToCardExceptionTest() {
        appRepository = Mockito.mock(AppRepository.class);
        appService = new AppServiceImpl(appRepository);
        assertThrows(ClassCastException.class, () -> appService.transferMoneyCardToCard((TransferRequest) new Object()));
    }

    @Test
    public void appServiceConfirmOperationTest() {
        appRepository = Mockito.mock(AppRepository.class);
        appService = new AppServiceImpl(appRepository);

        String expectedOperationId = "1234567";
        ConfirmRequest testConfirmRequest = new ConfirmRequest();
        testConfirmRequest.setOperationId(expectedOperationId);
        testConfirmRequest.setCode("0000");

        var testResult = appService.confirmOperation(testConfirmRequest);

        assertEquals(testResult.getClass(), SuccessConfirmation.class);
        assertEquals(testResult.getOperationId(), expectedOperationId);
    }

    @Test
    public void appServiceConfirmOperationExceptionTest() {
        appRepository = Mockito.mock(AppRepository.class);
        appService = new AppServiceImpl(appRepository);

        ConfirmRequest testConfirmRequest = new ConfirmRequest();
        testConfirmRequest.setCode("4444");

        assertThrows(ConfirmTransactionNotValidException.class, () -> appService.confirmOperation((testConfirmRequest)));
    }

    @ParameterizedTest
    @CsvSource({"12/, 54/34, 09/45"})
    void validDataCheckerImplIsValidTest(String expected1, String expected2, String expected3) {
        validDataChecker = new ValidDataCheckerImpl();
        ConstraintValidatorContext context = Mockito.mock(ConstraintValidatorContext.class);

        assertFalse(validDataChecker.isValid(expected1, context));
        assertFalse(validDataChecker.isValid(expected2, context));
        assertTrue(validDataChecker.isValid(expected3, context));
    }

    @Test
    public void appRepositoryCreateTest() {
        cardService = Mockito.mock(CardService.class);
        appRepository = new AppRepository(cardService);
        TransferRequest testTransferRequest = new TransferRequest();
        Mockito.when(cardService.validCard(testTransferRequest))
                .thenReturn(true);
        var testResult = appRepository.create(testTransferRequest);

        assertNotNull(testResult);
    }

    @Test
    public void appRepositoryCreateExceptionTest() {
        cardService = Mockito.mock(CardService.class);
        appRepository = new AppRepository(cardService);
        TransferRequest testTransferRequest = new TransferRequest();
        Mockito.when(cardService.validCard(testTransferRequest))
                .thenReturn(false);

        assertThrows(TransactionNotValidException.class, () -> appRepository.create(testTransferRequest));
    }

    @Test
    public void appRepositoryTransferMoneyTest() {
        cardService = Mockito.mock(CardService.class);
        appRepository = new AppRepository(cardService);

        TransferRequest testTransferRequest = new TransferRequest();
        testTransferRequest.setCardFromNumber("1111111111111111");
        testTransferRequest.setCardFromValidTill("09/24");
        testTransferRequest.setCardFromCVV("232");
        testTransferRequest.setCardToNumber("2222222222222222");
        testTransferRequest.setAmount(new Amount());
        testTransferRequest.getAmount().setValue(4000);
        testTransferRequest.getAmount().setCurrency("RUR");

        var testTransaction = new Transaction(testTransferRequest);
        var operationId = testTransaction.getOperationId();
        appRepository.getTransactionMap().put(operationId, testTransaction);

        appRepository.transferMoney(operationId);

        verify(cardService, Mockito.atLeastOnce()).transfer(testTransaction);
    }

    @Test
    public void cardServiceAddCardTest() {
        cardService = new CardService();

        assertFalse(cardService.getCardMap().isEmpty());
    }

    @Test
    public void cardServiceValidCardTest() {
        cardService = new CardService();

        TransferRequest testTransferRequest = new TransferRequest();
        testTransferRequest.setCardFromNumber("2222222222222222");
        testTransferRequest.setCardFromValidTill("09/24");
        testTransferRequest.setCardFromCVV("232");
        testTransferRequest.setCardToNumber("3333333333333333");
        testTransferRequest.setAmount(new Amount());
        testTransferRequest.getAmount().setValue(500);
        testTransferRequest.getAmount().setCurrency("RUR");

        assertTrue(cardService.validCard(testTransferRequest));
    }

    @Test
    public void cardServiceValidCardExceptionTest() {
        CardService testCardService = null;
        assertThrows(NullPointerException.class, () -> testCardService.validCard(new TransferRequest()));
    }

    @Test
    public void cardServiceTransferTest() {
        cardService = new CardService();

        TransferRequest testTransferRequest = new TransferRequest();
        testTransferRequest.setCardFromNumber("2222222222222222");
        testTransferRequest.setCardFromValidTill("09/24");
        testTransferRequest.setCardFromCVV("232");
        testTransferRequest.setCardToNumber("3333333333333333");
        testTransferRequest.setAmount(new Amount());
        testTransferRequest.getAmount().setValue(500);
        testTransferRequest.getAmount().setCurrency("RUR");

        var transaction = new Transaction(testTransferRequest);
        var expectedValue = cardService.getCardMap().get("3333333333333333").getAmount().getValue() + transaction.getAmount().getValue();
        cardService.transfer(transaction);
        var resultValue = cardService.getCardMap().get("3333333333333333").getAmount().getValue();

        assertEquals(expectedValue, resultValue);
    }

    @Test
    public void cardServiceTransferExceptionTest() {
        cardService = new CardService();

        var testTransferRequest = new TransferRequest();
        var transaction = new Transaction(testTransferRequest);

        assertThrows(NullPointerException.class, () -> cardService.transfer(transaction));
    }
}