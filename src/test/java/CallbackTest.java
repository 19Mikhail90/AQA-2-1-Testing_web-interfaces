
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static java.lang.Thread.sleep;

class CallbackTest {
    private WebDriver driver;


    @BeforeAll
    static void setUpAll() {
        System.setProperty("webdriver.chrome.driver", "./chromedriver/chromedriver.exe");
    }

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    public void shouldHappyPathTest() throws InterruptedException {
        ChromeOptions options = new ChromeOptions(); // Подскажите в комментариях правильно ли я расположил опцию headless. т.к. окно браузера запускается,  но теста не видать. Но в итоге он проходит
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Ганизада Ахад Гани-оглы");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79519506677");
        driver.findElement(By.cssSelector(".checkbox")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        String actualMassage = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText().strip();
        String expectedMassage = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        Assertions.assertEquals(expectedMassage, actualMassage, "Сообщение не соответствует ожидаемому");
        driver.quit(); // дописал чтоб закрывалось окно браузера, но что-то не закрывается.
    }

}

