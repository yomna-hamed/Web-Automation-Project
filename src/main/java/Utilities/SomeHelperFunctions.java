package Utilities;

import Pages.P03_HomePage;
import com.google.gson.JsonObject;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class SomeHelperFunctions {
    private WebDriver driver;

    public SomeHelperFunctions(WebDriver driver){
        this.driver = driver;
    }

    public WebElement byToWebElement(By locator) {
        return driver.findElement(locator);
    }

    public SomeHelperFunctions clickOnElement(By locator) {
        driver.findElement(locator).click();
        return this;
    }

    public void waitUpdateAfterAddToCard() {
        WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait1.until(ExpectedConditions.attributeToBe(P03_HomePage.notificationBar,"display","block"));
        WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait2.until(ExpectedConditions.attributeToBe(P03_HomePage.notificationBar,"display","none"));
    }

}
