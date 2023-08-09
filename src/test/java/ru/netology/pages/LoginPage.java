package ru.netology.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement loginInput = $("[data-test-id='login'] input");
    private final SelenideElement passwordInput = $("[data-test-id='password'] input");
    private final SelenideElement loginBtn = $("[data-test-id='action-login']");
    private final SelenideElement error = $("[data-test-id='error-notification']");

    public VerificationPage validLogin(String login, String password) {
        loginInput.sendKeys(login);
        passwordInput.sendKeys(password);
        loginBtn.click();
        return new VerificationPage();
    }

    private void clearInputField() {
        loginInput.doubleClick();
        loginInput.sendKeys(Keys.DELETE);
        passwordInput.doubleClick();
        passwordInput.sendKeys(Keys.DELETE);
    }

    public void invalidPassword(String login, String password) {
        loginInput.sendKeys(login);
        passwordInput.sendKeys(password);
        loginBtn.click();
        error.shouldBe(Condition.visible);
    }

    public void invalidLogin(String login, String password) {
        loginInput.sendKeys(login);
        passwordInput.sendKeys(password);
        loginBtn.click();
        error.shouldBe(Condition.visible);
    }

    public void tripleInvalidPassword(String login, String password) {
        for (int cycle = 0; cycle < 3; cycle++) {
            invalidPassword(login, password);
            clearInputField();
        }
    }
}
