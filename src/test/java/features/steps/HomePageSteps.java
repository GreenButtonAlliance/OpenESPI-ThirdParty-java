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
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static org.junit.Assert.assertTrue;


public class HomePageSteps {

    private WebDriver driver = new HtmlUnitDriver();

    @Given("^I am a Retail Customer$")
    public void I_am_a_Retail_Customer() throws Throwable {
    }

    @When("^I visit the home page$")
    public void I_visit_the_home_page() throws Throwable {
        driver.get("http://localhost:9000/");
    }

    @Then("^I should see a welcome message$")
    public void I_should_see_a_welcome_message() throws Throwable {
        assertTrue(driver.getPageSource().contains("Welcome"));
    }
}
