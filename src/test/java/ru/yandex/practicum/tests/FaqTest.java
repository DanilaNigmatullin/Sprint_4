package ru.yandex.practicum.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.yandex.practicum.pageobjects.MainPage;

import static org.junit.Assert.assertEquals;

/**
 * Тесты для проверки раздела "Вопросы о важном"
 * Используется параметризация для проверки всех вопросов
 */
@RunWith(Parameterized.class)
public class FaqTest {
    private WebDriver driver;
    private MainPage mainPage;

    // Параметры теста
    private final int questionIndex;
    private final String expectedAnswer;

    public FaqTest(int questionIndex, String expectedAnswer) {
        this.questionIndex = questionIndex;
        this.expectedAnswer = expectedAnswer;
    }

    /**
     * Параметры для тестов - индекс вопроса и ожидаемый ответ
     */
    @Parameterized.Parameters
    public static Object[][] getFaqData() {
        return new Object[][] {
                {0, "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                {1, "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."},
                {2, "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."},
                {3, "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
                {4, "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},
                {5, "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."},
                {6, "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."},
                {7, "Да, обязательно. Всем самокатов! И Москве, и Московской области."}
        };
    }

    @Before
    public void setUp() {
        // Настройка WebDriver для Chrome
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.acceptCookies();
    }

    @Test
    public void testFaqQuestion() {
        // Прокручиваем до раздела FAQ
        mainPage.scrollToFaq();

        // Кликаем на вопрос
        mainPage.clickFaqQuestion(questionIndex);

        // Получаем текст ответа
        String actualAnswer = mainPage.getFaqAnswerText(questionIndex);

        // Проверяем, что ответ соответствует ожидаемому
        assertEquals("Текст ответа не соответствует ожидаемому", expectedAnswer, actualAnswer);
    }

    @After
    public void tearDown() {
        // Закрываем браузер
        if (driver != null) {
            driver.quit();
        }
    }
}
