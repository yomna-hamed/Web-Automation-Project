package Tests;

import Pages.P01_RegisterPage;
import TestData.DataProviders;
import Utilities.LogsUtils;
import Utilities.SomeHelperFunctions;
import com.github.javafaker.Faker;
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

public class RegisterTest {
    private WebDriver driver;
    private String email = new Faker().internet().emailAddress();
    long startTime;
    long endTime;

    @BeforeMethod
    public void setup() {
        startTime = System.currentTimeMillis();
        LogsUtils.logger().info("Test case started");
        driver = new ChromeDriver(DriverFactory.setOptions());
        driver.get("https://demowebshop.tricentis.com/register");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test (dataProvider = "RegisterDateProvider" , dataProviderClass = DataProviders.class)
    public void validInputsForRegisterTC(String firstName,String lastName,String password,String confirmedPass) throws IOException {
        new P01_RegisterPage(driver).chooseGender("f")
                .enterFirstName(firstName)
                .enterLastName(lastName)
                .enterEmail(email)
                .enterPassword(password)
                .enterConfirmPassword(confirmedPass)
                .clickRegisterButton();

        new WebDriverWait(driver,Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(P01_RegisterPage.registerComplete));


        Assert.assertEquals(driver.getCurrentUrl(),"https://demowebshop.tricentis.com/registerresult/1");
        System.out.println(email+" "+password);

        new P01_RegisterPage(driver).saveCredentialsAfterRegister(email,password);
    }

    @Test (dataProvider = "RegisterDateProvider" , dataProviderClass = DataProviders.class)
    public void invalidPasswordLength(String firstName,String lastName,String password,String confirmedPass) {
        new P01_RegisterPage(driver).chooseGender("m")
                .enterFirstName(firstName)
                .enterLastName(lastName)
                .enterEmail(email)
                .enterPassword(password)
                .enterConfirmPassword(confirmedPass)
                .clickRegisterButton();

        String errorMessage = driver.findElement(P01_RegisterPage.fieldValidationError).getText();

        Assert.assertEquals(errorMessage,"The password should have at least 6 characters.");
        Assert.assertNotEquals(driver.getCurrentUrl(),"https://demowebshop.tricentis.com/registerresult/1");
    }

    @Test (dataProvider = "RegisterDateProvider" , dataProviderClass = DataProviders.class)
    public void confirmedPasswordDoesNotMatch(String firstName,String lastName,String password,String confirmedPass) {
        new P01_RegisterPage(driver).chooseGender("m")
                .enterFirstName(firstName)
                .enterLastName(lastName)
                .enterEmail(email)
                .enterPassword(password)
                .enterConfirmPassword(confirmedPass)
                .clickRegisterButton();

        String errorMessage = driver.findElement(P01_RegisterPage.fieldValidationError).getText();

        Assert.assertEquals(errorMessage,"The password and confirmation password do not match.");
        Assert.assertNotEquals(driver.getCurrentUrl(),"https://demowebshop.tricentis.com/registerresult/1");
    }

    @Test (dataProvider = "RegisterDateProvider" , dataProviderClass = DataProviders.class)
    public void leaveFirstNameFieldEmpty(String firstName,String lastName,String password,String confirmedPass) {
        new P01_RegisterPage(driver).chooseGender("f")
                .enterFirstName(firstName)
                .enterLastName(lastName)
                .enterEmail(email)
                .enterPassword(password)
                .enterConfirmPassword(confirmedPass)
                .clickRegisterButton();

        String errorMessage = driver.findElement(P01_RegisterPage.fieldValidationError).getText();

        Assert.assertEquals(errorMessage,"First name is required.");
        Assert.assertNotEquals(driver.getCurrentUrl(),"https://demowebshop.tricentis.com/registerresult/1");
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
