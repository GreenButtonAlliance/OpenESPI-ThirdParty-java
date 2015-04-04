/*
 * Copyright 2013, 2014, 2015 EnergyOS.org
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

import static features.steps.StepUtils.navigateTo;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.energyos.espi.common.test.WebDriverSingleton;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class RetailCustomersSteps {

	private WebDriver driver = WebDriverSingleton.getInstance();

	@Given("^I have a Retail Customer account$")
	public void I_have_a_Retail_Customer_account() throws Throwable {
	}

	@When("^I log in as Alan Turing$")
	public void I_log_in_as_Alan_Turing() throws Throwable {
		StepUtils.login(StepUtils.USERNAME, StepUtils.PASSWORD);
	}

	@Then("^I should be logged in$")
	public void I_should_be_logged_in() throws Throwable {
		assertTrue(driver.getPageSource().contains("logout"));
		assertFalse(driver.getPageSource().contains("login"));
	}

	@When("^I log in with invalid credentials$")
	public void I_log_in_with_invalid_credentials() throws Throwable {
		StepUtils.login(StepUtils.USERNAME, "incorrect");
	}

	@Then("^I should see the login form$")
	public void I_should_see_the_login_form() throws Throwable {
		assertTrue(driver.getPageSource().contains("Login"));
	}

	@Then("^I should see Usage Point with title \"([^\"]*)\"$")
	public void I_should_see_my_Usage_Points_with_title(String title)
			throws Throwable {
		assertTrue(driver.getPageSource().contains(title));
	}

	@When("^I look at my Usage Points page$")
	public void I_look_at_my_usage_page() throws Throwable {
		WebElement usagePointLink = driver.findElement(By
				.linkText("Usage Points"));
		usagePointLink.click();
	}

	@When("^I select Usage Point$")
	public void I_select_Usage_Point() throws Throwable {
		WebElement usagePointLink = driver.findElement(By
				.linkText("Front Electric Meter"));
		usagePointLink.click();
	}

	@When("^I select Meter Reading$")
	public void I_select_Meter_Reading() throws Throwable {
		WebElement usagePointLink = driver.findElement(By
				.linkText("Fifteen Minute Electricity Consumption"));
		usagePointLink.click();
	}

	@Then("^I should see Meter Reading$")
	public void I_should_see_Meter_Reading() throws Throwable {
		assertTrue("MeterReading title missing", driver.getPageSource()
				.contains("Fifteen Minute Electricity Consumption"));
	}

	@Then("^I should see Reading Type$")
	public void I_should_see_Reading_Type() throws Throwable {
		assertTrue("ReadingType title missing", driver.getPageSource()
				.contains("Energy Delivered (kWh)"));
	}

	@Then("^I should see Interval Blocks$")
	public void I_should_see_Interval_Blocks() throws Throwable {
		assertTrue("Interval blocks missing",
				driver.getPageSource().contains("86400"));
	}

	@Then("^I should see Electric Power Usage Summary$")
	public void I_should_see_Electric_Power_Usage_Summary() throws Throwable {
		assertTrue(driver.getPageSource().contains("Usage Summary"));
	}

	@Then("^I should see Interval Readings$")
	public void I_should_see_Interval_Readings() throws Throwable {
		assertTrue(driver.getPageSource().contains("974"));
		assertTrue(driver.getPageSource().contains("900"));
		assertTrue(driver.getPageSource().contains("965"));
	}

	@Then("^I should see Reading Qualities$")
	public void I_should_see_Reading_Qualities() throws Throwable {
		WebElement element = driver.findElement(By
				.cssSelector(".reading-qualities"));

		assertThat(element.getText(), containsString("8"));
	}

	@Then("^I should see Electric Power Quality Summary$")
	public void I_should_see_Electric_Power_Quality_Summary() throws Throwable {
		assertTrue(driver.getPageSource().contains("Quality Summary"));
	}

	@When("^I attempt to view custodian/home$")
	public void I_attempt_to_view_custodian_home() throws Throwable {
		navigateTo("/custodian/home");
	}

	@Then("^I should see an unauthorized screen$")
	public void I_should_see_an_unauthorized_screen() throws Throwable {
		assertThat(driver.getPageSource(),
				containsString("You don't have permission to view this page"));
	}
}
