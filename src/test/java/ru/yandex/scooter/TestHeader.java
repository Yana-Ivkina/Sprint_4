package ru.yandex.scooter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import ru.yandex.scooter.pageObjects.Header;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class TestHeader extends BaseTest {
    private WebDriver driver;
    private final String orderNumber;

    public TestHeader(String orderNumber) {
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
        this.driver = getDriver(BrowserName.Chrome);
    }

    @Test
    public void testSiteHeader() {
        driver.get("https://qa-scooter.praktikum-services.ru/");

        Header testButtonOnHeader = new Header(driver);
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
