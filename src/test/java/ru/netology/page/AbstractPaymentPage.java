package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.CardInfo;
import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static org.junit.jupiter.api.Assertions.assertEquals;


public abstract class AbstractPaymentPage {

    protected static final SelenideElement formHeader = $x("//form//preceding-sibling::h3");
    protected static final SelenideElement cardNumberField = $x("//input[@placeholder='0000 0000 0000 0000']");
    protected static final SelenideElement monthField = $x("//input[@placeholder='08']");
    protected static final SelenideElement yearField = $x("//input[@placeholder='22']");
    protected static final SelenideElement ownerField = $(byText("Владелец")).parent().$("input");
    protected static final SelenideElement cvcField = $x("//input[@placeholder='999']");
    protected static final SelenideElement buttonContinue = $x("//span[text()='Продолжить']//ancestor::button");
    //ошибки полей
    protected static final SelenideElement fieldCardNumberError = $x("//*[text()='Номер карты']/..//*[@class='input__sub']");
    protected static final SelenideElement fieldMonthError = $x("//*[text()='Месяц']/..//*[@class='input__sub']");
    protected static final SelenideElement fieldYearError = $x("//*[text()='Год']/..//*[@class='input__sub']");
    protected static final SelenideElement fieldOwnerError = $x("//*[text()='Владелец']/..//*[@class='input__sub']");
    protected static final SelenideElement fieldCvcError = $x("//*[text()='CVC/CVV']/..//*[@class='input__sub']");
    protected static final SelenideElement notificationApproved = $x("//div[contains(@class, 'notification_status_ok')]");
    protected static final SelenideElement notificationError = $x("//div[contains(@class, 'notification_status_error')]");

    public void sendingCardData(CardInfo info) {
        cardNumberField.setValue(info.getNumber());
        monthField.setValue(info.getMonth());
        yearField.setValue(info.getYear());
        ownerField.setValue(info.getHolder());
        cvcField.setValue(info.getCvc());
        pressButtonForContinue();
    }

    public void pressButtonForContinue() {
        buttonContinue.click();
    }

    public void sendingValidDataWithFieldCardNumberError (String error) {
        fieldCardNumberError.shouldHave(text(error)).shouldBe(visible);
    }

    public void sendingValidDataWithFakerCardNumber (String error) {
        notificationError.shouldHave(text(error)).shouldBe(visible);
    }

    public void sendingValidDataWithFieldMonthError (String error) {
        fieldMonthError.shouldHave(text(error)).shouldBe(visible);
    }

    public void sendingValidDataWithFieldYearError (String error) {
        fieldYearError.shouldHave(text(error)).shouldBe(visible);
    }

    public void sendingEmptyNameValidData (CardInfo info) {
        cardNumberField.setValue(info.getNumber());
        monthField.setValue(info.getMonth());
        yearField.setValue(info.getYear());
        cvcField.setValue(info.getCvc());
        pressButtonForContinue();
        fieldOwnerError.shouldBe(visible);
    }

    public void sendingValidDataWithFieldNameError (String error) {
        fieldOwnerError.shouldHave(text(error)).shouldBe(visible);
    }

    public void sendingValidDataWithFieldCVVError (String error) {
        fieldCvcError.shouldHave(text(error)).shouldBe(visible);
    }

    public void sendEmptyForm() {
        pressButtonForContinue();
        fieldCardNumberError.shouldBe(visible);
        fieldMonthError.shouldBe(visible);
        fieldYearError.shouldBe(visible);
        fieldOwnerError.shouldBe(visible);
        fieldCvcError.shouldBe(visible);
    }

    public void bankApproved() {
        notificationApproved.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void bankDeclined() {
        notificationError.shouldBe(visible, Duration.ofSeconds(15));
    }
}