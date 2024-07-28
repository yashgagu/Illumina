package testCases;

import org.qa.Illumina.base.BaseClass;
import org.qa.Illumina.pages.AccountPage;
import org.qa.Illumina.pages.ContactPage;
import org.qa.Illumina.pages.LoginPage;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.Test;

public class LoginAndCreteAccount extends BaseClass {

    @Test()
    public void LoginTest() throws Exception {
        LoginPage login = new LoginPage(driver);
        AccountPage accout = new AccountPage(driver);
        ContactPage contact = new ContactPage(driver);
        login.loginToSFDC(LoginPage.SFDC_USER_NAME , LoginPage.SFDC_USER_PASSWORD,LoginPage.url);

        login.switchApp("Accounts");
        login.search("yashkarn singh");



        login.switchApp("Accounts");

        String account_name = accout.create_Account();


        login.switchApp("Contacts");

        contact.createContact(account_name);
        contact.logout();








    }

}
