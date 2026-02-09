package ru.yandex.practicum.pageobjects;


import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * Page Object для страницы оформления заказа
 */
public class OrderPage {
    private WebDriver driver;

    // Форма "Для кого самокат"
    // Поле "Имя"
    private By firstNameInput = By.xpath(".//input[@placeholder='* Имя']");

    // Поле "Фамилия"
    private By lastNameInput = By.xpath(".//input[@placeholder='* Фамилия']");

    // Поле "Адрес: куда привезти заказ"
    private By addressInput = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']");

    // Поле "Станция метро"
    private By metroStationInput = By.xpath(".//input[@placeholder='* Станция метро']");

    // Выпадающий список станций метро
    private By metroStationDropdown = By.className("select-search__select");

    // Пункт станции метро в выпадающем списке (параметризованный)
    private String metroStationOption = ".//div[@class='select-search__select']//*[text()='%s']";

    // Поле "Телефон"
    private By phoneInput = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']");

    // Кнопка "Далее"
    private By nextButton = By.xpath(".//button[text()='Далее']");

    // Форма "Про аренду"
    // Поле "Когда привезти самокат"
    private By deliveryDateInput = By.xpath(".//input[@placeholder='* Когда привезти самокат']");

    // Поле "Срок аренды"
    private By rentalPeriodDropdown = By.className("Dropdown-placeholder");

    // Опция срока аренды (параметризованный)
    private String rentalPeriodOption = ".//div[@class='Dropdown-option' and text()='%s']";

    // Чекбокс "Цвет самоката" - черный жемчуг
    private By colorBlackCheckbox = By.id("black");

    // Чекбокс "Цвет самоката" - серая безысходность
    private By colorGreyCheckbox = By.id("grey");

    // Поле "Комментарий для курьера"
    private By commentInput = By.xpath(".//input[@placeholder='Комментарий для курьера']");

    // Кнопка "Заказать" (финальная)
    private By orderButton = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM' and text()='Заказать']");

    // Модальное окно подтверждения заказа
    // Заголовок "Хотите оформить заказ?"
    private By confirmOrderHeader = By.xpath(".//div[text()='Хотите оформить заказ?']");

    // Кнопка "Да" в модальном окне
    private By confirmYesButton = By.xpath(".//button[text()='Да']");

    // Кнопка "Нет" в модальном окне
    private By confirmNoButton = By.xpath(".//button[text()='Нет']");

    // Модальное окно успешного создания заказа
    // Заголовок "Заказ оформлен"
    private By orderSuccessHeader = By.xpath(".//div[text()='Заказ оформлен']");

    // Кнопка "Посмотреть статус"
    private By viewStatusButton = By.xpath(".//button[text()='Посмотреть статус']");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Заполняет поле "Имя"
     */
    public void setFirstName(String firstName) {
        driver.findElement(firstNameInput).sendKeys(firstName);
    }

    /**
     * Заполняет поле "Фамилия"
     */
    public void setLastName(String lastName) {
        driver.findElement(lastNameInput).sendKeys(lastName);
    }

    /**
     * Заполняет поле "Адрес"
     */
    public void setAddress(String address) {
        driver.findElement(addressInput).sendKeys(address);
    }

    /**
     * Выбирает станцию метро
     */
    public void selectMetroStation(String station) {
        driver.findElement(metroStationInput).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(metroStationDropdown));
        driver.findElement(By.xpath(String.format(metroStationOption, station))).click();
    }

    /**
     * Заполняет поле "Телефон"
     */
    public void setPhone(String phone) {
        driver.findElement(phoneInput).sendKeys(phone);
    }

    /**
     * Нажимает кнопку "Далее"
     */
    public void clickNextButton() {
        driver.findElement(nextButton).click();
    }

    /**
     * Заполняет первую форму заказа (Для кого самокат)
     */
    public void fillOrderFormFirst(String firstName, String lastName, String address, 
                                    String metroStation, String phone) {
        setFirstName(firstName);
        setLastName(lastName);
        setAddress(address);
        selectMetroStation(metroStation);
        setPhone(phone);
        clickNextButton();
    }

    /**
     * Устанавливает дату доставки
     */
    public void setDeliveryDate(String date) {
        WebElement dateInput = driver.findElement(deliveryDateInput);
        dateInput.sendKeys(date);
        dateInput.sendKeys(Keys.ENTER);
    }

    /**
     * Выбирает срок аренды
     */
    public void selectRentalPeriod(String period) {
        driver.findElement(rentalPeriodDropdown).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(String.format(rentalPeriodOption, period))));
        driver.findElement(By.xpath(String.format(rentalPeriodOption, period))).click();
    }

    /**
     * Выбирает цвет самоката (black или grey)
     */
    public void selectScooterColor(String color) {
        if ("black".equalsIgnoreCase(color)) {
            driver.findElement(colorBlackCheckbox).click();
        } else if ("grey".equalsIgnoreCase(color)) {
            driver.findElement(colorGreyCheckbox).click();
        }
    }

    /**
     * Заполняет комментарий для курьера
     */
    public void setComment(String comment) {
        driver.findElement(commentInput).sendKeys(comment);
    }

    /**
     * Нажимает финальную кнопку "Заказать"
     */
    public void clickOrderButton() {
        driver.findElement(orderButton).click();
    }

    /**
     * Заполняет вторую форму заказа (Про аренду)
     */
    public void fillOrderFormSecond(String date, String rentalPeriod, String color, String comment) {
        setDeliveryDate(date);
        selectRentalPeriod(rentalPeriod);
        selectScooterColor(color);
        if (comment != null && !comment.isEmpty()) {
            setComment(comment);
        }
        clickOrderButton();
    }

    /**
     * Подтверждает заказ в модальном окне
     */
    public void confirmOrder() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmOrderHeader));
        driver.findElement(confirmYesButton).click();
    }

    /**
     * Проверяет, появилось ли сообщение об успешном создании заказа
     */
    public boolean isOrderSuccessDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(orderSuccessHeader));
            return driver.findElement(orderSuccessHeader).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Получает текст заголовка успешного заказа
     */
    public String getOrderSuccessText() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(orderSuccessHeader));
        return driver.findElement(orderSuccessHeader).getText();
    }
}
