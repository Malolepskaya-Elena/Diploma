package ru.netology.test.ui;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.DebitPaymentPage;
import ru.netology.page.MainPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.*;
import static ru.netology.data.Notification.*;
import static ru.netology.data.SQLHelper.*;

public class TestPayDebitCard {
    DebitPaymentPage debitPaymentPage;

    @BeforeAll
    public static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void openPage() {
        SQLHelper.databaseCleanUp();
        open("http://localhost:8080/");
        MainPage mp = new MainPage();
        debitPaymentPage = mp.pressPayDebitCardButton();
    }

    @AfterEach
    void cleanDB() {
    }

    @AfterAll
    public static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    @DisplayName("Покупка валидной картой") //на 45тыс отоварились, но образовалось 4500000
    public void shouldPayDebitValidCard() {
        var info = getApprovedCard();
        debitPaymentPage.sendingCardData(info);
        debitPaymentPage.bankApproved();
        var expected = DataHelper.getStatusApprovedCard();
        var paymentInfo = SQLHelper.getPaymentInfo();
        var orderInfo = SQLHelper.getOrderInfo();
        var expectedAmount = "45000";
        assertEquals(expected, paymentInfo.getStatus());
        assertEquals(paymentInfo.getTransaction_id(), orderInfo.getPayment_id());
        assertEquals(expectedAmount, String.valueOf(paymentInfo.getAmount()));
        //SELECT * FROM payment_entity ORDER BY created DESC
        //Перезапустить отдельно
    }

    @Test
    @DisplayName("Покупка невалидной картой")
    void shouldPayDebitDeclinedCard() {
        var info = DataHelper.getDeclinedCard();
        debitPaymentPage.sendingCardData(info);
        debitPaymentPage.bankDeclined();
        var paymentStatus = getPaymentInfo();
        assertEquals("DECLINED", paymentStatus.getStatus());
    }

    @Test
    @DisplayName("Покупка картой без заполнения полей")
    void shouldEmptyFormDebitCard() {
        debitPaymentPage.sendEmptyForm();
    }

    @Test
    @DisplayName("Покупка картой: поле карты пусто, остальные поля - валидные данные")
    void shouldEmptyFieldCardFormDebit() {
        var info = DataHelper.getEmptyCardNumber();
        debitPaymentPage.sendingCardData(info);
        debitPaymentPage.sendingValidDataWithFieldCardNumberError(wrongFormatMsg);
    }

    @Test
    @DisplayName("Покупка картой: поле карты одной цифрой, остальные поля - валидные данные")
    public void shouldOneNumberInFieldCardFormDebit() {
        var info = DataHelper.getOneNumberCardNumber();
        debitPaymentPage.sendingCardData(info);
        debitPaymentPage.sendingValidDataWithFieldCardNumberError(wrongFormatMsg);
    }

    @Test
    @DisplayName("Покупка картой: поле карты 15 цифр, остальные поля - валидные данные")
    public void shouldFifteenNumberInFieldCardNumberFormDebit() {
        var info = DataHelper.getFifteenNumberCardNumber();
        debitPaymentPage.sendingCardData(info);
        debitPaymentPage.sendingValidDataWithFieldCardNumberError(wrongFormatMsg);
    }

    @Test
    @DisplayName("Покупка картой не из БД, остальные поля - валидные данные")
    public void shouldFakerCardNumberFormDebit() {
        var info = DataHelper.getFakerNumberCardNumber();
        debitPaymentPage.sendingCardData(info);
        debitPaymentPage.sendingValidDataWithFakerCardNumber(declinedMsg);
    }

    @Test
    @DisplayName("Покупка картой: поле месяц пусто, остальные поля - валидные данные")
    public void shouldEmptyFieldMonthFormDebit() {
        var info = getEmptyMonth();
        debitPaymentPage.sendingCardData(info);
        debitPaymentPage.sendingValidDataWithFieldMonthError(wrongFormatMsg);
    }

    @Test
    @DisplayName("Покупка картой: заполнено поле месяц одной цифрой, остальные поля - валидные данные")
    public void shouldOneNumberInFieldMonthFormDebit() {
        var info = getOneNumberMonth();
        debitPaymentPage.sendingCardData(info);
        debitPaymentPage.sendingValidDataWithFieldMonthError(invalidData);
    }

    @Test
    @DisplayName("Покупка картой: в поле месяц предыдущий от текущего, остальные поля - валидные данные")
    public void shouldFieldWithPreviousMonthFormDebit() {
        var info = getPreviousMonthInField();
        debitPaymentPage.sendingCardData(info);
        debitPaymentPage.sendingValidDataWithFieldMonthError(expiredDate);
    }

    @Test
    @DisplayName("Покупка картой: в поле месяц нулевой (не существующий) месяц" +
            " остальные поля - валидные данные")
    public void shouldFieldWithZeroMonthFormDebit() {
        var info = getZeroMonthInField();
        debitPaymentPage.sendingCardData(info);
        debitPaymentPage.sendingValidDataWithFieldMonthError(invalidData);
    }

    @Test
    @DisplayName("Покупка картой: в поле месяц тринадцатый (не существующий) месяц" +
            " остальные поля - валидные данные")
    public void shouldFieldWithThirteenMonthFormDebit() {
        var info = getThirteenMonthInField();
        debitPaymentPage.sendingCardData(info);
        debitPaymentPage.sendingValidDataWithFieldMonthError(invalidData);
    }

    @Test
    @DisplayName("Покупка картой: не заполнено поле год, остальные поля - валидные данные")
    public void shouldEmptyFieldYearFormDebit() {
        var info = getEmptyYear();
        debitPaymentPage.sendingCardData(info);
        debitPaymentPage.sendingValidDataWithFieldYearError(wrongFormatMsg);
    }

    @Test
    @DisplayName("Покупка картой: заполнение поля год, предыдущим годом от текущего" +
            " остальные поля - валидные данные")
    public void shouldPreviousYearFieldYearFormDebit() {
        var info = getPreviousYearInField();
        debitPaymentPage.sendingCardData(info);
        debitPaymentPage.sendingValidDataWithFieldYearError(expiredDate);
    }

    @Test
    @DisplayName("Покупка картой: заполнение поля год, на шесть лет больше чем текущий" +
            " остальные поля - валидные данные")
    public void shouldPlusSixYearFieldYearFormDebit() {
        var info = getPlusSixYearInField();
        debitPaymentPage.sendingCardData(info);
        debitPaymentPage.sendingValidDataWithFieldYearError(invalidData);
    }

    @Test
    @DisplayName("Покупка картой: поле владелец пустое, остальные - валидные данные")
    public void shouldEmptyFieldNameFormDebit() {
        var info = getApprovedCard();
        debitPaymentPage.sendingEmptyNameValidData(info);
        debitPaymentPage.sendingValidDataWithFieldNameError(mustBeFilledMsg);
    }

    @Test
    @DisplayName("Покупка картой: заполнение поля владелец спец. символами" +
            " остальные поля - валидные данные")
    public void shouldSpecialSymbolInFieldNameFormDebit() {
        var info = getSpecialSymbolInFieldName();
        debitPaymentPage.sendingCardData(info);
        debitPaymentPage.sendingValidDataWithFieldNameError(wrongFormatMsg);
    }

    @Test
    @DisplayName("Покупка картой: заполнение  поля владелец цифрами" +
            " остальные поля - валидные данные")
    public void shouldNumberInFieldNameFormDebit() {
        var info = getNumberInFieldName();
        debitPaymentPage.sendingCardData(info);
        debitPaymentPage.sendingValidDataWithFieldNameError(wrongFormatMsg);
    }

    @Test
    @DisplayName("Покупка картой: заполнение поля владелец рус буквами" +
            " остальные поля - валидные данные")
    public void shouldEnglishNameInFieldNameFormDebit() {
        var info = DataHelper.getRusName();
        debitPaymentPage.sendingCardData(info);
        debitPaymentPage.sendingValidDataWithFieldNameError(wrongFormatMsg);
    }

    @Test
    @DisplayName("Покупка картой: поле владелец только фамилия, остальные поля - валидные данные")
    public void shouldOnlySurnameFormDebit() {
        var info = DataHelper.getOnlySurnameInFieldName();
        debitPaymentPage.sendingCardData(info);
        debitPaymentPage.sendingValidDataWithFieldNameError(wrongFormatMsg);
    }

    @Test
    @DisplayName("Покупка картой: поле CVV пусто" + " остальные поля - валидные данные")
    public void shouldEmptyCVVInFieldCVVFormDebit() {
        var info = getEmptyCVVInFieldCVV();
        debitPaymentPage.sendingCardData(info);
        debitPaymentPage.sendingValidDataWithFieldCVVError(wrongFormatMsg);
    }

    @Test
    @DisplayName("Покупка картой: поле CVV одно число" + " остальные поля - валидные данные")
    public void shouldOneNumberInFieldCVVFormDebit() {
        var info = getOneNumberInFieldCVV();
        debitPaymentPage.sendingCardData(info);
        debitPaymentPage.sendingValidDataWithFieldCVVError(wrongFormatMsg);
    }

    @Test
    @DisplayName("Покупка картой: поле CVV двумя числами" + " остальные поля - валидные данные")
    public void shouldTwoNumberInFieldCVVАFormDebit() {
        var info = getOTwoNumberInFieldCVV();
        debitPaymentPage.sendingCardData(info);
        debitPaymentPage.sendingValidDataWithFieldCVVError(wrongFormatMsg);
    }
}