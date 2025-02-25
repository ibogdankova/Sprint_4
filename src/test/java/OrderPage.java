import org.hamcrest.MatcherAssert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.hamcrest.CoreMatchers.is;
import java.time.Duration;

public class OrderPage {
        private final WebDriver driver;
        private final WebDriverWait wait;

        // Текст заголовка страницы "Для кого самокат"
        public static final String TEXT_ORDER_HEADER = "Для кого самокат";
        // Текст кнопки "Посмотреть статус"
        public static final String BUTTON_STATUS_TEXT = "Посмотреть статус";
        // Локаторы
        // заголовок "Для кого самокат"
        private final By orderHead = By.xpath(".//div[contains(@class, 'Order_Header') and text()='Для кого самокат']");
        // кнопка "Посмотреть статус"
        private final By buttonStatus = By.xpath(".//button[text()='Посмотреть статус']");
        // поле "Имя"
        private final By nameField = By.xpath(".//input[@placeholder='* Имя']");
        // поле "Фамилия"
        private final By surnameField = By.xpath(".//input[@placeholder='* Фамилия']");
        // поле "Адрес"
        private final By addressField = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']");
        // поле "Станция метро"
        private final By metroField = By.xpath(".//input[@placeholder='* Станция метро']");
        // поле "Телефон"
        private final By phoneField = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']");
        // кнопка "Далее"
        private final By orderButtonNext = By.xpath(".//div[contains(@class, 'Order_NextButton')]/button[text()='Далее']");
        // поле "Когда привезти самокат"
        private final By datePicker = By.xpath(".//input[@placeholder='* Когда привезти самокат']");
        // выпадающий список "Срок аренды"
        private final By rentalPeriodField = By.className("Dropdown-placeholder");
        // поле "Комментарий для курьера"
        private final By commentField = By.xpath(".//input[@placeholder='Комментарий для курьера']");
        // кнопка "Заказать"
        private final By rentButtonOrder = By.xpath(".//div[contains(@class, 'Order_Buttons')]/button[text()='Заказать']");
        // кнопка подтверждения заказа "Да"
        private final By rentButtonAcceptOrder = By.xpath(".//button[text()='Да']");

        public OrderPage(WebDriver driver) {
            this.driver = driver;
            this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        }

        /**
         * Универсальный метод для заполнения полей ввода
         * @param locator локатор поля ввода
         * @param value текст, который нужно ввести
         */
        private void fillField(By locator, String value) {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            element.clear();
            element.sendKeys(value);
        }

        /**
         * Заполняет поле "Имя"
         * @param name имя пользователя
         */
        public void setName(String name) {
            fillField(nameField, name);
        }

        /**
         * Заполняет поле "Фамилия"
         * @param surname фамилия пользователя
         */
        public void setSurname(String surname) {
            fillField(surnameField, surname);
        }

        /**
         * Заполняет поле "Адрес"
         * @param address адрес доставки
         */
        public void setAddress(String address) {
            fillField(addressField, address);
        }

        /**
         * Выбирает станцию метро
         * @param metro название станции метро
         */
        public void setMetroStation(String metro) {
            WebElement metroInput = wait.until(ExpectedConditions.visibilityOfElementLocated(metroField));
            metroInput.click();
            metroInput.sendKeys(metro, Keys.DOWN, Keys.ENTER);
        }

        /**
         * Заполняет поле "Телефон"
         * @param phone номер телефона
         */
        public void setPhone(String phone) {
            fillField(phoneField, phone);
        }

        /**
         * Нажимает кнопку "Далее" для перехода к следующему шагу заказа
         */
        public void clickNextButton() {
            wait.until(ExpectedConditions.elementToBeClickable(orderButtonNext)).click();
        }

        /**
         * Заполняет поле "Когда привезти самокат"
         * @param date дата доставки
         */
        public void setDeliveryDate(String date) {
            fillField(datePicker, date);
            driver.findElement(datePicker).sendKeys(Keys.ENTER);
        }

        /**
         * Выбирает срок аренды самоката
         * @param rentalPeriod срок аренды (например, "двое суток")
         */
        public void setRentalPeriod(String rentalPeriod) {
            wait.until(ExpectedConditions.elementToBeClickable(rentalPeriodField)).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//div[text()='" + rentalPeriod + "']"))).click();
        }

        /**
         * Заполняет поле "Комментарий для курьера"
         * @param comment текст комментария
         */
        public void setComment(String comment) {
            fillField(commentField, comment);
        }

        /**
         * Выбирает цвет самоката
         * @param colour название цвета (например, "чёрный жемчуг" или "серая безысходность")
         */
        public void selectColour(String colour) {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//label[text()='" + colour + "']"))).click();
        }

        /**
         * Нажимает кнопку "Заказать"
         */
        public void clickOrderButton() {
            wait.until(ExpectedConditions.elementToBeClickable(rentButtonOrder)).click();
        }

        /**
         * Подтверждает заказ, нажав кнопку "Да"
         */
        public void confirmOrder() {
            wait.until(ExpectedConditions.elementToBeClickable(rentButtonAcceptOrder)).click();
        }

        /**
         * Получает текст заголовка страницы "Для кого самокат"
         * @return текст заголовка
         */
        public String getOrderHeaderText() {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(orderHead)).getText();
        }

        /**
         * Получает текст кнопки "Посмотреть статус"
         * @return текст кнопки
         */
        public String getButtonStatusText() {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(buttonStatus)).getText();
        }

        /**
         * Проверяет, что текст элемента на странице соответствует ожидаемому значению
         * @param actualText фактический текст элемента
         * @param expectedText ожидаемый текст
         */
        public void verifyPageElementText(String actualText, String expectedText) {
            MatcherAssert.assertThat(actualText, is(expectedText));
        }


}
