package Pages;

import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.FileWriter;
import java.io.IOException;

public class P01_RegisterPage {
    private final WebDriver driver;
    private final By homePageLink = By.className("ico-register");
    private final By maleButton = By.id("gender-male");
    private final By femaleButton = By.id("gender-female");
    private final By firstName = By.id("FirstName");
    private final By lastName = By.id("LastName");
    private final By email = By.id("Email");
    private final By password = By.id("Password");
    public static By fieldValidationError = By.cssSelector("span.field-validation-error span");
    private final By confirmedPassword = By.id("ConfirmPassword");
    private final By registerButton = By.id("register-button");
    public static By registerComplete = By.cssSelector("div.result");


    public P01_RegisterPage(WebDriver driver) {
        this.driver = driver;
    }

    public P01_RegisterPage goToHomePage() {
        driver.findElement(homePageLink).click();
        return this;
    }

    public P01_RegisterPage chooseGender(String gender) {
        if(gender.equals("m"))
            driver.findElement(maleButton).click();
        else if(gender.equals("f"))
            driver.findElement(femaleButton).click();

        return this;
    }

    public P01_RegisterPage enterFirstName(String firstNameText) {
        driver.findElement(firstName).sendKeys(firstNameText);
        return this;
    }

    public P01_RegisterPage enterLastName(String lastNameText) {
        driver.findElement(lastName).sendKeys(lastNameText);
        return this;
    }

    public P01_RegisterPage enterEmail(String emailText) {
        driver.findElement(email).sendKeys(emailText);
        return this;
    }

    public P01_RegisterPage enterPassword(String passwordText) {
        driver.findElement(password).sendKeys(passwordText);
        return this;
    }

    public P01_RegisterPage enterConfirmPassword(String confirmedPasswordText) {
        driver.findElement(confirmedPassword).sendKeys(confirmedPasswordText);
        return this;
    }

    public P01_RegisterPage clickRegisterButton() {
        driver.findElement(registerButton).click();
        return this;
    }

    public void saveCredentialsAfterRegister(String email,String password) throws IOException {
        JSONObject registerCredentials = new JSONObject();
        registerCredentials.put("email",email);
        registerCredentials.put("password",password);

        FileWriter writer = new FileWriter("D:\\AutomationCourse\\WebAutomationProject\\src\\test\\java\\TestData\\RegisterCredentials.json");
        writer.write(registerCredentials.toJSONString());
        writer.flush();
        writer.close();
    }

}
