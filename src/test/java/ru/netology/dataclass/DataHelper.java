package ru.netology.dataclass;

import com.github.javafaker.Faker;
import lombok.Getter;
import lombok.Value;

public class DataHelper {

    @Getter
    private static final String validVasya = "vasya";
    @Getter
    private static final String validPetya = "petya";
    @Getter
    private static final String validPasswordVasya = "qwerty123";
    @Getter
    private static final String validPasswordPetya = "123qwerty";
    @Getter
    private static final String cipheredPassword = "$2a$10$zXMspIdjEHrK4W4iueC2QO8XFxadTn0dsoyD5A/qyroJUcWigWsaO";
    private static final Faker fake = new Faker();

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }

    public static AuthInfo getAuthInfoVasya() {
        return new AuthInfo(validVasya, validPasswordVasya);
    }

    public static AuthInfo getAuthInfoPetya() {
        return new AuthInfo(validPetya, validPasswordPetya);
    }

    public static String getUnregisteredPassword() {
        return fake.internet().password();
    }

    public static String getUnregisteredLogin() {
        return fake.internet().password();
    }

    public static User getUser() {
        String login = fake.name().firstName();
        String password = "qwerty123";
        String id = fake.internet().uuid();

        return new User(login, password, id);
    }
}
