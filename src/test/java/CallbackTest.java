
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

class CallbackTest {
    private WebDriver driver;


    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeAll
    static void setUpAll() {
    }

    @BeforeEach
    void setupTest() {
        ChromeOptions options = new ChromeOptions(); // Подскажите в комментариях правильно ли я расположил опцию headless. т.к. окно браузера запускается,  но теста не видать. Но в итоге он проходит
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver = new ChromeDriver();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    public void shouldHappyPathTest() {

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

    @Test
    public void shouldValidationCheckTest() {

        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Petr Vodkin");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79519506677");
        driver.findElement(By.cssSelector(".checkbox")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        String actualMassage = driver.findElement(By.cssSelector("[class='input__sub']")).getText().strip();
        String expectedMassage = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        Assertions.assertEquals(expectedMassage, actualMassage, "Сообщение не соответствует ожидаемому");
        driver.quit();

    }
}

