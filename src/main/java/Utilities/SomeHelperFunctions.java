package Utilities;

import Pages.P03_HomePage;
import com.google.gson.JsonObject;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class SomeHelperFunctions {
    private WebDriver driver;
    private String ScreenShot_Path = "D:\\AutomationCourse\\WebAutomationProject\\TestOutputs\\ScreenShots\\";

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

    public void takeScreenShot(String screenShotName) throws IOException {
        File screenSrc = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        File screenDes = new File(ScreenShot_Path+screenShotName+".png");
        FileUtils.copyFile(screenSrc,screenDes);
        Allure.addAttachment(screenShotName, Files.newInputStream(Path.of(screenDes.getPath())));
    }

}
