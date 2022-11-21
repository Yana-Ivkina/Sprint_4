package ru.yandex.scooter;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.Assert.assertEquals;

import java.util.List;

class SectionOfImportant {
    private final WebDriver driver;
    private final By blockImportant = By.className("accordion");
    private final By elementsOfSectionImport = By.className("accordion__item");
    private final By buttonListImportant = By.className("accordion__button");
    private final By textListImportant = By.xpath(".//div[@class='accordion__panel']/p");

    public SectionOfImportant(WebDriver driver) {
        this.driver = driver;
    }

    public List<WebElement> getListDropdownElements() {
        return driver.findElements(elementsOfSectionImport);
    }

    public boolean checkButtonMatchesText(List<WebElement> elementsList, String nameDropdownListElement, String dropdownItemText) {
        for (WebElement webElement : elementsList) {
            if (!nameDropdownListElement.equals(webElement.findElement(buttonListImportant).getText())) {
                continue;
            }

            webElement.findElement(buttonListImportant).click();

            if (dropdownItemText.equals(webElement.findElement(textListImportant).getText())) {
                return true;
            }
        }
        return false;
    }

    public void scrollToDropdownList() {
        WebElement element = driver.findElement(blockImportant);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
    }
}

@RunWith(Parameterized.class)
public class MainPage {
    private WebDriver driver;
    private final String nameOfDropdownListElement;
    private final String dropdownItemText;
    private final boolean expected;

    public MainPage(String nameOfDropdownListElement, String dropdownItemText, boolean expected) {
        this.nameOfDropdownListElement = nameOfDropdownListElement;
        this.dropdownItemText = dropdownItemText;
        this.expected = expected;
    }

    @Before
    public void startUp() {
        //WebDriverManager.chromedriver().setup();
        //driver = new ChromeDriver();
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
    }

    @Parameterized.Parameters
    public static Object[][] getText() {
        return new Object[][]{
                {"Сколько это стоит? И как оплатить?", "Сутки — 400 рублей. Оплата курьеру — наличными или картой.", true},
                {"Я жизу за МКАДом, привезёте?", "Да, обязательно. Всем самокатов! И Москве, и Московской области.", true},
                {"Сколько это стоит? И как оплатить?", "Да, обязательно. Всем самокатов! И Москве, и Московской области.", false},
        };
    }

    @Test
    public void checkChapterOfImportant() {
        driver.get("https://qa-scooter.praktikum-services.ru/");

        SectionOfImportant sectionOfImportant = new SectionOfImportant(driver);
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
