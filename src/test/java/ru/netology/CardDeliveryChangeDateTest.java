package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selectors;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryChangeDateTest {

    public static String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void shouldChangeMeetingDate() {
        var info = DataGenerator.Registration.generateInfo("ru");
        var planingDateStart = generateDate(5);
        var planingDateFinish = generateDate(8);
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("[data-test-id=\"city\"] input").setValue(info.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planingDateStart);
        $("[name=\"name\"].input__control").setValue(info.getName());
        $("[name=\"phone\"].input__control").setValue(info.getPhone());
        $(".checkbox__box").click();
        $(".button__text").click();
        $(".notification__content").shouldHave(Condition.text("Встреча успешно запланирована на "
                + planingDateStart), Duration.ofSeconds(15));
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planingDateFinish);
        $(".button__text").click();
        $$(".button__text").last().click();
        $("[data-test-id=\"success-notification\"].notification")
                .shouldHave(Condition.text("Встреча успешно запланирована на " + planingDateFinish),
                        Duration.ofSeconds(15));
    }
}
