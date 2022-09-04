package ru.netology.page;

public class DebitPaymentPage extends AbstractPaymentPage {

    public static DebitPaymentPage getDebitPaymentPage() {
        MainPage mainPage = new MainPage();
        return mainPage.pressPayDebitCardButton();
    }
}