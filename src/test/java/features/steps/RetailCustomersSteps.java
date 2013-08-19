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
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static org.junit.Assert.assertTrue;


public class RetailCustomersSteps {

    private WebDriver driver = new HtmlUnitDriver();

    @Given("^I have a Retail Customer account$")
    public void I_have_a_Retail_Customer_account() throws Throwable {
    }

    @When("^I log in as Alan Turing$")
    public void I_log_in_as_Alan_Turing() throws Throwable {
        login("alan", "koala");
    }

    @Then("^I should be logged in$")
    public void I_should_be_logged_in() throws Throwable {
        assertTrue(driver.getPageSource().contains("Logout"));
    }

    @When("^I log in with invalid credentials$")
    public void I_log_in_with_invalid_credentials() throws Throwable {
        login("alan", "incorrect");
    }

    private void login(String username, String password) {
        driver.get("http://localhost:9000/");
        WebElement loginLink = driver.findElement(By.id("login"));
        loginLink.click();
        WebElement usernameInput = driver.findElement(By.name("j_username"));
        usernameInput.sendKeys(username);
        WebElement passwordInput = driver.findElement(By.name("j_password"));
        passwordInput.sendKeys(password);
        WebElement login = driver.findElement(By.name("submit"));
        login.click();
    }

    @Then("^I should see the login form$")
    public void I_should_see_the_login_form() throws Throwable {
        assertTrue(driver.getPageSource().contains("Sign in"));
    }
}
