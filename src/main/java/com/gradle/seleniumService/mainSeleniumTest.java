package com.gradle.seleniumService;

import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class mainSeleniumTest {
    private static final String URL = "https://5e.tools/races.html";

//    private final ChromeDriver chromeDriver =
    private WebDriver driver;

    @BeforeClass
    public static void setupWebdriverChromeDriver() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/main/resources/chromedriver.exe");
    }

    @Before
    public void setup() {
        driver = new ChromeDriver();
    }

    public void scrape(final String value){
        driver.get(URL + value);
    }



}
