import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import org.openqa.selenium.support.ui.WebDriverWait;


@RunWith(Parameterized.class)

public class TestFAQ {

        private WebDriver driver;
        private WebDriverWait wait;
        private final String headingIndex;
        private final String expectedAnswer;

        // Конструктор параметров
        public TestFAQ (String headingIndex, String expectedAnswer) {
            this.headingIndex = headingIndex;
            this.expectedAnswer = expectedAnswer;
        }

        // Параметры теста: индекс вопроса и ожидаемый ответ
        @Parameterized.Parameters
        public static Collection<Object[]> getInformationFAQ() {
            return Arrays.asList(new Object[][]{
                    {"0", "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                    {"1", "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."},
                    {"2", "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня..."},
                    {"3", "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
                    {"4", "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},
                    {"5", "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток..."},
                    {"6", "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."},
                    {"7", "Да, обязательно. Всем самокатов! И Москве, и Московской области."}
            });
        }

     @Before
      public void setUp() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5)); // ✅ Ожидание загрузки элементов
        wait = new WebDriverWait(driver, Duration.ofSeconds(5)); // ✅ Правильный способ для Selenium 4
        driver.get("https://qa-scooter.praktikum-services.ru/");
      }


        @Test
        public void testFAQ() {
            HomePage homePage = new HomePage(driver);

            // Принять cookies
            homePage.clickCookieAcceptButton();

            // Скролл вниз до раздела "Вопросы о важном"
            homePage.scrollToFAQ();

            // Клик на вопрос
            homePage.clickQuestion(headingIndex);

            // Проверка ответа
            homePage.verifyAnswer(headingIndex);
        }

        @After
        public void tearDown() {
            if (driver != null) {
                driver.quit();
            }
        }

}