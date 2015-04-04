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

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.energyos.espi.common.test.BaseStepUtils;
import org.energyos.espi.common.test.CucumberSession;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class StepUtils extends BaseStepUtils {
	public static void login(String username, String password) {
		driver.get(THIRD_PARTY_BASE_URL + "/j_spring_security_logout");
		driver.get(THIRD_PARTY_BASE_URL + "/");
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

	public static void registerUser(String username, String firstName,
			String lastName, String password) {
		StepUtils.login("grace", StepUtils.PASSWORD);

		clickLinkByText("Customer List");
		clickLinkByPartialText("Add new customer");

		assertTrue(driver.getPageSource().contains("New Retail Customer"));

		WebElement form = driver.findElement(By.name("new_customer"));

		WebElement usernameField = form.findElement(By.name("username"));
		usernameField.sendKeys(username);

		WebElement firstNameField = form.findElement(By.name("firstName"));
		firstNameField.sendKeys(firstName);

		WebElement lastNameField = form.findElement(By.name("lastName"));
		lastNameField.sendKeys(lastName);

		WebElement passwordField = form.findElement(By.name("password"));
		passwordField.sendKeys(password);

		WebElement create = form.findElement(By.name("create"));
		create.click();

		assertTrue(driver.getPageSource().contains("Retail Customers"));

		WebElement retailCustomerLink = driver.findElement(By
				.linkText(username));
		String href = retailCustomerLink.getAttribute("href");
		Pattern pattern = Pattern.compile("retailcustomers/(\\d+)");
		Matcher matcher = pattern.matcher(href);
		matcher.find();
		String hashedId = matcher.group(1);
		assertNotNull(hashedId);
		CucumberSession.setUserHashedId(hashedId);
	}

	public static void navigateTo(String url) {
		driver.get(THIRD_PARTY_BASE_URL + url);
	}
}
