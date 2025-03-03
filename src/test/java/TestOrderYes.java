import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import  org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.Assert.assertEquals;
import java.time.Duration;

@RunWith(Parameterized.class)
public class TestOrderYes {
    private WebDriver driver;
    private WebDriverWait wait;

    private final String name;
    private final String surname;
    private final String address;
    private final String metro;
    private final String phone;
    private final String date;
    private final String rentalPeriod;
    private final String colour;
    private final String comment;

    public TestOrderYes (String name, String surname, String address, String metro, String phone, String date, String rentalPeriod, String colour, String comment) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.metro = metro;
        this.phone = phone;
        this.date = date;
        this.rentalPeriod = rentalPeriod;
        this.colour = colour;
        this.comment = comment;
    }

    @Parameterized.Parameters
    public static Object[][] getOrderData() {
        return new Object[][]{
                {"Ян", "Тестов", "Москва, ул. Ленина, д. 1", "Черкизовская", "89997775533", "25.03.2025", "сутки", "чёрный жемчуг", "Хорошего дня!"},
                {"Альбус Персиваль Вульфрик Брайан Дамблдор", "Поттер", "Москва, ул. Пушкина, д. 10", "Сокольники", "89996664422", "27.04.2025", "двое суток", "серая безысходность", "Улыбнитесь!"}
        };
    }

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new FirefoxDriver(); //  FirefoxDriver()/ChromeDriver()
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.get("https://qa-scooter.praktikum-services.ru/order"); // Прямой переход на страницу заказа
    }

    @Test
    public void testOrderFlow() {
        HomePage homePage = new HomePage(driver);
        OrderPage orderPage = new OrderPage(driver);

        // **Используем метод `acceptCookies()` из HomePage**
        homePage.clickCookieAcceptButton();

        // **Начинаем оформление заказа через кнопку в заголовке**
        homePage.clickOrderButtonHead();

        // **Заполняем первую форму (личные данные)**
        orderPage.setName(name);
        orderPage.setSurname(surname);
        orderPage.setAddress(address);
        orderPage.setMetroStation(metro);
        orderPage.setPhone(phone);
        orderPage.clickNextButton();  // Переход ко второй форме

        // **Заполняем вторую форму (детали аренды)**
        orderPage.setDeliveryDate(date);
        orderPage.setRentalPeriod(rentalPeriod);
        orderPage.selectColour(colour);
        orderPage.setComment(comment);
        orderPage.clickOrderButton();
        orderPage.confirmOrder();

        // **Проверяем, что заказ оформлен**
        assertEquals(OrderPage.BUTTON_STATUS_TEXT, orderPage.getButtonStatusText());
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
