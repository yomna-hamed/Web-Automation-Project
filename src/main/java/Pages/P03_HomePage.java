package Pages;

import Utilities.SomeHelperFunctions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class P03_HomePage {
    private final WebDriver driver;
    private final By searchBox = By.xpath("//input[contains(@class,'search-box-text')]");
    private final By searchButton = By.xpath("//input[contains(@class,'search-box-button')]");
    public static By searchKeyWord = By.tagName("h1");
    public static By searchResult = By.cssSelector("strong.result");
    private final By booksCategory = By.xpath("//div[contains(@class,'block-category-navigation')] //a[@href='/books']");
    private final By computersCategory = By.xpath("//div[contains(@class,'block-category-navigation')] //a[@href='/computers']");
    private final By electronicsCategory = By.xpath("//div[contains(@class,'block-category-navigation')] //a[@href='/electronics']");
    private final By apparelCategory = By.xpath("//div[contains(@class,'block-category-navigation')] //a[@href='/apparel-shoes']");
    private final By digitalCategory = By.xpath(" //div[contains(@class,'block-category-navigation')] //a[@href='/digital-downloads']");
    private final By jewelryCategory = By.xpath("//div[contains(@class,'block-category-navigation')] //a[@href='/jewelry']");
    private final By giftCardCategory = By.xpath("//div[contains(@class,'block-category-navigation')] //a[@href='/gift-cards']");
    private final By sortByDropDown = By.id("products-orderby");
    public static By priceOfProduct = By.xpath("//span[contains(@class,'actual-price')]");
    private final By displayDropDown = By.id("products-pagesize");
    public static By productsBox = By.className("item-box");
    public static By productQuantityBox = By.className("qty-input");
    public static By addToCartButtonFromProductInfo = By.cssSelector("input#add-to-cart-button-5");
    private final By viewAsDropDown = By.id("products-viewmode");
    public static By productsGrid = By.className("product-grid");
    public static By productsList = By.className("product-list");
    private final By addToCartButton = By.xpath("(//input[contains(@class,'product-box-add-to-cart-button')])[3]");
    public static By cartQuantity = By.cssSelector("span[class='cart-qty']");
    public static By notificationBar = By.id("bar-notification");
    public static By categoryKeyWord = By.tagName("h1");


    public P03_HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public P03_HomePage selectCategory(String category) {
        switch (category) {
            case "books" :
                driver.findElement(booksCategory).click();
                break;
            case "computers" :
                driver.findElement(computersCategory).click();
                break;
            case "electronics" :
                driver.findElement(electronicsCategory).click();
                break;
            case "apparel-shoes" :
                driver.findElement(apparelCategory).click();
                break;
            case "digital-downloads" :
                driver.findElement(digitalCategory).click();
                break;
            case "jewelry" :
                driver.findElement(jewelryCategory).click();
                break;
            case "gift-cards" :
                driver.findElement(giftCardCategory).click();
                break;
        }
        return this;
    }

    public P03_HomePage clickAddToCardButton() {
        driver.findElement(addToCartButton).click();
        return new P03_HomePage(driver);
    }

    public void selectDisplayPerPage(String value) {
        Select select = new Select(new SomeHelperFunctions(driver).byToWebElement(displayDropDown));
        select.selectByVisibleText(value);
    }

    public void selectSortBy(int index) {
        Select select = new Select(new SomeHelperFunctions(driver).byToWebElement(sortByDropDown));
        select.selectByIndex(index);
    }

    public void selectViewAs(String value) {
        Select select = new Select(new SomeHelperFunctions(driver).byToWebElement(viewAsDropDown));
        select.selectByVisibleText(value);
    }

    public P03_HomePage enterSearchText(String text) {
        driver.findElement(searchBox).sendKeys(text);
        driver.findElement(searchButton).click();
        return this;
    }

    public P03_HomePage changeProductQuantity(String Quantity) {
        driver.findElement(productQuantityBox).clear();
        driver.findElement(productQuantityBox).sendKeys(Quantity);
        return this;
    }


}
