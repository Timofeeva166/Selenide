package ru.netology.selenide;
import org.junit.jupiter.api.Test;
import java.time.Duration;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {

    @Test
    public void shouldTestWithDefaultData() {
        open("http://localhost:9999");
        $("[data-test-id='city']").setValue("Москва");
        $("[data-test-id='name']").setValue("Андрей");
        $("[data-test-id='phone']").setValue("+78888888888");
        $("[data-test-id='agreement']").click();
        $("button").click();

        $("[data-test-id='notification'] .notification__title").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__content").shouldBe(visible, Duration.ofSeconds(15));

    }

}