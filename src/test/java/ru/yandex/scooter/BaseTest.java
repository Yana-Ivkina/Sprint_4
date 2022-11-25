package ru.yandex.scooter;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BaseTest {
    protected WebDriver getDriver(BrowserName browserName) {
        switch (browserName) {
            case Chrome:
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver();
            case Firefox:
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver();
            default:
                return null;
        }
    }
}
