package ru.netology.web;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.http.ContentType;

import java.util.Locale;

import static io.restassured.RestAssured.given;


public class DataGenerator {
    private DataGenerator() {
    }

    static final Faker faker = new Faker(new Locale("en"));
    static final Gson gson = new Gson();

    private static final String userActive = "active";
    private static final String userBlocked = "blocked";

    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    static void setUpAll(UserData userData) {
        // сам запрос
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(gson.toJson(userData)) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    public static UserData getValidUserActive() {
        UserData user = new UserData(faker.name().username(), faker.internet().password(), userActive);
        setUpAll(user);
        return user;
    }

    public static UserData getValidUserBlocked() {
        UserData user = new UserData(faker.name().username(), faker.internet().password(), userBlocked);
        setUpAll(user);
        return user;
    }

    public static UserData invalidLogin() {
        String password = faker.internet().password();
        setUpAll(new UserData(faker.name().username(), password, userActive));
        return new UserData(faker.name().username(), password, userActive);
    }

    public static UserData invalidPassword() {
        String login = faker.name().username();
        setUpAll(new UserData(login, faker.internet().password(), userActive));
        return new UserData(login, faker.internet().password(), userActive);
    }
}
