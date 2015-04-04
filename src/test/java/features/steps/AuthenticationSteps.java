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

import static org.junit.Assert.assertTrue;

import org.energyos.espi.common.test.WebDriverSingleton;
import org.openqa.selenium.WebDriver;

import cucumber.api.java.en.Then;

public class AuthenticationSteps {
	private WebDriver driver = WebDriverSingleton.getInstance();

	@Then("^I should see Retail Customer home page$")
	public void I_should_see_Retail_Customer_home_page() throws Throwable {
		assertTrue(driver.getCurrentUrl().endsWith(
				"/ThirdParty/RetailCustomer/1/home"));
		assertTrue(driver.getPageSource().contains("Logout"));
	}
}
