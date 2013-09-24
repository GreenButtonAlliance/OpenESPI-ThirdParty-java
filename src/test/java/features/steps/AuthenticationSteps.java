package features.steps;

import cucumber.api.java.en.Then;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertTrue;

public class AuthenticationSteps {
    private WebDriver driver = WebDriverSingleton.getInstance();

    @Then("^I should see Retail Customer home page$")
    public void I_should_see_Retail_Customer_home_page() throws Throwable {
        assertTrue(driver.getCurrentUrl().endsWith("/ThirdParty/"));
        assertTrue(driver.getPageSource().contains("Logout"));
    }
}
