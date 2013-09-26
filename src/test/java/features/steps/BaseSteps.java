package features.steps;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertTrue;

public class BaseSteps {
    protected WebDriver driver = WebDriverSingleton.getInstance();

    protected void clickLink(String linkText) {
        WebElement link = driver.findElement(By.linkText(linkText));
        link.click();
    }

    protected void clickByClass(String className) {
        WebElement radio = driver.findElement(By.className(className));
        radio.click();
    }

    protected void clickByName(String name) {
        WebElement submit = driver.findElement(By.name(name));
        submit.click();
    }

    protected void fillInByName(String name, String text) {
        WebElement usernameInput = driver.findElement(By.name(name));
        usernameInput.clear();
        usernameInput.sendKeys(text);
    }

    protected void clickById(String id) {
        WebElement loginLink = driver.findElement(By.id(id));
        loginLink.click();
    }

    protected void assertContains(String text) {
        assertTrue(driver.getPageSource().contains(text));
    }
}
