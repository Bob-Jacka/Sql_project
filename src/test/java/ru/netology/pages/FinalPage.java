package ru.netology.pages;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class FinalPage {
    private final SelenideElement personalAccountText = $("[data-test-id='dashboard']");

    public FinalPage() {
        personalAccountText.shouldBe(visible);
        personalAccountText.shouldHave(Condition.text("  Личный кабинет"));
    }
}
