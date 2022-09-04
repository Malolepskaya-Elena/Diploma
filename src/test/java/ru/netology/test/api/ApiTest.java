package ru.netology.test.api;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.DataHelper.*;

import static ru.netology.data.ApiHelper.payByCard;

public class ApiTest {
    CardInfo approvedCardInfo = DataHelper.getApprovedCard();
    CardInfo declinedCardInfo = DataHelper.getDeclinedCard();
    String debitPath = "/payment";
    String creditPath = "/credit";

    @BeforeAll
    static void setUp() {
        RestAssured.filters(
                new RequestLoggingFilter(),
                new ResponseLoggingFilter(),
                new AllureRestAssured());
    }

    @DisplayName("Проверка отправки данных банковскому сервису")
    @Test
    void shouldSendRequestToBankService() {

    }

    @DisplayName("Запрос на покупку по карте со статусом APPROVED")
    @Test
    void shouldApprovePayment() {
        payByCard(approvedCardInfo, debitPath);
    }

    @DisplayName("Запрос на кредит по карте со статусом APPROVED")
    @Test
    void shouldApproveCredit() {
        payByCard(approvedCardInfo, creditPath);
    }

    @DisplayName("Запрос на покупку по карте со статусом DECLINED")
    @Test
    void shouldDeclinePayment() {
        payByCard(declinedCardInfo, debitPath);
    }

    @DisplayName("Запрос на кредит по карте со статусом DECLINED")
    @Test
    void shouldDeclineCredit() {
        payByCard(declinedCardInfo, creditPath);
    }
}