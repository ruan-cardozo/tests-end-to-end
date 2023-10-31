package br.com.alura.leilao.login;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginTest {
    public static final String URL_LOGIN = "http://localhost:8080/login";
    public static final String URL_LOGIN_ERROR = "http://localhost:8080/login?error";
    private WebDriver driver;

    @BeforeAll
    public static void beforeAll() {
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
        this.driver.navigate().to(URL_LOGIN);
    }

    @AfterEach
    public void afterEach() {
        this.driver.quit();
    }

    @Test
    public void deveriaEfetuarLoginComDadosvalidos(){
        driver.findElement(By.id("username")).sendKeys("fulano");
        driver.findElement(By.id("password")).sendKeys("pass");
        driver.findElement(By.id("login-form")).submit();

        Assert.assertFalse(driver.getCurrentUrl().equals(URL_LOGIN));
        Assert.assertEquals("fulano", driver.findElement(By.id("usuario-logado")).getText());
    }

    @Test
    public void deveriaMostrarMensagemDeErroComDadosInvalidos(){
        driver.findElement(By.id("username")).sendKeys("invalido");
        driver.findElement(By.id("password")).sendKeys("123");
        driver.findElement(By.id("login-form")).submit();

        Assert.assertTrue(driver.getCurrentUrl().equals(URL_LOGIN_ERROR));
        Assert.assertTrue(driver.getPageSource().contains("Usuário e senha inválidos."));
        Assert.assertThrows(NoSuchElementException.class, () -> driver.findElement(By.id("usuario-logado")).getText());
    }
}
