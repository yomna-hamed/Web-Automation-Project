package Pages;

import Utilities.SomeHelperFunctions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P04_ShoppingCartPage {
    private final WebDriver driver;
    private final By shoppingCartTopLink = By.cssSelector("li[id='topcartlink'] a[href='/cart']");
    public static By removeProductCheckBox = By.cssSelector("input[name='removefromcart']:nth-of-type(1)");
    private final By updateShoppingCartButton = By.cssSelector("input[name='updatecart']");
    private final By continueShoppingButton = By.cssSelector("input[name='continueshopping']");
    private final By acceptTermsCheckBox = By.cssSelector("input[id='termsofservice']");
    public static By acceptTermsAlert = By.cssSelector("div[tabindex='-1']");
    private final By checkOutButton = By.cssSelector("button[id='checkout']");
    public static By checkOutKeyWord = By.tagName("h1");

    public P04_ShoppingCartPage(WebDriver driver) {
        this.driver = driver;
    }

    public P04_ShoppingCartPage goToShoppingCartPage() {
        new SomeHelperFunctions(driver).clickOnElement(shoppingCartTopLink);
        return this;
    }

    public P04_ShoppingCartPage clickOnRemoveProductCheckBox() {
        new SomeHelperFunctions(driver).clickOnElement(removeProductCheckBox);
        return this;
    }

    public void clickOnContinueShoppingButton() {
        new SomeHelperFunctions(driver).clickOnElement(continueShoppingButton);
    }

    public P04_ShoppingCartPage clickOnAcceptTermsCheckBox() {
        new SomeHelperFunctions(driver).clickOnElement(acceptTermsCheckBox);
        return this;
    }

    public P04_ShoppingCartPage clickOnCheckOutButton() {
        new SomeHelperFunctions(driver).clickOnElement(checkOutButton);
        return this;
    }

    public P04_ShoppingCartPage clickOnupdateShoppingCartButton() {
        new SomeHelperFunctions(driver).clickOnElement(updateShoppingCartButton);
        return this;
    }
}
