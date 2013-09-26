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
       login(StepUtils.USERNAME, StepUtils.PASSWORD);
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

    @When("^I select a Data Custodian from the list$")
    public void I_select_a_Data_Custodian_from_the_list() throws Throwable {
        WebElement link = driver.findElement(By.linkText("Data Custodians"));
        link.click();
        WebElement radio = driver.findElement(By.className("data-custodian"));
        radio.click();
        WebElement submit = driver.findElement(By.name("next"));
        submit.click();
    }

    @Then("^I should see the Data Custodian login screen$")
    public void I_should_see_the_Data_Custodian_login_screen() throws Throwable {
        assertTrue(driver.getCurrentUrl().contains("/DataCustodian"));
        assertTrue(driver.getPageSource().contains("Login"));
    }

    @When("^I log into the Data Custodian$")
    public void I_log_into_the_Data_Custodian() throws Throwable {
        WebElement usernameInput = driver.findElement(By.name("j_username"));
        usernameInput.clear();
        usernameInput.sendKeys(StepUtils.USERNAME);
        WebElement passwordInput = driver.findElement(By.name("j_password"));
        passwordInput.clear();
        passwordInput.sendKeys(StepUtils.PASSWORD);
        WebElement login = driver.findElement(By.name("submit"));
        login.click();
    }

    @Then("^I should see the Scope Selection page$")
    public void I_should_see_the_Scope_Selection_page() throws Throwable {
        assertTrue(driver.getPageSource().contains("Select Scope"));
    }

    public void login(String username, String password) {
        driver.get(StepUtils.BASE_URL + "/j_spring_security_logout");
        driver.get(StepUtils.BASE_URL + "/");
        WebElement loginLink = driver.findElement(By.id("login"));
        loginLink.click();
        WebElement usernameInput = driver.findElement(By.name("j_username"));
        usernameInput.clear();
        usernameInput.sendKeys(username);
        WebElement passwordInput = driver.findElement(By.name("j_password"));
        passwordInput.clear();
        passwordInput.sendKeys(password);
        WebElement login = driver.findElement(By.name("submit"));
        login.click();
    }
}
