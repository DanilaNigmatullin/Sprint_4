package ru.yandex.practicum.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * Page Object для главной страницы Яндекс.Самокат
 */
public class MainPage {
    private WebDriver driver;

    // URL главной страницы
    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru/";

    // Кнопка "Заказать" в шапке страницы
    private By orderButtonHeader = By.xpath(".//button[@class='Button_Button__ra12g']");

    // Кнопка "Заказать" в середине страницы
    private By orderButtonMiddle = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM']");

    // Заголовок "Вопросы о важном"
    private By faqHeader = By.xpath(".//div[text()='Вопросы о важном']");

    // Стрелки вопросов в разделе FAQ
    private String faqQuestionArrow = ".//div[@id='accordion__heading-%d']";

    // Панель с ответом на вопрос
    private String faqAnswerPanel = ".//div[@id='accordion__panel-%d']";

    // Текст ответа на вопрос
    private String faqAnswerText = ".//div[@id='accordion__panel-%d']/p";

    // Логотип Яндекс
    private By yandexLogo = By.xpath(".//a[@class='Header_LogoYandex__3TSOI']");

    // Логотип Самокат
    private By scooterLogo = By.xpath(".//a[@class='Header_LogoScooter__3lsAR']");

    // Кнопка принятия cookie
    private By cookieButton = By.id("rcc-confirm-button");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Открывает главную страницу
     */
    public void open() {
        driver.get(BASE_URL);
    }

    /**
     * Нажимает на кнопку "Заказать" в шапке страницы
     */
    public void clickOrderButtonHeader() {
        driver.findElement(orderButtonHeader).click();
    }

    /**
     * Нажимает на кнопку "Заказать" в середине страницы
     */
    public void clickOrderButtonMiddle() {
        WebElement button = driver.findElement(orderButtonMiddle);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", button);
        button.click();
    }

    /**
     * Прокручивает страницу до раздела "Вопросы о важном"
     */
    public void scrollToFaq() {
        WebElement faqElement = driver.findElement(faqHeader);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", faqElement);
    }

    /**
     * Нажимает на вопрос в разделе FAQ по индексу
     * @param index индекс вопроса (0-7)
     */
    public void clickFaqQuestion(int index) {
        WebElement question = driver.findElement(By.xpath(String.format(faqQuestionArrow, index)));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", question);
        question.click();
    }

    /**
     * Ждет появления ответа на вопрос и возвращает текст
     * @param index индекс вопроса (0-7)
     * @return текст ответа
     */
    public String getFaqAnswerText(int index) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(faqAnswerPanel, index))));
        return driver.findElement(By.xpath(String.format(faqAnswerText, index))).getText();
    }

    /**
     * Принимает cookie, если появляется баннер
     */
    public void acceptCookies() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            WebElement cookieBtn = wait.until(ExpectedConditions.elementToBeClickable(cookieButton));
            cookieBtn.click();
        } catch (Exception e) {
        }
    }
}
