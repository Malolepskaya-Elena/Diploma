package ru.netology.data;

import com.google.gson.Gson;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class ApiHelper {
    private static final Gson gson = new Gson();
    //private static CardInfo cardInfo;
    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(8080)  //коннект к сервису, а не симулятору
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    //постман
    public static void payByCard(CardInfo cardInfo, String path) {
        var body = gson.toJson(cardInfo);
        given()
                .spec(requestSpec)
                .body(body)
                .when()
                .post(path)
                .then()
                .statusCode(200);
    }
}