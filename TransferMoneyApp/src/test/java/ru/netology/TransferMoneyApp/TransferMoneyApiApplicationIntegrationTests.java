package ru.netology.TransferMoneyApp;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.netology.TransferMoneyApp.schemas.data.ConfirmRequest;
import ru.netology.TransferMoneyApp.schemas.errors.ErrorInputData;
import ru.netology.TransferMoneyApp.schemas.errors.ErrorTransfer;
import ru.netology.TransferMoneyApp.schemas.data.Amount;
import ru.netology.TransferMoneyApp.schemas.data.TransferRequest;
import ru.netology.TransferMoneyApp.schemas.sucsess.SuccessConfirmation;
import ru.netology.TransferMoneyApp.schemas.sucsess.SuccessTransfer;

import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransferMoneyApiApplicationIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Container
    public static final GenericContainer<?> container = new GenericContainer<>(
            new ImageFromDockerfile()
                    .withDockerfile(Paths.get("./Dockerfile")))
            .withExposedPorts(5500);

    @Test
    void transferMoneyCardToCard200Test() {
        final String HOST = "http://localhost:";
        var testRequest = new TransferRequest();
        testRequest.setCardFromNumber("1111111111111111");
        testRequest.setCardFromValidTill("09/24");
        testRequest.setCardFromCVV("232");
        testRequest.setCardToNumber("2222222222222222");
        testRequest.setAmount(new Amount());
        testRequest.getAmount().setValue(4000);
        testRequest.getAmount().setCurrency("RUR");

        ResponseEntity<SuccessTransfer> forEntity = restTemplate.postForEntity(
                HOST + container.getMappedPort(5500) + "/transfer",
                testRequest,
                SuccessTransfer.class);

        assertThat(forEntity.getStatusCode(), is(HttpStatus.OK));
        assertNotNull(forEntity.getBody());
    }

    @Test
    void transferMoneyCardToCard400Test() {
        final String HOST = "http://localhost:";
        var testRequest = new TransferRequest();
        testRequest.setCardFromNumber("1111111111111111");
        testRequest.setCardFromValidTill("55/24");
        testRequest.setCardFromCVV("232");
        testRequest.setCardToNumber("2222222222222222");
        testRequest.setAmount(new Amount());
        testRequest.getAmount().setValue(4000);
        testRequest.getAmount().setCurrency("RUR");

        ResponseEntity<ErrorInputData> forEntity = restTemplate.postForEntity(
                HOST + container.getMappedPort(5500) + "/transfer",
                testRequest,
                ErrorInputData.class);

        assertThat(forEntity.getStatusCode(), is(HttpStatus.BAD_REQUEST));
        assertNotNull(forEntity.getBody());
    }

    @Test
    void transferMoneyCardToCard500Test() {
        final String HOST = "http://localhost:";

        ResponseEntity<ErrorTransfer> forEntity = restTemplate.postForEntity(
                HOST + container.getMappedPort(5500) + "/transfer",
                null,
                ErrorTransfer.class);

        assertThat(forEntity.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
        assertNotNull(forEntity.getBody());
    }

    @Test
    void confirmOperation200Test() {
        final String HOST = "http://localhost:";
        var testRequest = new ConfirmRequest();
        testRequest.setOperationId("333433");
        testRequest.setCode("1234");

        ResponseEntity<SuccessConfirmation> forEntity = restTemplate.postForEntity(
                HOST + container.getMappedPort(5500) + "/confirmOperation",
                testRequest,
                SuccessConfirmation.class);

        assertThat(forEntity.getStatusCode(), is(HttpStatus.OK));
        assertNotNull(forEntity.getBody());
    }

    @Test
    void confirmOperation500Test() {
        final String HOST = "http://localhost:";

        ResponseEntity<SuccessConfirmation> forEntity = restTemplate.postForEntity(
                HOST + container.getMappedPort(5500) + "/confirmOperation",
                null,
                SuccessConfirmation.class);

        assertThat(forEntity.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
        assertNotNull(forEntity.getBody());
    }
}