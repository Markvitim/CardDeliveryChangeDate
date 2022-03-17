package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
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
    private Faker faker;

    public static String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @BeforeEach
    void setUpp() {
        faker = new Faker(new Locale("ru"));
    }

    @Test
    void shouldSetUppData() {
        String city = faker.address().cityName();
        String name = faker.name().fullName();
        String phone = faker.phoneNumber().phoneNumber();
        System.out.println(city);
        System.out.println(name);
        System.out.println(phone);

    }

    @Test
    void shouldGenerateTestData() {
        RegistrationInfo info = DataGenerator.Registration.generateInfo("ru");
        System.out.println(info);
    }

    @Test
    void shouldChangeMeetingDate() {
        String planingDateStart = generateDate(5);
        String planingDateFinish = generateDate(8);
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("[data-test-id=\"city\"] input").
                setValue(DataGenerator.Registration.generateInfo("ru").getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planingDateStart);
        $("[name=\"name\"].input__control")
                .setValue(DataGenerator.Registration.generateInfo("ru").getName());
        $("[name=\"phone\"].input__control")
                .setValue(DataGenerator.Registration.generateInfo("ru").getPhone());
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
