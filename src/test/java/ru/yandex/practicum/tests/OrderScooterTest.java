package ru.yandex.practicum.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.yandex.practicum.pageobjects.MainPage;
import ru.yandex.practicum.pageobjects.OrderPage;

import static org.junit.Assert.assertTrue;

/**
 * Тесты для проверки процесса заказа самоката
 * Используется параметризация для проверки с разными наборами данных
 * и разными точками входа (кнопка "Заказать" вверху и внизу страницы)
 */
@RunWith(Parameterized.class)
public class OrderScooterTest {
    private WebDriver driver;
    private MainPage mainPage;
    private OrderPage orderPage;

    // Параметры теста
    private String orderButtonPosition;
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private String deliveryDate;
    private String rentalPeriod;
    private String scooterColor;
    private String comment;

    public OrderScooterTest(String orderButtonPosition, String firstName, String lastName, 
                           String address, String metroStation, String phone, 
                           String deliveryDate, String rentalPeriod, String scooterColor, 
                           String comment) {
        this.orderButtonPosition = orderButtonPosition;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.deliveryDate = deliveryDate;
        this.rentalPeriod = rentalPeriod;
        this.scooterColor = scooterColor;
        this.comment = comment;
    }

    /**
     * Параметры для тестов - два набора данных для проверки позитивного сценария
     * Первый параметр - позиция кнопки "Заказать" (header или middle)
     */
    @Parameterized.Parameters
    public static Object[][] getOrderData() {
        return new Object[][] {
                // Набор данных 1: кнопка в шапке, короткие данные
                {
                    "header",                    // позиция кнопки
                    "Иван",                      // имя
                    "Иванов",                    // фамилия
                    "Москва, ул. Ленина, д. 1",  // адрес
                    "Сокольники",                // станция метро
                    "+79991234567",              // телефон
                    "20.12.2024",                // дата доставки
                    "сутки",                     // срок аренды
                    "black",                     // цвет самоката
                    "Позвонить за час"           // комментарий
                },
                // Набор данных 2: кнопка в середине, другие данные
                {
                    "middle",                          // позиция кнопки
                    "Мария",                           // имя
                    "Петрова",                         // фамилия
                    "Санкт-Петербург, Невский пр., 100", // адрес
                    "Черкизовская",                    // станция метро
                    "+79157654321",                    // телефон
                    "25.12.2024",                      // дата доставки
                    "двое суток",                      // срок аренды
                    "grey",                            // цвет самоката
                    "Оставить у двери"                 // комментарий
                },
                // Набор данных 3: кнопка в шапке, минимальные данные
                {
                    "header",                          // позиция кнопки
                    "Петр",                            // имя
                    "Сидоров",                         // фамилия
                    "Москва, Тверская, 5",             // адрес
                    "Красносельская",                  // станция метро
                    "+79261112233",                    // телефон
                    "30.12.2024",                      // дата доставки
                    "трое суток",                      // срок аренды
                    "black",                           // цвет самоката
                    ""                                 // без комментария
                },
                // Набор данных 4: кнопка в середине, другая станция метро
                {
                    "middle",                          // позиция кнопки
                    "Анна",                            // имя
                    "Смирнова",                        // фамилия
                    "Москва, Арбат, 20",               // адрес
                    "Сокольники",                      // станция метро
                    "+79123456789",                    // телефон
                    "15.12.2024",                      // дата доставки
                    "четверо суток",                   // срок аренды
                    "grey",                            // цвет самоката
                    "Быстрая доставка"                 // комментарий
                }
        };
    }

    @Before
    public void setUp() {
        // Настройка WebDriver для Chrome
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        mainPage = new MainPage(driver);
        orderPage = new OrderPage(driver);

        mainPage.open();
        mainPage.acceptCookies();
    }

    @Test
    public void testOrderScooter() {
        // Нажимаем на кнопку "Заказать" в зависимости от параметра
        if ("header".equals(orderButtonPosition)) {
            mainPage.clickOrderButtonHeader();
        } else {
            mainPage.clickOrderButtonMiddle();
        }

        // Заполняем первую форму заказа
        orderPage.fillOrderFormFirst(firstName, lastName, address, metroStation, phone);

        // Заполняем вторую форму заказа
        orderPage.fillOrderFormSecond(deliveryDate, rentalPeriod, scooterColor, comment);

        // Подтверждаем заказ
        orderPage.confirmOrder();

        // Проверяем, что появилось сообщение об успешном создании заказа
        assertTrue("Не появилось сообщение об успешном создании заказа", 
                   orderPage.isOrderSuccessDisplayed());
    }

    @After
    public void tearDown() {
        // Закрываем браузер
        if (driver != null) {
            driver.quit();
        }
    }
}
