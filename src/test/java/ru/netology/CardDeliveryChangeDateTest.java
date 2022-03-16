package ru.netology;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Locale;

public class CardDeliveryChangeDateTest {
    private Faker faker;

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

    private void printTestData(String city, String name, String phone) {
        System.out.println(city);
        System.out.println(name);
        System.out.println(phone);
    }

    @Test
    void shouldGenerateTestData() {
        RegistrationInfo info = DataGenerator.Registration.generateInfo("ru");
        System.out.println(info);
    }

}
