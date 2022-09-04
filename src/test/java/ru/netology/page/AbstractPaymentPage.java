package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import ru.netology.data.DataHelper.CardInfo;

public abstract class AbstractPaymentPage {

    protected SelenideElement cardNumberField = $x("//input[@placeholder='0000 0000 0000 0000']");
    protected SelenideElement monthField = $x("//input[@placeholder='08']");
    protected SelenideElement yearField = $x("//input[@placeholder='22']");
    protected SelenideElement ownerField = $(byText("Владелец")).parent().$("input");
    protected SelenideElement cvcField = $x("//input[@placeholder='999']");
    protected SelenideElement buttonContinue = $x("//span[text()='Продолжить']//ancestor::button");
    //ошибки полей
    protected SelenideElement fieldCardNumberError = $x("//*[text()='Номер карты']/..//*[@class='input__sub']");
    protected SelenideElement fieldMonthError = $x("//*[text()='Месяц']/..//*[@class='input__sub']");
    protected SelenideElement fieldYearError = $x("//*[text()='Год']/..//*[@class='input__sub']");
    protected SelenideElement fieldOwnerError = $x("//*[text()='Владелец']/..//*[@class='input__sub']");
    protected SelenideElement fieldCvcError = $x("//*[text()='CVC/CVV']/..//*[@class='input__sub']");
    protected SelenideElement notificationApproved = $x("//div[contains(@class, 'notification_status_ok')]");
    protected SelenideElement notificationError = $x("//div[contains(@class, 'notification_status_error')]");

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

    public String sendingValidDataWithFieldCardNumberError() {
        fieldCardNumberError.shouldBe(visible);
        return fieldCardNumberError.getText();
    }

    public String sendingValidDataWithFakerCardNumber() {
        notificationError.shouldBe(visible, Duration.ofSeconds(15));
        return notificationError.getText();
    }

    public String sendingValidDataWithFieldMonthError() {
        fieldMonthError.shouldBe(visible);
        return fieldMonthError.getText();
    }

    public String sendingValidDataWithFieldYearError() {
        fieldYearError.shouldBe(visible);
        return fieldYearError.getText();
    }

    public void sendingEmptyNameValidData(DataHelper.CardInfo info) {
        cardNumberField.setValue(info.getNumber());
        monthField.setValue(info.getMonth());
        yearField.setValue(info.getYear());
        cvcField.setValue(info.getCvc());
        pressButtonForContinue();
        fieldOwnerError.shouldBe(visible);
    }

    public String sendingValidDataWithFieldNameError() {
        fieldOwnerError.shouldBe(visible);
        return fieldOwnerError.getText();
    }

    public String sendingValidDataWithFieldCVVError() {
        fieldCvcError.shouldBe(visible);
        return fieldCvcError.getText();
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
        //System.out.println(notificationApproved.getText());
    }

    public void bankDeclined() {
        notificationError.shouldBe(visible, Duration.ofSeconds(15));
        //System.out.println(notificationError.getText());
    }
}