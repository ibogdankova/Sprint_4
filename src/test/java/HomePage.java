import org.hamcrest.MatcherAssert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.hamcrest.CoreMatchers.is;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

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

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    // Нажатие на кнопку "Да, все привыкли" (принятие cookie)
    public void clickCookieAcceptButton() {
        wait.until(ExpectedConditions.elementToBeClickable(cookieAcceptButton)).click();
    }

    // Клик по кнопке "Заказать" (в заголовке)
    public void clickOrderButtonHead() {
        wait.until(ExpectedConditions.elementToBeClickable(orderButtonHead)).click();
    }

    // Клик по кнопке "Заказать" (в центре)
    public void clickOrderButtonMiddle() {
        wait.until(ExpectedConditions.elementToBeClickable(orderButtonMiddle)).click();
    }

    // Клик по вопросу в секции FAQ
    public void clickQuestion(String index) {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("accordion__heading-" + index))).click();
    }

    // Получение текста ответа FAQ
    public String getAnswerText(String index) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("accordion__panel-" + index))).getText();
    }

    // Проверка ответа по индексу
    public void verifyAnswer(String index, String expectedText) {
        String actualText = getAnswerText(index);
        MatcherAssert.assertThat("Ответ на вопрос " + index + " некорректен!", actualText, is(expectedText));
    }

    // Скролл до секции FAQ
    public void scrollToFAQ() {
        WebElement faqSection = wait.until(ExpectedConditions.visibilityOfElementLocated(homeFAQ));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", faqSection);
    }
}
