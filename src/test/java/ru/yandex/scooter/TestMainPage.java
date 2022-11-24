package ru.yandex.scooter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.yandex.scooter.pageObjects.MainPage;

import static org.junit.Assert.assertEquals;

import java.util.List;

@RunWith(Parameterized.class)
public class TestMainPage extends BaseTest {
    private WebDriver driver;
    private final String nameOfDropdownListElement;
    private final String dropdownItemText;
    private final boolean expected;

    public TestMainPage(String nameOfDropdownListElement, String dropdownItemText, boolean expected) {
        this.nameOfDropdownListElement = nameOfDropdownListElement;
        this.dropdownItemText = dropdownItemText;
        this.expected = expected;
    }

    @Before
    public void startUp() {
        this.driver = getDriver(BrowserName.Firefox);
    }

    @Parameterized.Parameters(name = "{index}: название кнопки - {0}, выпадающий текст - {1}")
    public static Object[][] getText() {
        return new Object[][]{
                {"Сколько это стоит? И как оплатить?", "Сутки — 400 рублей. Оплата курьеру — наличными или картой.", true}, {"Я жизу за МКАДом, привезёте?", "Да, обязательно. Всем самокатов! И Москве, и Московской области.", true},
                {"Сколько это стоит? И как оплатить?", "Да, обязательно. Всем самокатов! И Москве, и Московской области.", false},
        };
    }

    @Test
    public void checkChapterOfImportant() {
        driver.get("https://qa-scooter.praktikum-services.ru/");

        MainPage sectionOfImportant = new MainPage(driver);
        sectionOfImportant.scrollToDropdownList();

        List<WebElement> listDropdownElements = sectionOfImportant.getListDropdownElements();
        boolean actually = sectionOfImportant.checkButtonMatchesText(listDropdownElements, nameOfDropdownListElement, dropdownItemText);
        assertEquals(actually, expected);
    }

    @After
    public void teardown() {
        driver.close();
    }
}
