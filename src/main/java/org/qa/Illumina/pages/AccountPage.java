package org.qa.Illumina.pages;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.qa.Illumina.base.BaseClass;

public class AccountPage extends BaseClass {
    public AccountPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    By account_New = By.xpath("//a[.='New']");
    By account_Name = By.xpath("//input[@name='Name']");
    By account_Phone = By.xpath("//input[@name='Phone']");
    By acount_Save = By.xpath("//button[@name='SaveEdit']");
    By newAccountText = By.xpath("//div//h2[text()='New Account']");

    public String create_Account(){
        String accountName= null;
        try {
        Faker faker = new Faker();
        accountName = faker.company().name();
        String accountPhone =   faker.phoneNumber().phoneNumber().toString();

        waitForPageLoad();
        forceWait(5);
      clickElement(account_New);
    if(isElementPresent(newAccountText)){
        System.out.println("Create New Account");

    }else {
        clickElement(account_New);
    }
       // driver.findElement(account_New).click();
        forceWait(5);
        waitForVisibilityOfField(account_Name, 3);
       // sendKeysUsingJS(account_Name, accountName);
        driver.findElement(account_Name).sendKeys(accountName);
        waitForVisibilityOfField(account_Phone, 3);
        driver.findElement(account_Phone).sendKeys(accountPhone);
        driver.findElement(acount_Save).click();
        waitUntillAllNetworkCallAreCompletedState();
    }
        catch (Exception e) {
            System.out.println("Account creation failed due to: " + e.getMessage());
            e.printStackTrace();
        }
        return accountName;
    }

    public void sendKeysUsingJS(By by, String keys) {
        WebElement element = driver.findElement(by);
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

        // Execute script
        jsExecutor.executeScript("arguments[0].value='" + keys +"';", element);
    }

    public void clickUsingJS(By by) {
        WebElement element = driver.findElement(by);
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

        // Execute script
        jsExecutor.executeScript("arguments[0].click();", element);
    }
}
