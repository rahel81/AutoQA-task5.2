package ru.netology.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class AuthTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSendActiveUser() {
        UserData user = DataGenerator.getValidUserActive();
        $("[data-test-id=login] .input__control").setValue(user.getLogin());
        $("[data-test-id=password] .input__control").setValue(user.getPassword());
        $("button[data-test-id=action-login]").click();
        $("h2.heading.heading_size_l.heading_theme_alfa-on-white").shouldHave(text("Личный кабинет"));
    }

    @Test
    void shouldSendBlockedUser() {
        UserData user = DataGenerator.getValidUserBlocked();
        $("[data-test-id=login] .input__control").setValue(user.getLogin());
        $("[data-test-id=password] .input__control").setValue(user.getPassword());
        $("button[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldHave(text("Пользователь заблокирован"));
    }

    @Test
    void shouldSendInvalidLoginUser() {
        UserData user = DataGenerator.invalidLogin();
        $("[data-test-id=login] .input__control").setValue(user.getLogin());
        $("[data-test-id=password] .input__control").setValue(user.getPassword());
        $("button[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка!"))
                .shouldHave(text("Неверно указан логин или пароль"));
    }

    @Test
    void shouldSendInvalidPasswordUser() {
        UserData user = DataGenerator.invalidPassword();
        $("[data-test-id=login] .input__control").setValue(user.getLogin());
        $("[data-test-id=password] .input__control").setValue(user.getPassword());
        $("button[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка!"))
                .shouldHave(text("Неверно указан логин или пароль"));
    }

    @Test
    void shouldSendEmptyFieldLoginUser() {
        UserData user = DataGenerator.getValidUserActive();
        $("[data-test-id=password] .input__control").setValue(user.getPassword());
        $("button[data-test-id=action-login]").click();
        $("[data-test-id=login]").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldSendEmptyFieldPasswordUser() {
        UserData user = DataGenerator.getValidUserActive();
        $("[data-test-id=login] .input__control").setValue(user.getLogin());
        $("button[data-test-id=action-login]").click();
        $("[data-test-id=password]").shouldHave(text("Поле обязательно для заполнения"));
    }
}
