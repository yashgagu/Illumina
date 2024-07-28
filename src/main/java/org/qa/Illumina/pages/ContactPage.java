package org.qa.Illumina.pages;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.qa.Illumina.base.BaseClass;
import org.testng.Assert;

import java.time.Duration;

public class ContactPage extends BaseClass {
    public ContactPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    By contact_New = By.xpath("//button[@class='slds-button slds-button_neutral slds-button_first'][normalize-space()='New']");
    By contact_phone = By.xpath("//input[@name='Phone']");
    By contact_firstName = By.xpath("//input[@name='firstName']");
    By contact_lastName = By.xpath("//input[@name='lastName']");
    By contact_account = By.xpath("//input[@placeholder='Search Accounts...']");
    By contact_save = By.xpath("//button[@name='SaveEdit']");
    By newcontact_text = By.xpath("//div//h2[text()='New Contact']");
    By detailsTab = By.xpath("//li[@data-label='Details']//a[1]");
    By accountName_Veriy = By.xpath("//*[text()='Account Name']/../../following-sibling::dd//div//a//span//span");
    By profile = By.xpath("//div[@class='profileTrigger branding-user-profile bgimg slds-avatar slds-avatar_profile-image-small circular forceEntityIcon']//span[@class='uiImage']");
    By logout = By.xpath("//a[contains(text(),'Log Out')]");





    public void createContact(String accountname) throws InterruptedException {
        try {
            Thread.sleep(5000);
            waitUntillAllNetworkCallAreCompletedState();

            Faker faker = new Faker();
            String Fname = faker.name().firstName();
            String LName = faker.name().name();
            String phone = faker.phoneNumber().phoneNumber().toString();
            String contactname = Fname + " " + LName;
            waitForVisibilityOfField(contact_New, 3000);
            driver.findElement(contact_New).click();
            if (isElementPresent(newcontact_text)) {
                System.out.println("Create New Contact On " + accountname);
            } else {
                driver.findElement(contact_New).click();
            }
            waitUntillAllNetworkCallAreCompletedState();
            driver.findElement(contact_firstName).sendKeys(Fname);
            driver.findElement(contact_lastName).sendKeys(LName);
            driver.findElement(contact_phone).sendKeys(phone);
            driver.findElement(contact_account).sendKeys(accountname);
            By accountxptah = By.xpath("//span//lightning-base-combobox-formatted-text[@title='" + accountname + "']");
            waitForVisibilityOfField(accountxptah, 10);
            driver.findElement(accountxptah).click();
            driver.findElement(contact_save).click();
            clickElementWithMultipleXpaths("(//li[@data-label='Details'])[2]", "(//li[@data-label='Details'])", "(//li[@data-label='Details'])[1]");
            //  waitForVisibilityOfField(detailsTab,10);
            // driver.findElement(detailsTab).click();
            waitForVisibilityOfField(accountName_Veriy, 10);
            Assert.assertTrue(isElementPresent(accountName_Veriy));
            forceWait(5);
        }catch (Exception e){
            System.out.println("Contact Creation failed Due to : "+ e.getMessage());
            e.printStackTrace();
        }

    }
    public void clickElementWithMultipleXpaths(String xpath1, String xpath2, String xpath3) {
        String[] xpaths = {xpath1, xpath2, xpath3};
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
        for (String xpath : xpaths) {
            try {
                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
                element.click();
                System.out.println("Element clicked successfully using XPath: " + xpath);
                return; // Exit the method after successful click
            } catch (NoSuchElementException e) {
                System.out.println("Element not found using XPath: " + xpath);
            } catch (Exception e) {
                System.out.println("An error occurred with XPath: " + xpath);
                e.printStackTrace();
            }
        }

        System.out.println("Element not found using any of the provided XPaths.");
    }

    public void logout() {
        try {
            clickElement(profile);
            if (isElementPresent(logout)) {
                System.out.println("Click on Logout ");
            } else {
                clickElement(profile);
            }
            forceWait(5);
        }catch (Exception e){
            System.out.println("User Unable to Logout : Due to  : "+e.getMessage());
            e.printStackTrace();
        }
    }
}



