package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.javaQA.delivery.DataGenerator;


import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

class DeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        // TODO: добавить логику теста в рамках которого будет выполнено планирование и перепланирование встречи.
        // Для заполнения полей формы можно использовать пользователя validUser и строки с датами в переменных
        // firstMeetingDate и secondMeetingDate. Можно также вызывать методы generateCity(locale),
        // generateName(locale), generatePhone(locale) для генерации и получения в тесте соответственно города,
        // имени и номера телефона без создания пользователя в методе generateUser(String locale) в датагенераторе
        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=date] .input__control")
                .press(Keys.chord(Keys.SHIFT,Keys.HOME),Keys.DELETE)
                .setValue(firstMeetingDate);
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] .input__control").setValue(validUser.getPhone());
        $("[data-test-id=agreement] span.checkbox__box").click();
        $$("button").find(Condition.exactText("Запланировать")).click();
        $("[data-test-id=success-notification] .notification__title")
                .should(Condition.visible)
                .shouldHave(Condition.exactText("Успешно!"));
        $("[data-test-id=success-notification] .notification__content")
                .shouldHave(Condition.text("Встреча успешно запланирована на " + firstMeetingDate));
        $("[data-test-id=date] .input__control")
                .press(Keys.chord(Keys.SHIFT,Keys.HOME),Keys.DELETE)
                .setValue(secondMeetingDate);
        $$("button").find(Condition.exactText("Запланировать")).click();
        $("[data-test-id=replan-notification] .notification__title")
                .should(Condition.visible, Duration.ofSeconds(4))
                .shouldHave(Condition.exactText("Необходимо подтверждение"));
        $$("[data-test-id=replan-notification] .notification__content")
                .find(Condition.exactText("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $$("[data-test-id=replan-notification] button").find(Condition.exactText("Перепланировать")).click();
        $("[data-test-id=success-notification] .notification__title")
                .should(Condition.visible, Duration.ofSeconds(4)).shouldHave(Condition.exactText("Успешно!"));
        $("[data-test-id=success-notification] .notification__content")
                .shouldHave(Condition.text("Встреча успешно запланирована на  " + secondMeetingDate));
    }
}
