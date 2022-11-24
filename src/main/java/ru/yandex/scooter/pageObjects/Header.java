package ru.yandex.scooter.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Header {
    private final By buttonOrderOnHeader = By.xpath(".//div[@class='Header_Nav__AGCXC']/button[@class='Button_Button__ra12g']");
    private final By buttonStatusOrder = By.className("Header_Link__1TAG7");
    private final By fieldStatusOrder = By.className("Header_SearchInput__3YRIQ");
    private final By stringStatusOrder = By.xpath(".//input[@class='Input_Input__1iN_Z Header_Input__xIoUq']");
    private final By buttonGo = By.xpath(".//button[@class='Button_Button__ra12g Header_Button__28dPO' and text()='Go!']");
    private final By imgNoOrder = By.xpath(".//div[@class='Track_NotFound__6oaoY']/img");
    private final By logoYandex = By.className("Header_LogoYandex__3TSOI");
    private final By logoScooter = By.className("Header_LogoScooter__3lsAR");
    private final By pageOrder = By.className("Order_Content__bmtHS");

    private final WebDriver driver;

    public Header(WebDriver driver) {
        this.driver = driver;
    }

    public void clickOnOrderButtonToOpenOrderPage() {
        driver.findElement(buttonOrderOnHeader).click();

        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOfAllElements(driver.findElements(pageOrder)));
    }

    public void clickOnScooterButtonToOpenMainPage() {
        driver.findElement(logoScooter).click();

        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.urlToBe("https://qa-scooter.praktikum-services.ru/"));
    }

    public void enterIncorrectOrderNumber(String orderNumber) {
        driver.findElement(buttonStatusOrder).click();

        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOfElementLocated(fieldStatusOrder));

        driver.findElement(stringStatusOrder).sendKeys(orderNumber);

        new WebDriverWait(driver, 10);

        driver.findElement(buttonGo).click();

        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.urlToBe(String.format("https://qa-scooter.praktikum-services.ru/track?t=%s", orderNumber)));

        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOfElementLocated(imgNoOrder));

        driver.navigate().back();
    }

    public String clickOnYandexLogoToOpenYandexPage() {
        driver.findElement(logoYandex).click();
        String title = "Дзен";
        String actualUrl = "";
        String currentWindow = driver.getWindowHandle();

        for (String winHandle : driver.getWindowHandles()) {
            if (driver.switchTo().window(winHandle).getTitle().equals(title)) {
                actualUrl = driver.getCurrentUrl();
                break;
            }
            driver.switchTo().window(currentWindow);
        }

        driver.navigate().back();

        return actualUrl;
    }
}
