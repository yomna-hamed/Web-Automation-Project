package Tests;

import Pages.P02_LoginPage;
import Pages.P03_HomePage;
import Pages.P04_ShoppingCartPage;
import TestData.DataProviders;
import Utilities.SomeHelperFunctions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

public class ShoppingCartTest {
    private WebDriver driver;

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.get("https://demowebshop.tricentis.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Test
    public void removeProductFromCartFeatureTC() {
        new P03_HomePage(driver).selectCategory("books")
                .clickAddToCardButton();

        new SomeHelperFunctions(driver).waitUpdateAfterAddToCard();

        String  cartQuantityTextBeforeRemoveProduct = driver.findElement(P03_HomePage.cartQuantity).getText();
        cartQuantityTextBeforeRemoveProduct = cartQuantityTextBeforeRemoveProduct.replaceAll("[^0-9-]", "");
        int cartQuantityNumberBeforeRemoveProduct = Integer.parseInt(cartQuantityTextBeforeRemoveProduct);
        System.out.println(cartQuantityNumberBeforeRemoveProduct);

        new P04_ShoppingCartPage(driver).goToShoppingCartPage()
                .clickOnRemoveProductCheckBox()
                .clickOnupdateShoppingCartButton();

        new WebDriverWait(driver,Duration.ofSeconds(5)).until(ExpectedConditions.invisibilityOfElementLocated(P04_ShoppingCartPage.removeProductCheckBox));

        String  cartQuantityTextAfterRemoveProduct = driver.findElement(P03_HomePage.cartQuantity).getText();
        cartQuantityTextAfterRemoveProduct = cartQuantityTextAfterRemoveProduct.replaceAll("[^0-9-]", "");
        int cartQuantityNumberAfterRemoveProduct = Integer.parseInt(cartQuantityTextAfterRemoveProduct);
        System.out.println(cartQuantityNumberAfterRemoveProduct);

        Assert.assertEquals(cartQuantityNumberAfterRemoveProduct,cartQuantityNumberBeforeRemoveProduct-1);
    }

    @Test
    public void continueShoppingFeatureTC() {
        new P03_HomePage(driver).selectCategory("digital-downloads");

        String categoryPageURL = driver.getCurrentUrl();
        System.out.println(categoryPageURL);

        new P03_HomePage(driver).clickAddToCardButton();

        new SomeHelperFunctions(driver).waitUpdateAfterAddToCard();

        new P04_ShoppingCartPage(driver).goToShoppingCartPage()
                .clickOnContinueShoppingButton();

        new WebDriverWait(driver,Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(P03_HomePage.categoryKeyWord));

        String redirectedUrl = driver.getCurrentUrl();
        System.out.println(categoryPageURL);

        Assert.assertEquals(categoryPageURL,redirectedUrl);
    }

    @Test (dataProvider = "LoginDateProvider", dataProviderClass = DataProviders.class)
    public void checkOutAfterLoginAndAcceptTerms(String email,String password) {
        new P02_LoginPage(driver).clickOnLoginPage()
                .enterEmail(email)
                .enterPassword(password)
                .clickLoginButton();

        new WebDriverWait(driver,Duration.ofSeconds(2)).until(ExpectedConditions.invisibilityOfElementLocated(P02_LoginPage.signInTitle));

        new P03_HomePage(driver).selectCategory("books")
                .clickAddToCardButton();

        new SomeHelperFunctions(driver).waitUpdateAfterAddToCard();

        new P04_ShoppingCartPage(driver).goToShoppingCartPage()
                .clickOnAcceptTermsCheckBox()
                .clickOnCheckOutButton();

        new WebDriverWait(driver,Duration.ofSeconds(2)).until(ExpectedConditions.visibilityOfElementLocated(P04_ShoppingCartPage.checkOutKeyWord));

        String redirectedUrl = driver.getCurrentUrl();

        Assert.assertEquals(redirectedUrl,"https://demowebshop.tricentis.com/onepagecheckout");
    }

    @Test (dataProvider = "LoginDateProvider", dataProviderClass = DataProviders.class)
    public void checkOutWithoutAcceptingTerms(String email,String password) {
        new P02_LoginPage(driver).clickOnLoginPage()
                .enterEmail(email)
                .enterPassword(password)
                .clickLoginButton();

        new WebDriverWait(driver,Duration.ofSeconds(2)).until(ExpectedConditions.invisibilityOfElementLocated(P02_LoginPage.signInTitle));

        new P03_HomePage(driver).selectCategory("books")
                .clickAddToCardButton();

        new SomeHelperFunctions(driver).waitUpdateAfterAddToCard();

        new P04_ShoppingCartPage(driver).goToShoppingCartPage()
                .clickOnCheckOutButton();

        boolean isVisible = new WebDriverWait(driver,Duration.ofSeconds(2)).until(ExpectedConditions.not(ExpectedConditions.invisibilityOfElementLocated(P04_ShoppingCartPage.acceptTermsAlert)));

        Assert.assertNotEquals(driver.getCurrentUrl(),"https://demowebshop.tricentis.com/onepagecheckout");
        Assert.assertTrue(isVisible);
    }

    @AfterMethod
    public void close(ITestResult result) throws IOException {
        if(ITestResult.FAILURE == result.getStatus())
            new SomeHelperFunctions(driver).takeScreenShot("ScreenShot On Failure");
        driver.quit();
    }
}
