package ru.yandex.scooter;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertEquals;

class TestButtonOnHeader {
    private final WebDriver driver;

    TestButtonOnHeader(WebDriver driver) {
        this.driver = driver;
    }

    public void clickOnOrderButtonToOpenOrderPage() {
        driver.findElement(By.xpath(".//div[@class='Header_Nav__AGCXC']/button[@class='Button_Button__ra12g']")).click();

        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.className("Order_Content__bmtHS"))));
    }

    public void clickOnScooterButtonToOpenMainPage() {
        driver.findElement(By.className("Header_LogoScooter__3lsAR")).click();

        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.urlToBe("https://qa-scooter.praktikum-services.ru/"));
    }

    public void enterIncorrectOrderNumber(String orderNumber) {
        driver.findElement(By.className("Header_Link__1TAG7")).click();

        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOfElementLocated(By.className("Header_SearchInput__3YRIQ")));

        driver.findElement(By.xpath(".//input[@class='Input_Input__1iN_Z Header_Input__xIoUq']")).sendKeys(orderNumber);

        new WebDriverWait(driver, 10);

        driver.findElement(By.xpath(".//button[@class='Button_Button__ra12g Header_Button__28dPO' and text()='Go!']")).click();

        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.urlToBe(String.format("https://qa-scooter.praktikum-services.ru/track?t=%s", orderNumber)));

        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//div[@class='Track_NotFound__6oaoY']/img")));

        driver.navigate().back();
    }

    public String clickOnYandexLogoToOpenYandexPage() {
        driver.findElement(By.className("Header_LogoYandex__3TSOI")).click();
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

@RunWith(Parameterized.class)
public class Header {
    private WebDriver driver;
    private final String orderNumber;

    public Header(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Parameterized.Parameters
    public static Object[][] getOrderNumber() {
        return new Object[][]{
                {"1"},
                {"sdf231"},
        };
    }

    @Before
    public void startUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        //WebDriverManager.firefoxdriver().setup();
        //driver = new FirefoxDriver();
    }

    @Test
    public void testSiteHeader() {
        driver.get("https://qa-scooter.praktikum-services.ru/");

        TestButtonOnHeader testButtonOnHeader = new TestButtonOnHeader(driver);
        testButtonOnHeader.clickOnOrderButtonToOpenOrderPage();
        testButtonOnHeader.clickOnScooterButtonToOpenMainPage();
        testButtonOnHeader.enterIncorrectOrderNumber(orderNumber);
        String actualUrl = testButtonOnHeader.clickOnYandexLogoToOpenYandexPage();
        assertEquals("https://dzen.ru/?yredirect=true", actualUrl);
    }

    @After
    public void teardown() {
        driver.close();
    }
}
