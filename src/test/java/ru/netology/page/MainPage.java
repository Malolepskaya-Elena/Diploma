package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class MainPage {
    public SelenideElement buttonBuy = $x("//*[@id=\"root\"]/div/button[1]");
    public SelenideElement buttonBuyWithCredit = $x("//*[@id=\"root\"]/div/button[2]");

    public MainPage() {
        buttonBuy.shouldBe(visible);
        buttonBuyWithCredit.shouldBe(visible);
    }

    public DebitPaymentPage pressPayDebitCardButton() {
        buttonBuy.click();
        return new DebitPaymentPage();
    }
    public CreditPaymentPage pressPayCreditCardButton() {
        buttonBuyWithCredit.click();
        return new CreditPaymentPage();
    }
}