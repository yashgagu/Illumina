package org.qa.Illumina.base;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.qa.Illumina.browserFactory.BrowserFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class BaseClass {

    protected WebDriver driver;
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

    @Parameters({ "browser", "url" })
     @BeforeMethod
    public WebDriver setupBrowser(String browser, String url) throws MalformedURLException {

        System.out.println("Browser Name is::"+browser);

        driver= BrowserFactory.startBrowser(browser,url);


        System.out.println("LOG:INFO - Application is up and running");
        return driver;

    }



    @AfterMethod
    public void closeBrowser()
    {
        driver.quit();

        System.out.println("LOG:INFO - Closing the browser and application");
    }


    protected  void waitForPageLoad(){
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
    }

    protected void scrollDown(){
        Actions a = new Actions(driver);
        //scroll down a page
       a.sendKeys(Keys.PAGE_DOWN).build().perform();
    }

    protected void waitForElementToBeVisible(By element){
       wait.until(ExpectedConditions.presenceOfElementLocated(element));

    }

    public boolean waitForVisibilityOfField(By  element, int timeoutInSeconds) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        WebElement Element = wait.until(ExpectedConditions.visibilityOfElementLocated(element));
        if (!Element.isDisplayed()) {
            return false;
        }
        return true;
    }

    public void waitUntillAllNetworkCallAreCompletedState() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(3));
        try {
            String script = "return document.readyState === 'complete'";
            System.out.println("READY STATE STATUS: " + (boolean) wait.until(ExpectedConditions.jsReturnsValue(script)));

            script = "return window.jQuery ? (jQuery.active === 0) : true";
            System.out.println("AJAX REQUEST1 STATUS : " + (boolean) wait.until(ExpectedConditions.jsReturnsValue(script)));

        } catch(TimeoutException r) {
            System.out.print("Issue while page loading " + r.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Web driver: " + driver + " cannot execute javascript");
        }
    }

    public String getText(By element){
    return driver.findElement(element).getText();
    }

    public boolean isElementPresent(By by) {
        try {
            WebElement element = driver.findElement(by);
            return element != null;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void clickElement(By elementValue) {


        try {
            // Check if element is present
            WebElement element = driver.findElement(elementValue);
            element.click();
            System.out.println("Element clicked successfully.");
        } catch (NoSuchElementException e) {
            System.out.println("Element not present, waiting for it to appear.");

            // Wait for the element to be present and clickable, then click it
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(elementValue));
            element.click();
            System.out.println("Element clicked after waiting.");
        }
    }

    public void forceWait(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}




