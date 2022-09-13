package ru.netology.page;

import com.codeborne.selenide.Condition;

public class DebitPaymentPage extends AbstractPaymentPage {

    public DebitPaymentPage() {
        formHeader.should(Condition.visible, Condition.text("Оплата по карте"));
    }
}