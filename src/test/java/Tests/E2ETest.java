package Tests;

import Pages.*;
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;

public class E2ETest {
    private WebDriver driver;
    long startTime;
    long endTime;

    @BeforeMethod
    public void setup() {
        startTime = System.currentTimeMillis();
        LogsUtils.logger().info("Test case started");
        driver = new ChromeDriver(DriverFactory.setOptions());
        driver.get("https://demowebshop.tricentis.com/");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Test
    public void EndToEndScenarioTC() throws FileNotFoundException {
        new P02_LoginPage(driver).clickOnLoginPage()
                .enterEmail(DataProviders.getLoginDataFunction("email"))
                .enterPassword(DataProviders.getLoginDataFunction("password"))
                .clickLoginButton();

        new WebDriverWait(driver,Duration.ofSeconds(2)).until(ExpectedConditions.invisibilityOfElementLocated(P02_LoginPage.signInTitle));

        new P03_HomePage(driver).selectCategory("books")
                .clickAddToCardButton();

        new SomeHelperFunctions(driver).waitUpdateAfterAddToCard();

        new P04_ShoppingCartPage(driver).goToShoppingCartPage()
                .clickOnAcceptTermsCheckBox()
                .clickOnCheckOutButton();

        new P05_CheckOutPage(driver).checkNewAddressFeatureAvaliableOrNot();

        new WebDriverWait(driver,Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(P05_CheckOutPage.orderSuccessfullyConfirmed));

        Assert.assertEquals(driver.getCurrentUrl(),"https://demowebshop.tricentis.com/checkout/completed/");
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
