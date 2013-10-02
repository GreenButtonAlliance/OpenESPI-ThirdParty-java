package features.steps;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertTrue;

public class OAuthSteps extends BaseSteps {

    @Before
    public void before() {
        driver.get(StepUtils.DATA_CUSTODIAN_BASE_URL + "/j_spring_security_logout");
        driver.get(StepUtils.THIRD_PARTY_BASE_URL + "/j_spring_security_logout");
    }

    @Given("^a logged in Retail Customer$")
    public void a_logged_in_Retail_Customer() throws Throwable {
        StepUtils.login(StepUtils.USERNAME, StepUtils.PASSWORD);
    }

    @When("^I navigate to Data Custodian list$")
    public void I_navigate_to_Data_Custodian_list() throws Throwable {
        clickLink("Data Custodians");
    }

    @Then("^I should see a list of Data Custodians$")
    public void I_should_see_a_list_of_Data_Custodians() throws Throwable {
        assertContains("ConEdison");
    }

    @When("^I select a Data Custodian from the list$")
    public void I_select_a_Data_Custodian_from_the_list() throws Throwable {
        clickLink("Data Custodians");
        clickByClass("data-custodian");
        clickByName("next");
    }

    @Then("^I should see the Data Custodian login screen$")
    public void I_should_see_the_Data_Custodian_login_screen() throws Throwable {
        assertTrue(driver.getCurrentUrl().contains("/DataCustodian"));
        assertContains("Login");
    }

    @When("^I log into the Data Custodian$")
    public void I_log_into_the_Data_Custodian() throws Throwable {
        fillInByName("j_username", StepUtils.USERNAME);
        fillInByName("j_password", StepUtils.PASSWORD);
        clickByName("submit");
    }

    @Then("^I should see the Scope Selection page$")
    public void I_should_see_the_Scope_Selection_page() throws Throwable {
        assertContains("Select Scope");
    }

    @When("^I log into Data Custodian$")
    public void I_log_into_Data_Custodian() throws Throwable {
        clickById("login");
        fillInByName("j_username", StepUtils.USERNAME);
        fillInByName("j_password", StepUtils.PASSWORD);
        clickByName("submit");
    }

    @Then("^I should see Scope selection screen$")
    public void I_should_see_Scope_selection_screen() throws Throwable {
        assertContains("Select Scope");
        assertContains("FB_4_5_15_IntervalDuration_3600_BlockDuration_monthly_HistoryLength_13");
    }

    @When("^I select Scopes$")
    public void I_select_Scope() throws Throwable {
        clickByClass("scope");
        clickByName("next");
    }

    @Then("^I should see authorization screen$")
    public void I_should_see_authorization_screen() throws Throwable {
        assertContains("Please Confirm");
    }

    @When("^I authorize Third Party$")
    public void I_authorize_Third_Party() throws Throwable {
        clickByName("authorize");
    }

    @Then("^I should see all my authorizations$")
    public void I_should_see_all_my_authorizations() throws Throwable {
        assertContains("Authorizations");
        assertContains("ConEdison");
    }
}
