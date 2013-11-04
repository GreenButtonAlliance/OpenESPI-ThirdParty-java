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

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class StepUtils {

    public final static String DATA_CUSTODIAN_BASE_URL = "http://localhost:8080/DataCustodian";
    public final static String THIRD_PARTY_BASE_URL = "http://localhost:8080/ThirdParty";
    public final static String USERNAME = "alan";
    public final static String PASSWORD = "koala";

    private static WebDriver driver = WebDriverSingleton.getInstance();

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

    static void openPage() throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI(driver.getCurrentUrl()));
    }

    static void saveAndOpenPage() throws IOException, URISyntaxException {
        File file = new File("/tmp/cucumber.html");

        if (!file.exists()) {
            file.createNewFile();
        }

        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(driver.getPageSource());
        bw.close();

        Desktop.getDesktop().browse(new URI("file:///tmp/cucumber.html"));
    }
}
