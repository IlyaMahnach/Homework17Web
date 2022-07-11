package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


import static org.junit.jupiter.api.Assertions.*;

public class CardOrderTest {

    private WebDriver driver;

    @BeforeAll
    public static void setUp() {
        WebDriverManager.chromedriver().setup();

    }

    @BeforeEach
    void setUp2() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("disable-infobars");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        options.addArguments("--disable-extensions");
        options.addArguments("--no-sandbox");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void shouldTestSuccessOrderCorrectFilling() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Андрей Петров-Иванов");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79994995555");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        assertEquals("  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text);


    }


    @Test
    public void shouldTestFailingIfIncorrectTel() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Андрей Петров-Иванов");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+7999499555500");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text);


    }

    @Test
    public void shouldTestFailingIfIncorrectName() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Andrey Petrov");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79994995555");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text);


    }

    @Test
    public void shouldTestFailingIfNoName() {
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79994995555");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text);


    }

    @Test
    public void shouldTestFailingIfNoTel() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Андрей Петров-Иванов");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text);


    }

    @Test
    public void shouldTestFailingIfNoCheckBox() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Андрей Петров-Иванов");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79994995555");
        driver.findElement(By.tagName("button")).click();
        String text = driver.findElement(By.className("checkbox__text")).getCssValue("color");
        assertEquals("rgba(255, 92, 92, 1)", text);


    }
}