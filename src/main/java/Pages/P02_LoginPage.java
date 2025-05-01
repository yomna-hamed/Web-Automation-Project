package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P02_LoginPage {
    private final WebDriver driver;
    private final By loginPage = By.className("ico-login");
    private final By email = By.id("Email");
    private final By password = By.id("Password");
    private final By loginButton = By.xpath("//input[contains(@class,'login-button')]");
    public static By signInTitle = By.tagName("h1");

    public P02_LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public P02_LoginPage clickOnLoginPage() {
        driver.findElement(loginPage).click();
        return this;
    }

    public P02_LoginPage enterEmail(String emailText) {
        driver.findElement(email).sendKeys(emailText);
        return this;
    }

    public P02_LoginPage enterPassword(String passwordText) {
        driver.findElement(password).sendKeys(passwordText);
        return this;
    }

    public P03_HomePage clickLoginButton() {
        driver.findElement(loginButton).click();
        return new P03_HomePage(driver);
    }

}
