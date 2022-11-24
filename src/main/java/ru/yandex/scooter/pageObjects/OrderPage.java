package ru.yandex.scooter.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class OrderPage {
    private final By fieldName = By.xpath(".//input[@placeholder='* Имя']");
    private final By fieldSurname = By.xpath(".//input[@placeholder='* Фамилия']");
    private final By fieldAddress = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']");
    private final By fieldStation = By.className("select-search__input");
    private final By buttonOrder = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM' and text()='Заказать']");
    private final By pageOrder = By.className("Order_Content__bmtHS");
    private final By listNameStations = By.className("select-search__select");
    private final By buttonNameStation = By.xpath("//button[@tabindex='-1']");
    private final By fieldPhoneNumber = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']");
    private final By buttonNext = By.xpath("//button[text()='Далее']");
    private final By fieldWhen = By.className("Order_MixedDatePicker__3qiay");
    private final By fieldRentalPeriod = By.className("Dropdown-control");
    private final By elementsOfRentalPeriod = By.className("Dropdown-option");
    private final By fieldOfColor = By.className("Checkbox_Label__3wxSf");
    private final By fieldOfComment = By.xpath(".//input[@placeholder='Комментарий для курьера']");
    private final By buttonBack = By.xpath(".//button[text()='Назад']");
    private final By titleMonthAndYearInCalendar = By.className("react-datepicker__current-month");
    private final By buttonArrowInTitleOfCalendar = By.xpath(".//button[@class='react-datepicker__navigation react-datepicker__navigation--next']");
    private final By daysInMonth = By.xpath(".//div[@class='react-datepicker__week']/div");
    private final By orderConfirmationWindow = By.xpath(".//div[@class='Order_Modal__YZ-d3']/div[text()='Хотите оформить заказ?']");
    private final By buttonYesInOrderConfirmationWindow = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM' and text()='Да']");
    private final By windowWithAnOrder = By.xpath(".//div[@class='Order_Modal__YZ-d3']/div[text()='Заказ оформлен']");

    private final WebDriver driver;

    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickButtonOrder() {
        WebElement element = driver.findElement(buttonOrder);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);

        driver.findElement(buttonOrder).click();

        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOfAllElements(driver.findElements(pageOrder)));
    }

    public void fillFirstFields(String name, String surname, String address, String nameOfStation, String phoneNumber) {
        driver.findElement(fieldName).sendKeys(name);
        driver.findElement(fieldSurname).sendKeys(surname);
        driver.findElement(fieldAddress).sendKeys(address);
        driver.findElement(fieldStation).click();

        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOfElementLocated(listNameStations));

        List<WebElement> buttonNameStations = driver.findElements(buttonNameStation);

        for (WebElement buttonNameStation : buttonNameStations) {
            if (nameOfStation.equals(buttonNameStation.getText())) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", buttonNameStation);
                buttonNameStation.click();
                break;
            }
        }

        driver.findElement(fieldPhoneNumber).sendKeys(phoneNumber);
    }

    public void clickButtonNext() {
        driver.findElement(buttonNext).click();

        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOfElementLocated(buttonBack));
    }

    public void fillSecondFields(String dateString, String rentalPeriod, String colorScooter, String comment) {
        // Заполнение формы (* Когда привезти самокат)
        driver.findElement(fieldWhen).click();

        Date date = new SimpleDateFormat("dd.MM.yyyy").parse(dateString, new ParsePosition(0));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String[] monthNames = {"январь", "февраль", "март", "апрель", "май", "июнь", "июль", "август", "сентябрь", "октябрь", "ноябрь", "декабрь"};
        String month = monthNames[calendar.get(Calendar.MONTH)];
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

        // находим нужный месяц и год
        while (!(month + " " + year).equals(driver.findElement(titleMonthAndYearInCalendar).getText())) {
            driver.findElement(buttonArrowInTitleOfCalendar).click();
            new WebDriverWait(driver, 5);
        }

        clickOnDay(day);

        // Заполнение формы (* Срок аренды)
        driver.findElement(fieldRentalPeriod).click();

        List<WebElement> rentalPeriods = driver.findElements(elementsOfRentalPeriod);

        for (WebElement element : rentalPeriods) {
            if (rentalPeriod.equals(element.getText())) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
                element.click();
                break;
            }
        }

        // Заполнение формы (Цвет самоката)
        List<WebElement> listOfCheckboxColor = driver.findElements(fieldOfColor);
        for (WebElement checkboxColor : listOfCheckboxColor) {
            if (colorScooter.equals(checkboxColor.getText())) {
                checkboxColor.click();
                break;
            }
        }

        // Заполнение формы (Комментарий для курьера)
        driver.findElement(fieldOfComment).sendKeys(comment);

        new WebDriverWait(driver, 5);
    }

    public void clickOnDay(String day) {
        List<WebElement> days = driver.findElements(daysInMonth);
        int indexStartCalendar = 0;

        // исключаем начало месяца не с первого числа
        for (WebElement dayMonth : days) {
            if (dayMonth.getText().equals("1")) {
                break;
            }
            indexStartCalendar++;
        }

        // находим нужное число данного месяца
        for (int i = indexStartCalendar; i < days.size(); i++) {
            if (day.equals(days.get(i).getText())) {
                days.get(i).click();
                break;
            }
        }
    }

    public void waitingWindowsWithSuccessfulOrder() {
        driver.findElement(buttonOrder).click();

        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOfElementLocated(orderConfirmationWindow));

        driver.findElement(buttonYesInOrderConfirmationWindow).click();

        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOfElementLocated(windowWithAnOrder));
    }
}
