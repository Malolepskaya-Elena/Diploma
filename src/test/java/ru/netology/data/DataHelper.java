package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {
    //параметры карт, карты
    private static Faker faker = new Faker(new Locale("en"));
    private static Faker fakerRu = new Faker(new Locale("ru"));

    private static String approvedCard = "4444 4444 4444 4441";
    private static String declinedCard = "4444 4444 4444 4442";

    private DataHelper() {
    }

    public static String getStatusApprovedCard() {
        return "APPROVED";
    }

    public static String getStatusDeclinedCard() {
        return "DECLINED";
    }

    public static String getValidMonth() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
    }

    private static String getPreviousMonth() {
        return LocalDate.now().minusMonths(1).format(DateTimeFormatter.ofPattern("MM")); }

    private static String getZeroMonth() {
        return "00";
    }

    private static String getThirteenMonth() {
        return "13";
    }

    public static String getValidYear() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yy"));
    }

    private static String getValidYearPlusOne() {
        return LocalDate.now().plusYears(1).format(DateTimeFormatter.ofPattern("yy")); }

    private static String getPreviousYear() {
        return LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("yy")); }

    private static String getPlusSixYear() {
        return LocalDate.now().plusYears(6).format(DateTimeFormatter.ofPattern("yy")); }
    //факанутость
    public static String getOwner() {
        return faker.name().fullName();
    }

    public static String getNameRus() {
        return fakerRu.name().lastName() + " " + fakerRu.name().firstName();
    }

    private static String getNameEn() {
        return faker.name().lastName();
    }

    public static String getCVC() {
        return faker.numerify("###");
    }

    private static String getTwoNumber() {
        return faker.numerify("##");
    }

    private static String getOneNumber() {
        return faker.numerify("#");
    }

    private static String getFakerNumberCard() {
        return faker.business().creditCardNumber();
    }

    private static String getFifteenNumber() {
        return approvedCard.substring(0, 18);
    }

    private static String getEmptyField() {
        return " ";
    }

    private static String getSpecialSymbol() {
        return "@#$%^&*()~-+/*?><|";
    }

    @Value
    public static class CardInfo {
        String number;
        String month;
        String year;
        String holder;   //резерв, надо
        String cvc;
    }
    //прописываем сценарии тестов
    public static CardInfo getApprovedCard() {           //все валидно 41
        return new CardInfo(approvedCard, getValidMonth(), getValidYear(), getOwner(), getCVC());
    }

    public static CardInfo getDeclinedCard() {          //хз 42
        return new CardInfo(declinedCard, getValidMonth(), getValidYear(), getOwner(), getCVC());
    }

    public static CardInfo getEmptyCardNumber() {          //пустой номер
        return new CardInfo(getEmptyField(), getValidMonth(), getValidYear(), getOwner(), getCVC());
    }

    public static CardInfo getOneNumberCardNumber() {    // номер карты 1 шт.
        return new CardInfo(getOneNumber(), getValidMonth(), getValidYear(), getOwner(), getCVC());
    }

    public static CardInfo getFifteenNumberCardNumber() {   //15шт
        return new CardInfo(getFifteenNumber(), getValidMonth(), getValidYear(), getOwner(), getCVC());
    }

    public static CardInfo getFakerNumberCardNumber() {    //нет такой
        return new CardInfo(getFakerNumberCard(), getValidMonth(), getValidYear(), getOwner(), getCVC());
    }

    public static CardInfo getEmptyMonth() {           //мес пусто
        return new CardInfo(approvedCard, getEmptyField(), getValidYear(), getOwner(), getCVC());
    }

    public static CardInfo getOneNumberMonth() {         //мес 1
        return new CardInfo(approvedCard, getOneNumber(), getValidYear(), getOwner(), getCVC());
    }

    public static CardInfo getPreviousMonthInField() {     //мес-1
        return new CardInfo(approvedCard, getPreviousMonth(), getValidYear(), getOwner(), getCVC());
    }

    public static CardInfo getZeroMonthInField() {      //мес00
        return new CardInfo(approvedCard, getZeroMonth(), getValidYearPlusOne(), getOwner(), getCVC());
    }

    public static CardInfo getThirteenMonthInField() {     //мес верх
        return new CardInfo(approvedCard, getThirteenMonth(), getValidYear(), getOwner(), getCVC());
    }

    public static CardInfo getEmptyYear() {                 //год пусто
        return new CardInfo(approvedCard, getValidMonth(), getEmptyField(), getOwner(), getCVC());
    }

    public static CardInfo getPreviousYearInField() {            //год-1
        return new CardInfo(approvedCard, getValidMonth(), getPreviousYear(), getOwner(), getCVC());
    }

    public static CardInfo getPlusSixYearInField() {             //более 5
        return new CardInfo(approvedCard, getValidMonth(), getPlusSixYear(), getOwner(), getCVC());
    }


    public static CardInfo getSpecialSymbolInFieldName() {            //спецуха
        return new CardInfo(approvedCard, getValidMonth(), getValidYear(), getSpecialSymbol(), getCVC());
    }

    public static CardInfo getNumberInFieldName() {                 //в имени циф
        return new CardInfo(approvedCard, getValidMonth(), getValidYear(), getTwoNumber(), getCVC());
    }

    public static CardInfo getOnlySurnameInFieldName() {           //одно имя
        return new CardInfo(approvedCard, getValidMonth(), getValidYear(), getNameEn(), getCVC());
    }

    public static CardInfo getEmptyCVVInFieldCVV() {               //нет CVV
        return new CardInfo(approvedCard, getValidMonth(), getValidYear(), getOwner(), getEmptyField());
    }

    public static CardInfo getOneNumberInFieldCVV() {              //CVV1
        return new CardInfo(approvedCard, getValidMonth(), getValidYear(), getOwner(), getOneNumber());
    }

    public static CardInfo getOTwoNumberInFieldCVV() {             //CVV2
        return new CardInfo(approvedCard, getValidMonth(), getValidYear(), getOwner(), getTwoNumber());
    }

    public  static CardInfo getRusName() {                         //кирил
        return new CardInfo(approvedCard, getValidMonth(), getValidYear(), getNameRus(), getCVC());
    }
}
