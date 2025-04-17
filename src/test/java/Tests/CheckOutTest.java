package Tests;

import Pages.P02_LoginPage;
import Pages.P03_HomePage;
import Pages.P04_ShoppingCartPage;
import Pages.P05_CheckOutPage;
import TestData.DataProviders;
import Utilities.LogsUtils;
import Utilities.SomeHelperFunctions;
import org.openqa.selenium.NoSuchElementException;
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

public class CheckOutTest {
    private WebDriver driver;
    String firstName = "Yomna";
    String lastName = "Hamed";
    String company = "ITI";
    String city = "Sohag";
    String address1 = "ElKashef st";
    String address2 = "Elzahraa st";
    String zipCode = "123";
    String phoneNumber = "0123456789";
    String faxNumber = "456";
    long startTime;
    long endTime;

    @BeforeMethod
    public void setup() throws FileNotFoundException {
        startTime = System.currentTimeMillis();
        LogsUtils.logger.info("Test case started");
        driver = new ChromeDriver();
        driver.get("https://demowebshop.tricentis.com/");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
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

        new WebDriverWait(driver,Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(P04_ShoppingCartPage.checkOutKeyWord));
    }

    @Test (priority = 1)
    public void completeCheckOutInfoTC() throws FileNotFoundException {
        try {
            new P05_CheckOutPage(driver).selectNewAddressOption()
                    .enterFirstName(firstName)
                    .enterLastName(lastName)
                    .enterCompany(company)
                    .selectCountry(3)
                    .enterCity(city)
                    .enterAddress1(address1)
                    .enterAddress2(address2)
                    .enterZipCode(zipCode)
                    .enterPhoneNumber(phoneNumber)
                    .enterFaxNumber(faxNumber)
                    .completeCheckOutProcessAfterEnteringAddress();
        } catch (NoSuchElementException e) {
            new P05_CheckOutPage(driver).enterFirstName(firstName)
                    .enterLastName(lastName)
                    .enterCompany(company)
                    .selectCountry(3)
                    .enterCity(city)
                    .enterAddress1(address1)
                    .enterAddress2(address2)
                    .enterZipCode(zipCode)
                    .enterPhoneNumber(phoneNumber)
                    .enterFaxNumber(faxNumber)
                    .completeCheckOutProcessAfterEnteringAddress();
        }

        new WebDriverWait(driver,Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(P05_CheckOutPage.orderSuccessfullyConfirmed));

        Assert.assertEquals(driver.getCurrentUrl(),"https://demowebshop.tricentis.com/checkout/completed/");
    }

    @Test (priority = 2)
    public void backToPreviousStepDuringCheckOutTC() {
        new P05_CheckOutPage(driver).selectNewAddressOption()
                .enterFirstName(firstName)
                .enterLastName(lastName)
                .enterCompany(company)
                .selectCountry(4)
                .enterCity(city)
                .enterAddress1(address1)
                .enterAddress2(address2)
                .enterZipCode(zipCode)
                .enterPhoneNumber(phoneNumber)
                .enterFaxNumber(faxNumber);

        new SomeHelperFunctions(driver).clickOnElement(P05_CheckOutPage.AddressContinueButton1);
        new SomeHelperFunctions(driver).clickOnElement(P05_CheckOutPage.backButton);

        boolean isVisible = new WebDriverWait(driver,Duration.ofSeconds(5)).until(ExpectedConditions.not(ExpectedConditions.invisibilityOfElementLocated(P05_CheckOutPage.AddressContinueButton1)));

        Assert.assertTrue(isVisible);

    }

    @Test (priority = 3)
    public void displayOrderInfoAfterConfirmTC() {
        new P05_CheckOutPage(driver).selectNewAddressOption()
                .enterFirstName(firstName)
                .enterLastName(lastName)
                .enterCompany(company)
                .selectCountry(1)
                .enterCity(city)
                .enterAddress1(address1)
                .enterAddress2(address2)
                .enterZipCode(zipCode)
                .enterPhoneNumber(phoneNumber)
                .enterFaxNumber(faxNumber)
                .completeCheckOutProcessAfterEnteringAddress();

        String orderNumber = new P05_CheckOutPage(driver).getOrderNumber();

        new SomeHelperFunctions(driver).clickOnElement(P05_CheckOutPage.orderInfoLink);

        boolean isVisible = new WebDriverWait(driver,Duration.ofSeconds(2)).until(ExpectedConditions.not(ExpectedConditions.invisibilityOfElementLocated(P05_CheckOutPage.orderInformationTitle)));

        Assert.assertTrue(isVisible);
        Assert.assertEquals(driver.getCurrentUrl(),"https://demowebshop.tricentis.com/orderdetails/"+orderNumber);
    }

    @Test (priority = 4)
    public void hitReOrderTC() {
        new P05_CheckOutPage(driver).selectNewAddressOption()
                .enterFirstName(firstName)
                .enterLastName(lastName)
                .enterCompany(company)
                .selectCountry(6)
                .enterCity(city)
                .enterAddress1(address1)
                .enterAddress2(address2)
                .enterZipCode(zipCode)
                .enterPhoneNumber(phoneNumber)
                .enterFaxNumber(faxNumber)
                .completeCheckOutProcessAfterEnteringAddress();

        new SomeHelperFunctions(driver).clickOnElement(P05_CheckOutPage.orderInfoLink);
        new SomeHelperFunctions(driver).clickOnElement(P05_CheckOutPage.reOrderButton);

        Assert.assertEquals(driver.getCurrentUrl(),"https://demowebshop.tricentis.com/cart");
    }


    @AfterMethod
    public void close(ITestResult result) throws IOException {
        endTime = System.currentTimeMillis();
        LogsUtils.logger.info("Test case ended");
        LogsUtils.logger.info("Test duration: " + (endTime - startTime) + "ms");
        if(ITestResult.FAILURE == result.getStatus())
            new SomeHelperFunctions(driver).takeScreenShot("ScreenShot On Failure");
        driver.quit();
    }
}
