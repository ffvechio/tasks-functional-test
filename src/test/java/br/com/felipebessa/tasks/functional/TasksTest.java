package br.com.felipebessa.tasks.functional;

import com.github.javafaker.Faker;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class TasksTest {

    Faker faker = new Faker(new Locale("pt-BR"));

    public WebDriver setup() throws MalformedURLException {
//        WebDriver driver = new ChromeDriver();
        DesiredCapabilities cap = DesiredCapabilities.chrome();
        WebDriver driver = new RemoteWebDriver(new URL("http://192.168.15.13:4444/wd/hub"), cap);
        driver.navigate().to("http://192.168.15.13:8001/tasks");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }

    @Test
    public void deveSalvarTarefaComSucesso() throws MalformedURLException {
        WebDriver driver = setup();
        try {
            //clicar em add todo
            driver.findElement(By.id("addTodo")).click();

            //escrever descrição
            driver.findElement(By.id("task")).sendKeys(faker.name().fullName() + " " + faker.number().digit());

            //escrever data
            driver.findElement(By.id("dueDate")).sendKeys("10/10/2020");

            //clicar em salvar
            driver.findElement(By.id("saveButton")).click();

            //validar nebsagen de sucesso
            String message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Success!", message);
        } finally {
            driver.quit();
        }

    }

    @Test
    public void naoDeveSalvarTarefaComDataPassada() throws MalformedURLException {
        WebDriver driver = setup();
        try {
            //clicar em add todo
            driver.findElement(By.id("addTodo")).click();

            //escrever descrição
            driver.findElement(By.id("task")).sendKeys(faker.name().fullName() + " " + faker.number().digit());

            //escrever data
            driver.findElement(By.id("dueDate")).sendKeys("10/10/2010");

            //clicar em salvar
            driver.findElement(By.id("saveButton")).click();

            //validar nebsagen de sucesso
            String message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Due date must not be in past", message);
        } finally {
            driver.quit();
        }

    }

    @Test
    public void naoDeveSalvarTarefaSemDescicao() throws MalformedURLException {
        WebDriver driver = setup();
        try {
            //clicar em add todo
            driver.findElement(By.id("addTodo")).click();

            //escrever data
            driver.findElement(By.id("dueDate")).sendKeys("10/10/2020");

            //clicar em salvar
            driver.findElement(By.id("saveButton")).click();

            //validar nebsagen de sucesso
            String message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Fill the task description", message);
        } finally {
            driver.quit();
        }

    }

    @Test
    public void naoDeveSalvarSemData() throws MalformedURLException {
        WebDriver driver = setup();

        try {
            //clicar em add todo
            driver.findElement(By.id("addTodo")).click();

            //escrever descrição
            driver.findElement(By.id("task")).sendKeys(faker.name().fullName() + " " + faker.number().digit());

            //clicar em salvar
            driver.findElement(By.id("saveButton")).click();

            //validar nebsagen de sucesso
            String message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Fill the due date", message);
        } finally {
            driver.quit();
        }
    }

}
