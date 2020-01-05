package cz.vse.selenium;

import cz.churchcrm.testframework.BrowserFactory;
import cz.churchcrm.testframework.ConfigFileReader;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import cz.churchcrm.testframework.LoginPage;
import org.openqa.selenium.support.PageFactory;

public class TestUserLogin {
    ConfigFileReader configFileReader;

 //   String appUrl = configFileReader.getApplicationUrl();
    /*

   Test Suite - User Login
 TC#1: User logs into system using valid username and password. DONE

 TC#2: User cannot log into system using valid username and invalid password. DONE

 TC#3: Logged user loggs off. DONE

     */

    @Test
    public void loginPositive() {
        WebDriver driver = BrowserFactory.startBrowser("chrome", "");
        LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
        loginPage.loginIntoPortal("rukovoditel","vse456ru");
    }

    @Test @Ignore
    public void loginNegative() {
        WebDriver driver = BrowserFactory.startBrowser("chrome","");
        LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
        loginPage.loginIntoPortal("rukovoditel","SpatneHeslo");
    }

    @Test @Ignore
    public void userLogout() {
        WebDriver driver = BrowserFactory.startBrowser("chrome","");
        LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
        loginPage.loginIntoPortal("rukovoditel","vse456ru");
        loginPage.logout();
    }
}
