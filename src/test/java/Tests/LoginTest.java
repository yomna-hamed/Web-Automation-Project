package Tests;

import Pages.P02_LoginPage;
import TestData.DataProviders;
import Utilities.LogsUtils;
import Utilities.SomeHelperFunctions;
import drivers.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

public class LoginTest {
    private WebDriver driver;
    long startTime;
    long endTime;

    @BeforeMethod
    public void setup() {
        startTime = System.currentTimeMillis();
        LogsUtils.logger().info("Test case started");
        driver = new ChromeDriver(DriverFactory.setOptions());
        driver.get("https://demowebshop.tricentis.com/login");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Test (dataProvider = "LoginDateProvider", dataProviderClass = DataProviders.class)
    public void loginWithRegisteredEmail(String email,String password) {
        new P02_LoginPage(driver).enterEmail(email)
                .enterPassword(password)
                .clickLoginButton();

        new WebDriverWait(driver,Duration.ofSeconds(2)).until(ExpectedConditions.invisibilityOfElementLocated(P02_LoginPage.signInTitle));

        Assert.assertEquals(driver.getCurrentUrl(),"https://demowebshop.tricentis.com/");
    }

    @Test (dataProvider = "LoginDateProvider", dataProviderClass = DataProviders.class)
    public void loginWithUnRegisteredEmail(String email,String password) {
        String unRegisteredEmail = "unregisteredemail@gmail.com";
        new P02_LoginPage(driver).enterEmail(unRegisteredEmail)
                .enterPassword(password)
                .clickLoginButton();

        Assert.assertNotEquals(driver.getCurrentUrl(),"https://demowebshop.tricentis.com/");
    }

    @Test (dataProvider = "LoginDateProvider", dataProviderClass = DataProviders.class)
    public void loginWithWrongPassword(String email,String password) {
        String wrongPassword = "123456789";
        new P02_LoginPage(driver).enterEmail(email)
                .enterPassword(wrongPassword)
                .clickLoginButton();

        Assert.assertNotEquals(driver.getCurrentUrl(),"https://demowebshop.tricentis.com/");
    }

    @AfterMethod
    public void close(ITestResult result) throws IOException {
        endTime = System.currentTimeMillis();
        LogsUtils.logger().info("Test case ended");
        LogsUtils.logger().info("Test duration: " + (endTime - startTime) + "ms");
        new SomeHelperFunctions(driver).takeScreenShot("Final view of "+ result.getMethod().getMethodName());
        driver.quit();
    }
}
