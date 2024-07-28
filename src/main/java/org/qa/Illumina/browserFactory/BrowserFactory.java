package org.qa.Illumina.browserFactory;
import java.net.MalformedURLException;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;


public class BrowserFactory {

    static WebDriver driver;

    public static WebDriver getBrowserInstance()
    {
        return driver;
    }


    public static WebDriver startBrowser(String browserName,String applicationURL) throws MalformedURLException {


        if(browserName.contains("Chrome") || browserName.contains("GC") || browserName.contains("Google Chrome"))
        {
            ChromeOptions opt=new ChromeOptions();
            driver=new ChromeDriver(opt);
        }
        else if(browserName.contains("Firefox"))
        {
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            driver=new FirefoxDriver();
        }
        else if(browserName.contains("Safari"))
        {
            driver=new SafariDriver();
        }
        else if(browserName.contains("Edge"))
        {
            driver=new EdgeDriver();
        }
        else {
            driver=new ChromeDriver();
        }

        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

        return driver;
       }

}