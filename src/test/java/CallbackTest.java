
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
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    public void shouldHappyPathTest() throws InterruptedException  {

        driver.get("http://localhost:9999");

        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Ганизада Ахад Гани-оглы");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79519506677");
        driver.findElement(By.cssSelector(".checkbox")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        String actualMassage = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText().strip();
        String expectedMassage = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        Assertions.assertEquals(expectedMassage, actualMassage, "Сообщение не соответствует ожидаемому");
        driver.quit(); // закрывалось окно браузера
    }
    @Test
    public void shouldValidationEmptyFieldName() { //незаполненным полем ввода имени и остальными полями, заполненными валидными данными

        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79519506677");
        driver.findElement(By.cssSelector(".checkbox")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        String actualMassage = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().strip();
        String expectedMassage = "Поле обязательно для заполнения";
        Assertions.assertEquals(expectedMassage, actualMassage, "Сообщение не соответствует ожидаемому");
        driver.quit();
    }
    @Test
    public void shouldValidationEmptyFieldPhone() { //незаполненное поле ввода телефона и остальными полями, заполненными валидными данными

        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Григорий Дятлов");
        driver.findElement(By.cssSelector(".checkbox")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        String actualMassage = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().strip();
        String expectedMassage = "Поле обязательно для заполнения";
        Assertions.assertEquals(expectedMassage, actualMassage, "Сообщение не соответствует ожидаемому");
        driver.quit();
    }

    @Test
    public void shouldValidationFieldsNameAndSurname() { //цифры в поле имя и фамилия

        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("9519506");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79519506677");
        driver.findElement(By.cssSelector(".checkbox")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        String actualMassage = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().strip();
        String expectedMassage = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        Assertions.assertEquals(expectedMassage, actualMassage, "Сообщение не соответствует ожидаемому");
        driver.quit();
    }

    @Test
    public void shouldValidationFieldsPhone() { // Латинские буквы в поле имя и фамилия

        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Petr Vodkin");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79519506677");
        driver.findElement(By.cssSelector(".checkbox")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        String actualMassage = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().strip();
        String expectedMassage = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        Assertions.assertEquals(expectedMassage, actualMassage, "Сообщение не соответствует ожидаемому");
        driver.quit();
    }
    @Test
    public void shouldValidationFieldPhone() { //некорректный номер в поле телефон

        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Григорий Дятлов");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("89519506677");
        driver.findElement(By.cssSelector(".checkbox")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        String actualMassage = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().strip();
        String expectedMassage = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        Assertions.assertEquals(expectedMassage, actualMassage, "Сообщение не соответствует ожидаемому");
        driver.quit();
    }
    @Test
    public void shouldValidationCheckBoxTest() { //поля заполнены, а чек бокс не проставлен.

        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Федор Сумкин");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79519506677");
        driver.findElement(By.cssSelector(".button__text")).click();
        String actualMassage = driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid .checkbox__text")).getText().strip();
        String expectedMassage = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        Assertions.assertEquals(expectedMassage, actualMassage, "Сообщение не соответствует ожидаемому");
        driver.quit();
    }

    @Test
    public void shouldValidationEmptyFieldsAndCheckBox() { // пустые поля и чек бокс

        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector(".button__text")).click();
        String actualMassage = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().strip();
        String expectedMassage = "Поле обязательно для заполнения";
        Assertions.assertEquals(expectedMassage, actualMassage, "Сообщение не соответствует ожидаемому");
        driver.quit();
    }

    @Test
    public void shouldValidationEmptyFields() { // пустые поля

        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector(".checkbox")).click();
        driver.findElement(By.cssSelector(".button__text")).click();
        String actualMassage = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().strip();
        String expectedMassage = "Поле обязательно для заполнения";
        Assertions.assertEquals(expectedMassage, actualMassage, "Сообщение не соответствует ожидаемому");
        driver.quit();
    }

}

