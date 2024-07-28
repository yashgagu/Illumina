package org.qa.Illumina.pages;

import com.github.javafaker.Cat;
import com.github.javafaker.Faker;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.qa.Illumina.base.BaseClass;

import java.time.Duration;

public class LoginPage extends BaseClass {

    public static String url = "https://yashkarn-dev-ed.develop.my.salesforce.com";
    public static String SFDC_USER_NAME = "yashkarn@aol.com.test";
    public static String SFDC_USER_PASSWORD = "Iambest@008";

    By SF_user = By.xpath("//input[@id='username']");
    By SF_Password = By.xpath("//input[@id='password']");
    By SF_login = By.xpath("//input[@id='Login']");
    By appname = By.xpath("//*[contains(@class,'appName')]");
    By app_lanucher = By.xpath("//button//div[@class='slds-icon-waffle']");
    By app_Launcher_Search = By.xpath("//input[@placeholder='Search apps and items...']");

    By account_New = By.xpath("//a[.='New']");
    By account_Name = By.xpath("//input[@name='Name']");
    By account_Phone = By.xpath("//input[@name='Phone']");
    By acount_Save = By.xpath("//button[@name='SaveEdit']");
By searchBox = By.xpath("//button[contains(text(),'Search..')]");
By searchBox_Click = By.xpath("//lightning-input//input[@placeholder='Search...']");

By searchResult = By.xpath("//div[contains(@class,'resultsItem slds-col slds-no-flex slds-var-m-bottom_small')] ");
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void loginToSFDC(String userName, String password, String url){
        try {
        launchURL(url);
        waitUntillAllNetworkCallAreCompletedState();
        driver.findElement(SF_user).sendKeys(userName);
        driver.findElement(SF_Password).sendKeys(password);
        driver.findElement(SF_login).submit();}
        catch (Exception e){
            System.out.println("Login failed due to incorrect login details: " + e.getMessage());
            e.printStackTrace();
        }

    }

    private final void launchURL(String url) {
        try {
            driver.navigate().to(url);
            System.out.print("-----------------------------" + "\n URL :\t" + driver.getCurrentUrl()
                    + "\n Page Title :\t" + driver.getTitle() + "\n-----------------------------");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("Unable to launch the SFDC application with URL - " + url);
        }
    }



    public void switchApp(String app) throws Exception {
     try{
     //   launchSFDCURL(url);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));

        String applicationNameFromUI = driver.findElement(appname).getText();
        System.out.println("Application: " + applicationNameFromUI);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));
        driver.findElement(app_lanucher).click();

        if (app.equalsIgnoreCase(applicationNameFromUI)) {
            System.out.println("Application : " + applicationNameFromUI);
        }

        else {
            for (int i = 0; i < 10; i++) {
                System.out.println("i: " + i);
                driver.findElement(app_lanucher).click();
                Thread.sleep(5000);
                if (isElementPresent(app_Launcher_Search))
                    i = i + 10;
            }
            driver.findElement(app_Launcher_Search).sendKeys(app);
            Thread.sleep(5000);

            String appXpath = "//b[contains(text(),'" + app + "')]";
            driver.findElement(By.xpath("//b[contains(text(),'" + app + "')]")).click();
            waitUntillAllNetworkCallAreCompletedState();}


        }catch(Exception e){
         System.out.print("Switch App Failed Due To Incorrect app Name "+ e.getMessage());
         e.printStackTrace();

     }
    }

    public boolean search(String searchTerm) throws InterruptedException {
        Boolean isResultFound = false;
        waitForPageLoad();
        Thread.sleep(6000);
    waitForVisibilityOfField(searchBox,10);
       // driver.findElement(searchBox).click();
        clickElement(searchBox);
        if (isElementPresent(searchBox_Click)){
            System.out.println("Insert The Search Term");
        }else {
            clickElement(searchBox);
        }
        driver.findElement(searchBox_Click).sendKeys(searchTerm);
        Thread.sleep(6000);
        try {
            driver.findElement(searchBox_Click).sendKeys(Keys.RETURN);

        } catch (Exception e) {
            System.out.print("Failed to click enter");
            e.printStackTrace();
        }

String result = driver.findElement(searchResult).toString();
        if(driver.findElement(searchResult).isDisplayed()){
            isResultFound = true;
        }
return isResultFound;
    }

    public String create_Account(){
        Faker faker = new Faker();
        String accountName = faker.company().name();
        String accountPhone =   faker.phoneNumber().phoneNumber().toString();
        driver.findElement(account_New).click();;
        waitForPageLoad();
        driver.findElement(account_Name).sendKeys(accountName);
        driver.findElement(account_Phone).sendKeys(accountPhone);
        driver.findElement(acount_Save).click();
        return accountName;
    }

    private  void launchSFDCURL(String url) {
        try {
            driver.navigate().to(url);
            System.out.print("-----------------------------" + "\n URL :\t" + driver.getCurrentUrl()
                    + "\n Page Title :\t" + driver.getTitle() + "\n-----------------------------");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("Unable to launch the SFDC application with URL - " + url);
        }
    }

}
