package features.steps;

import cucumber.api.java.en.Then;
import org.energyos.espi.common.test.WebDriverSingleton;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertTrue;

public class AuthenticationSteps {
    private WebDriver driver = WebDriverSingleton.getInstance();

    @Then("^I should see Retail Customer home page$")
    public void I_should_see_Retail_Customer_home_page() throws Throwable {
        assertTrue(driver.getCurrentUrl().endsWith("/ThirdParty/RetailCustomer/1/home"));
        assertTrue(driver.getPageSource().contains("Logout"));
    }
}
