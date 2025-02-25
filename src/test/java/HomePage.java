import org.hamcrest.MatcherAssert;
import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import static org.hamcrest.CoreMatchers.is;
import java.time.Duration;
import java.util.Map;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
public class HomePage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Локаторы кнопок "Заказать"
    private final By orderButtonHead = By.xpath(".//div[contains(@class, 'Header_Nav')]/button[text()='Заказать']");
    private final By orderButtonMiddle = By.xpath(".//div[contains(@class, 'Home_FinishButton')]/button[text()='Заказать']");

    // Локатор кнопки "Да все привыкли" (принятие cookie)
    private final By cookieAcceptButton = By.xpath(".//button[contains(@class, 'App_CookieButton') and text()='да все привыкли']");

    // Локатор секции "Вопросы о важном"
    private final By homeFAQ = By.xpath(".//div[contains(@class, 'Home_FAQ')]");

    // База вопросов и ответов (ключ - индекс вопроса, значение - текст ответа)
    private static final Map<String, String> FAQ_DATA = Map.of(
            "0", "Сутки — 400 рублей. Оплата курьеру — наличными или картой.",
            "1", "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим.",
            "2", "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня...",
            "3", "Только начиная с завтрашнего дня. Но скоро станем расторопнее.",
            "4", "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010.",
            "5", "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток...",
            "6", "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои.",
            "7", "Да, обязательно. Всем самокатов! И Москве, и Московской области."
    );

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    // Нажатие на кнопку "Да, все привыкли" (принятие cookie)
    public void clickCookieAcceptButton() {
        driver.findElement(cookieAcceptButton).click();
    }

    // Клик по кнопке "Заказать" (в заголовке)
    public void clickOrderButtonHead() {
               driver.findElement(orderButtonHead).click();
    }

    // Клик по кнопке "Заказать" (в центре)
    public void clickOrderButtonMiddle() {
                driver.findElement(orderButtonMiddle).click();
    }

    // Клик по вопросу в секции FAQ
    public void clickQuestion(String index) {
        driver.findElement(By.id("accordion__heading-" + index)).click();
    }

    // Получение текста ответа
    public String getAnswerText(String index) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("accordion__panel-" + index))).getText();
    }

    // Проверка, что ответ соответствует ожидаемому тексту
    public void verifyAnswer(String index) {
        String actualText = getAnswerText(index);
        MatcherAssert.assertThat("Ответ на вопрос " + index + " не соответствует ожидаемому!",
                actualText, is(FAQ_DATA.get(index)));
    }

    // Скролл до секции FAQ
    public void scrollToFAQ() {
        WebElement faqSection = driver.findElement(homeFAQ);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", faqSection);
    }
}

}
