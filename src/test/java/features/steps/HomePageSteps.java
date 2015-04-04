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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.energyos.espi.common.test.WebDriverSingleton;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class HomePageSteps {

	private WebDriver driver = WebDriverSingleton.getInstance();

	@Given("^I am a Retail Customer$")
	public void I_am_a_Retail_Customer() throws Throwable {
	}

	@When("^I visit the home page$")
	public void I_visit_the_home_page() throws Throwable {
		driver.get(StepUtils.THIRD_PARTY_BASE_URL);
	}

	@Then("^I should see a welcome message$")
	public void I_should_see_a_welcome_message() throws Throwable {
		assertTrue(driver.getPageSource().contains("Welcome"));
	}

	@When("^I visit the Terms of Service page$")
	public void I_visit_the_Terms_of_Service_page() throws Throwable {
		driver.get(StepUtils.THIRD_PARTY_BASE_URL);
		driver.findElement(By.linkText("Terms of Service")).click();
	}

	@Then("^I should see the Terms of Service$")
	public void I_should_see_the_Terms_of_Service() throws Throwable {
		assertTrue(driver.getCurrentUrl().endsWith("/TermsOfService"));
		assertEquals("Terms of Service",
				driver.findElement(By.cssSelector(".row h2")).getText());
	}

	@When("^I visit the Usage Policy page$")
	public void I_visit_the_Usage_Policy_page() throws Throwable {
		driver.get(StepUtils.THIRD_PARTY_BASE_URL);
		driver.findElement(By.linkText("Usage Policy")).click();
	}

	@Then("^I should see the Usage Policy$")
	public void I_should_see_the_Usage_Policy() throws Throwable {
		assertTrue(driver.getCurrentUrl().endsWith("/UsagePolicy"));
		assertEquals("Usage Policy",
				driver.findElement(By.cssSelector(".row h2")).getText());
	}
}
