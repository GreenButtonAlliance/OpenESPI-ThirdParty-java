package features.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertTrue;

public class OAuthSteps {
    private WebDriver driver = WebDriverSingleton.getInstance();

    @Given("^a logged in Retail Customer$")
    public void a_logged_in_Retail_Customer() throws Throwable {
       StepUtils.login(StepUtils.USERNAME, StepUtils.PASSWORD);
    }

    @When("^I navigate to Data Custodian list$")
    public void I_navigate_to_Data_Custodian_list() throws Throwable {
        WebElement link = driver.findElement(By.linkText("Data Custodians"));
        link.click();
    }

    @Then("^I should see a list of Data Custodians$")
    public void I_should_see_a_list_of_Data_Custodians() throws Throwable {
        assertTrue(driver.getPageSource().contains("ConEdison"));
    }
}
