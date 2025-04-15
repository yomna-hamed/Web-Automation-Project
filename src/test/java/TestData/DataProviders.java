package TestData;

import com.github.javafaker.Faker;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Method;

public class DataProviders {

    @DataProvider (name = "RegisterDateProvider")
    public Object[][] getRegisterData(Method method) throws FileNotFoundException {
        FileReader reader = new FileReader("D:\\AutomationCourse\\WebAutomationProject\\src\\test\\java\\TestData\\RegisterData.json");
        JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
        JsonObject registerData = jsonObject.getAsJsonObject("RegisterData");
        String testCaseName = method.getName();

        JsonObject record = registerData.getAsJsonObject(testCaseName);

        return new Object[][] {
                {record.get("firstname").getAsString(),record.get("lastname").getAsString()
                        ,record.get("password").getAsString(),record.get("confirmedpass").getAsString()}
        };
    }

    @DataProvider (name = "LoginDateProvider")
    public Object[][] getLoginData() throws FileNotFoundException {
        FileReader reader = new FileReader("D:\\AutomationCourse\\WebAutomationProject\\src\\test\\java\\TestData\\RegisterCredentials.json");
        JsonObject loginData = JsonParser.parseReader(reader).getAsJsonObject();

        return new Object[][] {
                {loginData.get("email").getAsString(),loginData.get("password").getAsString()}
        };
    }

    public static String getE2EScenarioData(String fileName,String key) throws FileNotFoundException {
        FileReader reader = new FileReader("D:\\AutomationCourse\\WebAutomationProject\\src\\test\\java\\TestData\\"+fileName+".json");
        JsonObject E2EScenarioData = JsonParser.parseReader(reader).getAsJsonObject();
        String[] keys = key.split("\\.");
        JsonElement element = E2EScenarioData;

        for (String k : keys) {
            if (element.isJsonObject()) {
                element = element.getAsJsonObject().get(k);
            } else {
                return null;
            }
        }

        return element.getAsString();
    }

    public static String getLoginDataFunction(String key) throws FileNotFoundException {
        FileReader reader = new FileReader("D:\\AutomationCourse\\WebAutomationProject\\src\\test\\java\\TestData\\RegisterCredentials.json");
        JsonObject loginData = JsonParser.parseReader(reader).getAsJsonObject();
        return loginData.get(key).getAsString();

    }

    public static String getSearchText(String fileName,String key) throws FileNotFoundException {
        FileReader reader = new FileReader("D:\\AutomationCourse\\WebAutomationProject\\src\\test\\java\\TestData\\"+fileName+".json");
        JsonObject searchText = JsonParser.parseReader(reader).getAsJsonObject();
        return searchText.get(key).getAsString();
    }


}
