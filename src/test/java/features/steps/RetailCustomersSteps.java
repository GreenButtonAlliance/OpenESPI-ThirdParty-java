/*
 * Copyright 2013 EnergyOS.org
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package features.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class RetailCustomersSteps {

    private WebDriver driver = WebDriverSingleton.getInstance();

    @Given("^I have a Retail Customer account$")
    public void I_have_a_Retail_Customer_account() throws Throwable {
    }

    @When("^I log in as Alan Turing$")
    public void I_log_in_as_Alan_Turing() throws Throwable {
        StepUtils.login("alan", "koala");
    }

    @Then("^I should be logged in$")
    public void I_should_be_logged_in() throws Throwable {
        assertTrue(driver.getPageSource().contains("logout"));
        assertFalse(driver.getPageSource().contains("login"));
    }

    @When("^I log in with invalid credentials$")
    public void I_log_in_with_invalid_credentials() throws Throwable {
        StepUtils.login("alan", "incorrect");
    }

    @Then("^I should see the login form$")
    public void I_should_see_the_login_form() throws Throwable {
        assertTrue(driver.getPageSource().contains("Login"));
    }

    @Then("^I should see Usage Point with title \"([^\"]*)\"$")
    public void I_should_see_my_Usage_Points_with_title(String title) throws Throwable {
        assertTrue(driver.getPageSource().contains(title));
    }
}
