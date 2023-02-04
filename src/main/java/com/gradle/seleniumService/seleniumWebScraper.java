package com.gradle.seleniumService;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class seleniumWebScraper {

    private WebDriver driver;

    @BeforeClass
    public static void setupWebdriverChromeDriver() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/main/resources/chromedriver.exe");
    }

    @Before
    public void setup() {
        driver = new ChromeDriver();
    }

    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void searchElements() {
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//        new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(40));
        driver.get("https://5e.tools/races.html");


//        System.out.println(driver.);
        int i = 0;
        while(true) {
            boolean flag = driver.findElement(By.className("lst__row ve-flex-col")).isDisplayed();
            if(flag)
                break;
        }
        WebElement myDynamicElement = driver.findElement(By.className("lst__row ve-flex-col"));
//        java.util.List<WebElement> elements = driver.findElements(By.className("lst__row ve-flex-col"));
//        for(WebElement element : elements) {
//            System.out.println(i);
//            i++;
//            System.out.println(element.getText());
//        }
        assertThat(driver.getTitle(), containsString("Races"));
    }

}