package Tests;

import Pages.P03_HomePage;
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class HomeTest {
    private WebDriver driver;
    String homeURL = "https://demowebshop.tricentis.com/";

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.get("https://demowebshop.tricentis.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Test
    public void chooseCategoryTC() {
        new P03_HomePage(driver).selectCategory("books");

        Assert.assertEquals(driver.getCurrentUrl(),homeURL+"books");
    }

    @Test
    public void sortByFeatureTC() throws InterruptedException {
        new P03_HomePage(driver).selectCategory("digital-downloads")
                .selectSortBy(4);

        List<WebElement> priceElementsAfterSelect = driver.findElements(P03_HomePage.priceOfProduct);

        //Extracting actual prices of elements
        List<Double> actualPrices= new ArrayList<>();
        for(WebElement priceElement : priceElementsAfterSelect) {
            String priceText = priceElement.getText();
            actualPrices.add(Double.parseDouble(priceText));
        }

        //make a copy of actual prices in another list and sort the copy then comparing them
        List<Double> expectedPrices= new ArrayList<>(actualPrices);
        expectedPrices.sort(Collections.reverseOrder());

        System.out.println("Actual Prices after sorting: " + actualPrices);
        System.out.println("Expected Prices after sorting: " + expectedPrices);
        Assert.assertEquals(expectedPrices,actualPrices);
    }

    @Test
    public void displayPerPageFeatureTC() {
        new P03_HomePage(driver).selectCategory("books")
                .selectDisplayPerPage("4");

        List<WebElement> displayedItems = driver.findElements(P03_HomePage.productsBox);
        int numberOfDisplayedItems = displayedItems.size();
        Assert.assertTrue(numberOfDisplayedItems<=8);
    }

    @Test
    public void viewAsFeatureTC() {
        WebElement grid = new SomeHelperFunctions(driver).byToWebElement(P03_HomePage.productsGrid);
        new P03_HomePage(driver).selectCategory("books")
                .selectViewAs("List");
        boolean gridNotVisible = new WebDriverWait(driver,Duration.ofSeconds(5)).until(ExpectedConditions.invisibilityOf(grid));
        Assert.assertTrue(gridNotVisible);

        WebElement list = new SomeHelperFunctions(driver).byToWebElement(P03_HomePage.productsList);
        new P03_HomePage(driver).selectViewAs("Grid");
        boolean listNotVisible = new WebDriverWait(driver,Duration.ofSeconds(5)).until(ExpectedConditions.invisibilityOf(list));
        Assert.assertTrue(listNotVisible);
    }

    @Test
    public void addToCardFeatureTC() {
        //Get number of products in tha cart before adding another one
        WebElement cartQuantityElementBeforeAffToCart = new SomeHelperFunctions(driver).byToWebElement(P03_HomePage.cartQuantity);
        String cartQuantityTextBeforeAddToCart = cartQuantityElementBeforeAffToCart.getText();
        cartQuantityTextBeforeAddToCart = cartQuantityTextBeforeAddToCart.replaceAll("[^0-9-]", "");
        int cartQuantityNumberBeforeAddToCart = Integer.parseInt(cartQuantityTextBeforeAddToCart);

        new P03_HomePage(driver).selectCategory("apparel-shoes")
                .clickAddToCardButton();

        new WebDriverWait(driver,Duration.ofSeconds(5)).until(ExpectedConditions.attributeContains(P03_HomePage.notificationBar,"display","block"));

        //Get number of products in tha cart after adding another one
        WebElement cartQuantityElementAfterAddToCart = new SomeHelperFunctions(driver).byToWebElement(P03_HomePage.cartQuantity);
        String cartQuantityTextAfterAddToCart = cartQuantityElementAfterAddToCart.getText();
        cartQuantityTextAfterAddToCart = cartQuantityTextAfterAddToCart.replaceAll("[^0-9-]", "");
        int cartQuantityNumberAfterAddToCart = Integer.parseInt(cartQuantityTextAfterAddToCart);

        //Check if the number of products in the card increased by one
        Assert.assertEquals(cartQuantityNumberAfterAddToCart,cartQuantityNumberBeforeAddToCart+1);
    }

    @Test
    public void increaseQuantityOfOrderedProduct() {
        String cartQuantityTextBeforeAddingProducts = new SomeHelperFunctions(driver).byToWebElement(P03_HomePage.cartQuantity).getText().replaceAll("[^0-9-]", "");
        int cartQuantityNumberBeforeAddingProducts = Integer.parseInt(cartQuantityTextBeforeAddingProducts);

        new P03_HomePage(driver).selectCategory("apparel-shoes")
                .clickAddToCardButton();

        new WebDriverWait(driver,Duration.ofSeconds(5)).until(ExpectedConditions.attributeContains(P03_HomePage.notificationBar,"display","block"));

        new SomeHelperFunctions(driver).clickOnElement(P03_HomePage.productsBox);

        new P03_HomePage(driver).changeProductQuantity("4");

        new SomeHelperFunctions(driver).clickOnElement(P03_HomePage.addToCartButtonFromProductInfo);

        new WebDriverWait(driver,Duration.ofSeconds(5)).until(ExpectedConditions.attributeContains(P03_HomePage.notificationBar,"display","block"));

        String cartQuantityTextAfterAddingProducts = driver.findElement(P03_HomePage.cartQuantity).getText().replaceAll("[^0-9-]", "");
        int cartQuantityNumberAfterAddingProducts = Integer.parseInt(cartQuantityTextAfterAddingProducts);

        Assert.assertEquals(cartQuantityNumberAfterAddingProducts,cartQuantityNumberBeforeAddingProducts+5);

    }

    @Test
    public void searchUsingExistingTextTC() throws FileNotFoundException {
        int counter=0;
        new P03_HomePage(driver).selectCategory("apparel-shoes")
                .enterSearchText(DataProviders.getSearchText("SearchText","existed-text1"));

        new WebDriverWait(driver,Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(P03_HomePage.searchKeyWord));

        List<WebElement> displayedProducts = driver.findElements(P03_HomePage.productsBox);
        List<String> extractedText = new ArrayList<>();
        for (WebElement element : displayedProducts) {
            String extractedTextFromCurrentProduct = element.getText();
            extractedText.add(extractedTextFromCurrentProduct);
        }

        for (String text : extractedText) {
            if(text.toLowerCase().contains(DataProviders.getSearchText("SearchText","existed-text1").toLowerCase()))
                counter = counter+1;
        }

        Assert.assertEquals(displayedProducts.size(),counter);
    }

    @Test
    public void searchUsingPartOfExistingTextTC() throws FileNotFoundException {
        int counter=0;
        new P03_HomePage(driver).selectCategory("apparel-shoes")
                .enterSearchText(DataProviders.getSearchText("SearchText","existed-text2"));

        new WebDriverWait(driver,Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(P03_HomePage.searchKeyWord));

        List<WebElement> displayedProducts = driver.findElements(P03_HomePage.productsBox);
        List<String> extractedText = new ArrayList<>();
        for (WebElement element : displayedProducts) {
            String extractedTextFromCurrentProduct = element.getText();
            extractedText.add(extractedTextFromCurrentProduct);
        }

        for (String text : extractedText) {
            if(text.toLowerCase().contains(DataProviders.getSearchText("SearchText","existed-text2").toLowerCase()))
                counter = counter+1;
        }

        Assert.assertEquals(displayedProducts.size(),counter);
    }

    @Test
    public void searchUsingUnExistingTextTC() throws FileNotFoundException {
        new P03_HomePage(driver).selectCategory("apparel-shoes")
                .enterSearchText(DataProviders.getSearchText("SearchText","non-existed-Text"));

        new WebDriverWait(driver,Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(P03_HomePage.searchKeyWord));

        List<WebElement> displayedProducts = driver.findElements(P03_HomePage.productsBox);

        String result = driver.findElement(P03_HomePage.searchResult).getText();

        Assert.assertEquals(displayedProducts.size(),0);
        Assert.assertEquals(result,"No products were found that matched your criteria.");
    }

    @AfterMethod
    public void close(ITestResult result) throws IOException {
        if(ITestResult.FAILURE == result.getStatus())
            new SomeHelperFunctions(driver).takeScreenShot("ScreenShot On Failure");
        driver.quit();
    }
}
