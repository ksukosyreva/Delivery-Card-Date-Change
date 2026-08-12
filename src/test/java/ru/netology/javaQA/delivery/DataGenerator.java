package ru.netology.javaQA.delivery;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataGenerator {
    private DataGenerator() {
    }

    public static String generateDate(int shift) {
        return LocalDate.now().plusDays(shift).format(DateTimeFormatter.ofPattern("dd.MM.yyy"));
    }

    public static String generateCity(Faker faker) {
        String[] setOfCities = {"Москва", "Санкт-Петербург", "Екатеринбург", "Калининград", "Самара", "Челябинск", "Новосибирск", "Саратов"};
        String city = setOfCities[faker.random().nextInt(setOfCities.length)];
        return city;
    }

    public static String generateName(Faker faker) {
        String name = faker.name().fullName();
        return name;
    }

    public static String generatePhone(Faker faker) {
        String phone = faker.phoneNumber().phoneNumber();
        return phone;
    }

    public static class Registration {
        private static Faker faker;

        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            faker = new Faker(new Locale(locale));
            UserInfo user = new UserInfo(generateCity(faker), generateName(faker), generatePhone(faker));
            return user;
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }
}

