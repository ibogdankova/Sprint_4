import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.junit.Assert.assertEquals;
import  org.openqa.selenium.firefox.FirefoxDriver;

public class TestOrderButton {

    private static WebDriver driver;
    private HomePage homePage;
    private OrderPage orderPage;

    @BeforeClass
    public static void setUpClass() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @Before
    public void setUp() {
        driver.get("https://qa-scooter.praktikum-services.ru/");
        homePage = new HomePage(driver);
        orderPage = new OrderPage(driver);
           }

    @Test
    public void testOrderButtonHead() {
        homePage.clickOrderButtonHead();
        assertEquals("Для кого самокат", orderPage.getOrderHeaderText());
    }

    @Test
    public void testOrderButtonMiddle() {
        homePage.clickCookieAcceptButton();
        homePage.clickOrderButtonMiddle();
        assertEquals("Для кого самокат", orderPage.getOrderHeaderText());
    }

    @AfterClass
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
