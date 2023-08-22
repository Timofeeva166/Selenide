package ru.netology.selenide;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {

    public String date(int target) {
        return LocalDate.now().plusDays(target).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/valid.csv") //тестирует валидные данные
    public void shouldTestValidData(String city, int target, String name, String phone) {
        $("[data-test-id='city'] input").setValue(city);
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a", Keys.DELETE); //очищает поле
        $("[data-test-id='date'] input").sendKeys(date(target));
        $("[data-test-id='name'] input").setValue(name);
        $("[data-test-id='phone'] input").setValue(phone);
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $("[data-test-id='notification'] .notification__title").shouldBe(visible,
                Duration.ofSeconds(15)).shouldHave(exactText("Успешно!"));
        $("[data-test-id='notification'] .notification__content").shouldBe(visible,
                Duration.ofSeconds(15)).shouldHave(exactText("Встреча успешно забронирована на " + date(target)));
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/invalidForCity.csv") //тестирует невалидные значения для города
    public void shouldTestInvalidDataForCity(String city, int target, String name, String phone) {
        $("[data-test-id='city'] input").setValue(city);
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a", Keys.DELETE); //очищает поле
        $("[data-test-id='date'] input").sendKeys(date(target));
        $("[data-test-id='name'] input").setValue(name);
        $("[data-test-id='phone'] input").setValue(phone);
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $("[data-test-id='city'] .input__sub").shouldBe(visible,
                Duration.ofSeconds(15)).shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/invalidForName.csv") //тестирует невалидные значения для имени
    public void shouldTestInvalidDataForName(String city, int target, String name, String phone) {
        $("[data-test-id='city'] input").setValue(city);
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a", Keys.DELETE); //очищает поле
        $("[data-test-id='date'] input").sendKeys(date(target));
        $("[data-test-id='name'] input").setValue(name);
        $("[data-test-id='phone'] input").setValue(phone);
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $("[data-test-id='name'] .input__sub").shouldBe(visible,
                Duration.ofSeconds(15)).shouldHave(exactText("Имя и Фамилия указаные неверно. " +
                "Допустимы только русские буквы, пробелы и дефисы."));
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/invalidForPhone.csv") //тестирует невалидные значения для телефона
    public void shouldTestInvalidDataForPhone(String city, int target, String name, String phone) {
        $("[data-test-id='city'] input").setValue(city);
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a", Keys.DELETE); //очищает поле
        $("[data-test-id='date'] input").sendKeys(date(target));
        $("[data-test-id='name'] input").setValue(name);
        $("[data-test-id='phone'] input").setValue(phone);
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $("[data-test-id='phone'] .input__sub").shouldBe(visible,
                Duration.ofSeconds(15)).shouldHave(exactText("Телефон указан неверно. " +
                "Должно быть 11 цифр, например, +79012345678."));
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/invalidForDate.csv") //тестирует невалидные значения для даты
    public void shouldTestInvalidDataForDate(String city, int target, String name, String phone) {
        $("[data-test-id='city'] input").setValue(city);
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a", Keys.DELETE); //очищает поле
        $("[data-test-id='date'] input").sendKeys(date(target));
        $("[data-test-id='name'] input").setValue(name);
        $("[data-test-id='phone'] input").setValue(phone);
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $("[data-test-id='date'] .input__sub").shouldBe(visible,
                Duration.ofSeconds(15)).shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/valid.csv")
    public void shouldTestWithoutCheckbox(String city, int target, String name, String phone) { //не поставлена галочка
        $("[data-test-id='city'] input").setValue(city);
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a", Keys.DELETE); //очищает поле
        $("[data-test-id='date'] input").sendKeys(date(target));
        $("[data-test-id='name'] input").setValue(name);
        $("[data-test-id='phone'] input").setValue(phone);
        $("button.button").click();

        $("[data-test-id='agreement'] .checkbox__text").shouldBe(visible,
                Duration.ofSeconds(15)).shouldHave(exactText("Я соглашаюсь с условиями обработки и " +
                "использования моих персональных данных"));
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/valid.csv") //не заполнено поле "город"
    public void shouldTestWithoutCity(String city, int target, String name, String phone) {
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a", Keys.DELETE); //очищает поле
        $("[data-test-id='date'] input").sendKeys(date(target));
        $("[data-test-id='name'] input").setValue(name);
        $("[data-test-id='phone'] input").setValue(phone);
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $("[data-test-id='city'] .input__sub").shouldBe(visible,
                Duration.ofSeconds(15)).shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/valid.csv") //не заполнено поле "дата"
    public void shouldTestWithoutDate(String city, int target, String name, String phone) {
        $("[data-test-id='city'] input").setValue(city);
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a", Keys.DELETE); //очищает поле
        $("[data-test-id='name'] input").setValue(name);
        $("[data-test-id='phone'] input").setValue(phone);
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $("[data-test-id='date'] .input__sub").shouldBe(visible,
                Duration.ofSeconds(15)).shouldHave(exactText("Неверно введена дата"));
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/valid.csv") //не заполнено поле "имя"
    public void shouldTestWithoutName(String city, int target, String name, String phone) {
        $("[data-test-id='city'] input").setValue(city);
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a", Keys.DELETE); //очищает поле
        $("[data-test-id='date'] input").sendKeys(date(target));
        $("[data-test-id='phone'] input").setValue(phone);
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $("[data-test-id='name'] .input__sub").shouldBe(visible,
                Duration.ofSeconds(15)).shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/valid.csv") //не заполнено поле "телефон"
    public void shouldTestWithoutPhone(String city, int target, String name, String phone) {
        $("[data-test-id='city'] input").setValue(city);
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a", Keys.DELETE); //очищает поле
        $("[data-test-id='date'] input").sendKeys(date(target));
        $("[data-test-id='name'] input").setValue(name);
        $("[data-test-id='agreement']").click();
        $("button.button").click();

        $("[data-test-id='phone'] .input__sub").shouldBe(visible,
                Duration.ofSeconds(15)).shouldHave(exactText("Поле обязательно для заполнения"));
    }
}