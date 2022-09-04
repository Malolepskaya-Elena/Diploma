package ru.netology.page;

public class CreditPaymentPage extends AbstractPaymentPage {

    public static CreditPaymentPage getCreditPaymentPage() {
        MainPage mainPage = new MainPage();
        return mainPage.pressPayCreditCardButton();
    }
}