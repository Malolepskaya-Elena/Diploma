package ru.netology.test.ui;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.CreditPaymentPage;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.*;
import static ru.netology.data.Notification.*;
import static ru.netology.data.SQLHelper.*;

public class TestPayCreditCard {
    CreditPaymentPage creditPaymentPage;

    @BeforeAll
    public static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide()); }

    @BeforeEach
    public void openPage() {
        open("http://localhost:8080");
        creditPaymentPage = CreditPaymentPage.getCreditPaymentPage();
    }

    @AfterEach
    void cleanDB() {
        SQLHelper.databaseCleanUp();
    }

    @AfterAll
    public static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    @SneakyThrows
    @DisplayName("Покупка в кредит")
    void shouldApproveCreditCard() {
        var info = getApprovedCard();
        creditPaymentPage.sendingCardData(info);
        creditPaymentPage.bankApproved();
        var expected = DataHelper.getStatusApprovedCard();
        var creditRequest = getCreditRequestInfo();
        var orderInfo = getOrderInfo();
        assertEquals(expected, getCreditRequestInfo().getStatus());
        assertEquals(orderInfo.getPayment_id(), creditRequest.getBank_id());
    }

    @Test
    @SneakyThrows
    @DisplayName("Покупка в кредит невалидной картой")
    void shouldPayCreditDeclinedCard() {
        var info = DataHelper.getDeclinedCard();
        creditPaymentPage.sendingCardData(info);
        creditPaymentPage.bankDeclined();
        var expected = getStatusDeclinedCard();
        var paymentInfo = getPaymentInfo().getStatus();
        assertEquals(expected, paymentInfo);
    }

    @Test
    @DisplayName("Покупка в кредит: все поля пустые")
    void shouldEmptyFormWithCredit() {
        creditPaymentPage.sendEmptyForm();
    }

    @Test
    @DisplayName("Покупка в кредит: поле карты пусто, остальные поля - валидные данные")
    public void shouldEmptyFieldCardWithCredit() throws SQLException {
        var info = getEmptyCardNumber();
        creditPaymentPage.sendingCardData(info);
        String message = creditPaymentPage.sendingValidDataWithFieldCardNumberError();
        assertEquals(wrongFormatMsg, message);
    }

    @Test
    @DisplayName("Покупка в кредит: поле карты одной цифрой, остальные поля - валидные данные")
    public void shouldOneNumberInFieldCardNumberWithCredit() {
        var info = getOneNumberCardNumber();
        creditPaymentPage.sendingCardData(info);
        String message = creditPaymentPage.sendingValidDataWithFieldCardNumberError();
        assertEquals(wrongFormatMsg, message);
    }

    @Test
    @DisplayName("Покупка в кредит: поле карты 15 цифр, остальные поля - валидные данные")
    public void shouldFifteenNumberInFieldCardNumberWithCredit() {
        var info = getFifteenNumberCardNumber();
        creditPaymentPage.sendingCardData(info);
        String message = creditPaymentPage.sendingValidDataWithFieldCardNumberError();
        assertEquals(wrongFormatMsg, message);
    }

    @Test
    @DisplayName("Покупка в кредит: карты нет в БД, остальные поля - валидные данные")
    public void shouldFakerCardInFieldCardNumberWithCredit() {
        var info = getFakerNumberCardNumber();
        creditPaymentPage.sendingCardData(info);
        String message = creditPaymentPage.sendingValidDataWithFakerCardNumber();
        assertEquals(declinedMsg, message);
    }

    @Test
    @DisplayName("Покупка в кредит: поле месяц пусто, остальные поля - валидные данные")
    public void shouldEmptyFieldMonthWithCredit() {
        var info = getEmptyMonth();
        creditPaymentPage.sendingCardData(info);
        String message = creditPaymentPage.sendingValidDataWithFieldMonthError();
        assertEquals(wrongFormatMsg, message);
    }

    @Test
    @DisplayName("Покупка в кредит: поле месяц одной цифрой, остальные поля - валидные данные")
    public void shouldOneNumberInFieldMonthWithCredit() {
        var info = getOneNumberMonth();
        creditPaymentPage.sendingCardData(info);
        String message = creditPaymentPage.sendingValidDataWithFieldMonthError();
        assertEquals(wrongFormatMsg, message);
    }

    @Test
    @DisplayName("Покупка в кредит: в поле месяц предыдущий от текущего, остальные поля - валидные данные")
    public void shouldFieldWithPreviousMonthWithCredit() {
        var info = getPreviousMonthInField();
        creditPaymentPage.sendingCardData(info);
        String message = creditPaymentPage.sendingValidDataWithFieldMonthError();
        assertEquals(expiredDate, message);
    }

    @Test
    @DisplayName("Покупка в кредит: в поле месяц нулевой (не существующий) месяц" +
            " остальные поля - валидные данные")
    public void shouldFieldWithZeroMonthWithCredit() {
        var info = getZeroMonthInField();
        creditPaymentPage.sendingCardData(info);
        String message = creditPaymentPage.sendingValidDataWithFieldMonthError();
        assertEquals(invalidData, message);
    }

    @Test
    @DisplayName("Покупка в кредит: в поле месяц тринадцатый (не существующий) месяц" +
            " остальные поля - валидные данные")
    public void shouldFieldWithThirteenMonthWithCredit() {
        var info = getThirteenMonthInField();
        creditPaymentPage.sendingCardData(info);
        String message = creditPaymentPage.sendingValidDataWithFieldMonthError();
        assertEquals(invalidData, message);
    }

    @Test
    @DisplayName("Покупка в кредит: поле год пусто, остальные поля - валидные данные")
    public void shouldEmptyFieldYearWithCredit() {
        var info = getEmptyYear();
        creditPaymentPage.sendingCardData(info);
        String message = creditPaymentPage.sendingValidDataWithFieldYearError();
        assertEquals(wrongFormatMsg, message);
    }

    @Test
    @DisplayName("Покупка в кредит: поле год предыдущий год от текущего" +
            " остальные поля - валидные данные")
    public void shouldPreviousYearFieldYearWithCredit() {
        var info = getPreviousYearInField();
        creditPaymentPage.sendingCardData(info);
        String message = creditPaymentPage.sendingValidDataWithFieldYearError();
        assertEquals(expiredDate, message);
    }

    @Test
    @DisplayName("Покупка в кредит: поле год на шесть лет больше чем текущий" +
            " остальные поля - валидные данные")
    public void shouldPlusSixYearFieldYearWithCredit() {
        var info = getPlusSixYearInField();
        creditPaymentPage.sendingCardData(info);
        String message = creditPaymentPage.sendingValidDataWithFieldYearError();
        assertEquals(invalidData, message);
    }

    @Test
    @DisplayName("Покупка в кредит: поле владелец пустое, остальные поля - валидные данные")
    public void shouldEmptyFieldNameWithCredit() {
        var info = getApprovedCard();
        creditPaymentPage.sendingEmptyNameValidData(info);
        String message = creditPaymentPage.sendingValidDataWithFieldNameError();
        assertEquals(mustBeFilledMsg, message);
    }

    @Test
    @DisplayName("Покупка в кредит: поле владелец спец.символами" +
            " остальные поля - валидные данные")
    public void shouldSpecialSymbolInFieldNameWithCredit() {
        var info = getSpecialSymbolInFieldName();
        creditPaymentPage.sendingCardData(info);
        String message = creditPaymentPage.sendingValidDataWithFieldNameError();
        assertEquals(wrongFormatMsg, message);
    }

    @Test
    @DisplayName("Покупка в кредит: поле владелец цифрами" +
            " остальные поля - валидные данные")
    public void shouldNumberInFieldNameWithCredit() {
        var info = getNumberInFieldName();
        creditPaymentPage.sendingCardData(info);
        String message = creditPaymentPage.sendingValidDataWithFieldNameError();
        assertEquals(wrongFormatMsg, message);
    }

    @Test
    @DisplayName("Покупка в кредит: поле владелец рус. буквами" +
            " остальные поля - валидные данные")
    public void shouldRussianNameInFieldNameWithCredit() {
        var info = getRusName();
        creditPaymentPage.sendingCardData(info);
        String message = creditPaymentPage.sendingValidDataWithFieldNameError();
        assertEquals(wrongFormatMsg, message);
    }

    @Test
    @DisplayName("Покупка в кредит: поле владелец одно слово" +
            " остальные поля - валидные данные")
    public void shouldOnlySurnameInFieldNameWithCredit() {
        var info = getOnlySurnameInFieldName();
        creditPaymentPage.sendingCardData(info);
        String message = creditPaymentPage.sendingValidDataWithFieldNameError();
        assertEquals(wrongFormatMsg, message);
    }

    @Test
    @DisplayName("Покупка в кредит: поле CVV пусто" +
            " остальные поля - валидные данные")
    public void shouldEmptyCVVInFieldCVVWithCredit() {
        var info = getEmptyCVVInFieldCVV();
        creditPaymentPage.sendingCardData(info);
        String message = creditPaymentPage.sendingValidDataWithFieldCVVError();
        assertEquals(wrongFormatMsg, message);
    }

    @Test
    @DisplayName("Покупка в кредит: поле CVV одним числом" +
            " остальные поля - валидные данные")
    public void shouldOneNumberInFieldCVVWithCredit() {
        var info = getOneNumberInFieldCVV();
        creditPaymentPage.sendingCardData(info);
        String message = creditPaymentPage.sendingValidDataWithFieldCVVError();
        assertEquals(wrongFormatMsg, message);
    }

    @Test
    @DisplayName("Покупка в кредит: поле CVV два числа" +
            " остальные поля - валидные данные")
    public void shouldTwoNumberInFieldCVVWithCredit() {
        var info = getOTwoNumberInFieldCVV();
        creditPaymentPage.sendingCardData(info);
        String message = creditPaymentPage.sendingValidDataWithFieldCVVError();
        assertEquals(wrongFormatMsg, message);
    }
}