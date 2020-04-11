package br.com.felipebessa.tasks.functional.prod;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class HealthCheckIT {

    @Test
    public void healthCheck() throws MalformedURLException, Exception {
        WebDriver driver = new ChromeDriver();
//        DesiredCapabilities cap = DesiredCapabilities.chrome();
//        WebDriver driver = new RemoteWebDriver(new URL("http://192.168.15.21:4444/wd/hub"), cap);
        driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);

        try {
            driver.navigate().to("http://192.168.15.21:9999/tasks");

            //valida se aplicação está up
            String message = driver.findElement(By.id("version")).getText();
            Assert.assertTrue(message.startsWith("build_"));
        } finally {
            //fecha browser
            driver.quit();
        }
    }
}
