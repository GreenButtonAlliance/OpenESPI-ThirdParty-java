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
import features.WildcardListener;
import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.custommonkey.xmlunit.XMLAssert.assertXpathExists;
import static org.junit.Assert.assertTrue;


public class RetailCustomerDataSteps {

    private WebDriver driver = WebDriverSingleton.getInstance();

    @Given("^a logged in retail customer$")
    public void a_logged_in_retail_customer() throws Throwable {
        StepUtils.login("alan", "koala");
    }

    @When("^I look at my usage page$")
    public void I_look_at_my_usage_page() throws Throwable {
        WebElement usagePointLink = driver.findElement(By.linkText("Usage Points"));
        usagePointLink.click();
    }

    @Then("^I should be able to download Usage Points in XML format$")
    public void I_should_be_able_to_download_Usage_Points_in_XML_format() throws Throwable {
        WebElement downloadLink = driver.findElement(By.partialLinkText("Download XML"));
        downloadLink.click();

        String expected =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                        "<feed xmlns=\"http://www.w3.org/2005/Atom\">" +
                        "<title>UsagePoint Feed</title>" +
                        "<id>*</id>" +
                        "<entry>" +
                        "<title>House meter</title>" +
                        "<link rel=\"self\" href=\"RetailCustomer/6/UsagePoint/4\" />" +
                        "<link rel=\"up\" href=\"RetailCustomer/6/UsagePoint\" />" +
                        "<id>4</id>" +
                        "<updated>*</updated>" +
                        "<published>*</published>" +
                        "<content>" +
                        "<UsagePoint xmlns=\"http://naesb.org/espi\">" +
                        "<ServiceCategory>" +
                        "<kind>1</kind>" +
                        "</ServiceCategory>" +
                        "</UsagePoint>" +
                        "</content>" +
                        "</entry>" +
                        "<entry>" +
                        "<title>Gas meter</title>" +
                        "<link rel=\"self\" href=\"RetailCustomer/6/UsagePoint/5\" />" +
                        "<link rel=\"up\" href=\"RetailCustomer/6/UsagePoint\" />" +
                        "<id>5</id>" +
                        "<updated>*</updated>" +
                        "<published>*</published>" +
                        "<content>" +
                        "<UsagePoint xmlns=\"http://naesb.org/espi\">" +
                        "<ServiceCategory>" +
                        "<kind>1</kind>" +
                        "</ServiceCategory>" +
                        "</UsagePoint>" +
                        "</content>" +
                        "</entry>" +
                        "</feed>";

        assertXmlMatches(expected, driver.getPageSource());


//        String xmlResult = driver.getPageSource();
//        assertXpathExists("feed/entry/title", xmlResult);



    }

    private void assertXmlMatches(String expected, String result) throws Throwable {
        XMLUnit.setIgnoreWhitespace(Boolean.TRUE);
        DetailedDiff diffXml = new DetailedDiff(new Diff(expected, result));
        diffXml.overrideDifferenceListener(new WildcardListener());

        String flatResult = result.replace("\n", "").replaceAll("\\s+<", "<").replaceAll(">\\s+", ">");
        String flatExpected = expected.replace("\n", "").replaceAll("\\s+<", "<").replaceAll(">\\s+", ">");
        assertTrue("\n\n\nXMLUnit ERROR:\n" + diffXml.toString() + ":\n\nEXPECTED:\n[" + flatExpected + "]\n\nGOT:\n[" + flatResult + "]\n\n\n", diffXml.similar());
    }
}
