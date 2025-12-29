package ru.netology.carddelivery;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    @Test
    void shouldSubmitFormSuccessfully() {
        open("http://localhost:9999");

        String meetingDate = LocalDate.now()
                .plusDays(3)
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        $("[data-test-id=city] input").sendKeys("Москва");

        var dateInput = $("[data-test-id=date] input");
        dateInput.doubleClick();
        dateInput.sendKeys(Keys.BACK_SPACE);
        dateInput.sendKeys(meetingDate);

        $("[data-test-id=name] input").sendKeys("Иван Иванов");
        $("[data-test-id=phone] input").sendKeys("+79991234567");
        $("[data-test-id=agreement]").click();

        $$("button").findBy(Condition.text("Забронировать")).click();

        $("[data-test-id=notification]")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("Успешно!"))
                .shouldHave(Condition.text(meetingDate));
    }
}

