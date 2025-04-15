package Pages;

import Utilities.SomeHelperFunctions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class P05_CheckOutPage {
    private final WebDriver driver;
    private final By addressSelect = By.cssSelector("select#billing-address-select");
    private final By firstNameBox = By.id("BillingNewAddress_FirstName");
    private final By lastNameBox = By.id("BillingNewAddress_LastName");
    private final By companyBox = By.id("BillingNewAddress_Company");
    private final By countrySelect = By.cssSelector("select#BillingNewAddress_CountryId");
    private final By cityBox = By.id("BillingNewAddress_City");
    private final By address1Box = By.id("BillingNewAddress_Address1");
    private final By address2Box = By.id("BillingNewAddress_Address2");
    private final By zipCodeBox = By.id("BillingNewAddress_ZipPostalCode");
    private final By phoneNumberBox = By.id("BillingNewAddress_PhoneNumber");
    private final By faxNumberBox = By.id("BillingNewAddress_FaxNumber");
    public static By AddressContinueButton1 = By.xpath("(//input[contains(@class,'new-address-next-step-button')])[1]");
    public static By pickInStoreCheckBox = By.id("PickUpInStore");
    public static By backButton = By.xpath("(//p[@class='back-link'])[1] //a");
    public static By AddressContinueButton2 = By.xpath("(//input[contains(@class,'new-address-next-step-button')])[2]");
    public static By cashOnDeliveryPayment = By.id("paymentmethod_0");
    public static By paymentMethodContinueButton = By.xpath("//input[contains(@class,'payment-method-next-step-button')]");
    public static By paymentInfoContinueButton = By.xpath("//input[contains(@class,'payment-info-next-step-button')]");
    public static By confirmOrderButton = By.xpath("//input[contains(@class,'confirm-order-next-step-button')]");
    public static By orderInfoLink = By.cssSelector("ul.details li:nth-of-type(2) a");
    public static By orderSuccessfullyConfirmed = By.tagName("strong");
    public static By orderNumberParagraph = By.cssSelector("ul.details li:nth-of-type(1)");
    public static By orderInformationTitle = By.tagName("h1");
    public static By reOrderButton = By.xpath("//input[contains(@class,'re-order-button')]");

    public P05_CheckOutPage(WebDriver driver) {
        this.driver = driver;
    }

    public P05_CheckOutPage selectNewAddressOption() {
        Select select = new Select(driver.findElement(addressSelect));
        select.selectByVisibleText("New Address");
        return this;
    }

    public P05_CheckOutPage selectCountry(int index) {
        Select select = new Select(driver.findElement(countrySelect));
        select.selectByIndex(index);
        return this;
    }

    public P05_CheckOutPage enterFirstName(String firstNameText) {
        driver.findElement(firstNameBox).clear();
        driver.findElement(firstNameBox).sendKeys(firstNameText);
        return this;
    }

    public P05_CheckOutPage enterLastName(String lastNameText) {
        driver.findElement(lastNameBox).clear();
        driver.findElement(lastNameBox).sendKeys(lastNameText);
        return this;
    }

    public P05_CheckOutPage enterCompany(String companyName) {
        driver.findElement(companyBox).clear();
        driver.findElement(companyBox).sendKeys(companyName);
        return this;
    }

    public P05_CheckOutPage enterCity(String cityName) {
        driver.findElement(cityBox).clear();
        driver.findElement(cityBox).sendKeys(cityName);
        return this;
    }

    public P05_CheckOutPage enterAddress1(String address1Text) {
        driver.findElement(address1Box).clear();
        driver.findElement(address1Box).sendKeys(address1Text);
        return this;
    }

    public P05_CheckOutPage enterAddress2(String address2Text) {
        driver.findElement(address2Box).clear();
        driver.findElement(address2Box).sendKeys(address2Text);
        return this;
    }

    public P05_CheckOutPage enterZipCode(String zipCodeText) {
        driver.findElement(zipCodeBox).clear();
        driver.findElement(zipCodeBox).sendKeys(zipCodeText);
        return this;
    }

    public P05_CheckOutPage enterPhoneNumber(String phoneNumberText) {
        driver.findElement(phoneNumberBox).clear();
        driver.findElement(phoneNumberBox).sendKeys(phoneNumberText);
        return this;
    }

    public P05_CheckOutPage enterFaxNumber(String faxNumberText) {
        driver.findElement(faxNumberBox).clear();
        driver.findElement(faxNumberBox).sendKeys(faxNumberText);
        return this;
    }


    public String getOrderNumber() {
        String text = driver.findElement(orderNumberParagraph).getText();
        return text.replaceAll("[^0-9]", "");
    }

    public P05_CheckOutPage completeCheckOutProcessAfterEnteringAddress() {
        new SomeHelperFunctions(driver).clickOnElement(P05_CheckOutPage.AddressContinueButton1);
        new SomeHelperFunctions(driver).clickOnElement(P05_CheckOutPage.pickInStoreCheckBox);
        new SomeHelperFunctions(driver).clickOnElement(P05_CheckOutPage.AddressContinueButton2);
        new SomeHelperFunctions(driver).clickOnElement(P05_CheckOutPage.cashOnDeliveryPayment);
        new SomeHelperFunctions(driver).clickOnElement(P05_CheckOutPage.paymentMethodContinueButton);
        new SomeHelperFunctions(driver).clickOnElement(P05_CheckOutPage.paymentInfoContinueButton);
        new SomeHelperFunctions(driver).clickOnElement(P05_CheckOutPage.confirmOrderButton);
        return this;
    }

    public void checkNewAddressFeatureAvaliableOrNot() {
        boolean isVisible = new WebDriverWait(driver,Duration.ofSeconds(5)).until(ExpectedConditions.not(ExpectedConditions.invisibilityOfElementLocated(addressSelect)));
        if (isVisible)
            selectNewAddressOption()
                .enterFirstName("Yomna")
                .enterLastName("Hamed")
                .enterCompany("ITI")
                .selectCountry(6)
                .enterCity("Sohag")
                .enterAddress1("ElKashef st")
                .enterAddress2("Elzahraa st")
                .enterZipCode("123")
                .enterPhoneNumber("0123456789")
                .enterFaxNumber("456")
                .completeCheckOutProcessAfterEnteringAddress();


        else
            enterFirstName("Yomna")
                .enterLastName("Hamed")
                .enterCompany("ITI")
                .selectCountry(6)
                .enterCity("Sohag")
                .enterAddress1("ElKashef st")
                .enterAddress2("Elzahraa st")
                .enterZipCode("123")
                .enterPhoneNumber("0123456789")
                .enterFaxNumber("456")
                .completeCheckOutProcessAfterEnteringAddress();

    }

}
