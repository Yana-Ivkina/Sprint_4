package ru.yandex.scooter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import ru.yandex.scooter.pageObjects.OrderPage;

@RunWith(Parameterized.class)
public class TestOrderPage extends BaseTest {
    private WebDriver driver;
    private final String name;
    private final String surname;
    private final String address;
    private final String nameOfStation;
    private final String phoneNumber;
    private final String data;
    private final String rentalPeriod;
    private final String colorOfScooter;
    private final String comment;

    public TestOrderPage(String name, String surname, String address, String nameOfStation, String phoneNumber, String data, String rentalPeriod, String colorOfScooter, String comment) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.nameOfStation = nameOfStation;
        this.phoneNumber = phoneNumber;
        this.data = data;
        this.rentalPeriod = rentalPeriod;
        this.colorOfScooter = colorOfScooter;
        this.comment = comment;
    }

    @Before
    public void startUp() {
        this.driver = getDriver(BrowserName.Firefox);
    }

    @Parameterized.Parameters(name = "{index}: {0} {1}, адрес: {2}, станция метро: {3}, номер телефона: {4}, когда привезти: {5}, срок аренды: {6}, цвет: {7}, комментарий для курьера: {8}")
    public static Object[][] getText() {
        return new Object[][]{
                {"Андрей", "Романов", "Ленина 84", "Владыкино", "89347283495", "30.12.2022", "двое суток", "чёрный жемчуг", "Спасибо"},
                {"Екатерина", "Алексеенко", "Каслина 190", "Зорге", "89236592019", "30.11.2022", "семеро суток", "серая безысходность", "Позвоните за час"},
        };
    }

    @Test
    public void checkChapterOfImportant() {
        driver.get("https://qa-scooter.praktikum-services.ru/");

        OrderPage positiveScript = new OrderPage(driver);
        positiveScript.clickButtonOrder();
        positiveScript.fillFirstFields(name, surname, address, nameOfStation, phoneNumber);
        positiveScript.clickButtonNext();
        positiveScript.fillSecondFields(data, rentalPeriod, colorOfScooter, comment);
        positiveScript.waitingWindowsWithSuccessfulOrder();
    }

    @After
    public void teardown() {
        driver.close();
    }
}
