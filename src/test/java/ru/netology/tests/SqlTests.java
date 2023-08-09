package ru.netology.tests;

import jdk.jfr.Label;
import org.junit.jupiter.api.Test;
import ru.netology.dataclass.DataHelper;
import ru.netology.dataclass.User;
import ru.netology.pages.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.dataclass.DataHelper.*;
import static ru.netology.dataclass.SqlRequests.*;


public class SqlTests {
    private final String site = "http://localhost:9999";


    @Test
    @Label("Positive test")
    public void authTestUserPositive() {
        open(site);
        User user = getUser();
        requestCreateUser(user);
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin(user.getLogin(), user.getPassword());
        verificationPage.validVerify(requestCode(user));
        requestDeleteUser(user);
    }

    @Test
    @Label("Positive test")
    public void authAsVasya() {
        open(site);
        var vasya = requestUser(getAuthInfoVasya());
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin(vasya.getLogin(), DataHelper.getValidPasswordVasya());
        verificationPage.validVerify(requestCode(vasya));
    }

    @Test
    @Label("Positive test")
    public void authAsPetya() {
        open(site);
        var petya = requestUser(getAuthInfoPetya());
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin(petya.getLogin(), DataHelper.getValidPasswordPetya());
        verificationPage.validVerify(requestCode(petya));
    }

    @Test
    @Label("Negative test")
    public void shouldFailIfThreeInvalidPassword() {
        open(site);
        var loginPage = new LoginPage();
        loginPage.tripleInvalidPassword(getValidVasya(), getUnregisteredPassword());
    }

    @Test
    @Label("Negative test")
    public void shouldFailIfInvalidLogin() {
        open(site);
        var loginPage = new LoginPage();
        loginPage.invalidLogin(getUnregisteredLogin(), getValidPasswordVasya());
    }

    @Test
    @Label("Negative test")
    public void shouldFailIfUnregisteredUser() {
        open(site);
        var loginPage = new LoginPage();
        loginPage.invalidLogin(getUnregisteredLogin(), getUnregisteredPassword());
    }

}