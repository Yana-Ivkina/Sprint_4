package ru.yandex.scooter.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class MainPage {
    private final By blockImportant = By.className("accordion");
    private final By elementsOfSectionImport = By.className("accordion__item");
    private final By buttonListImportant = By.className("accordion__button");
    private final By textListImportant = By.xpath(".//div[@class='accordion__panel']/p");

    private final WebDriver driver;

    public MainPage(WebDriver driver) {
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
