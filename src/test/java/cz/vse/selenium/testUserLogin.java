package cz.vse.selenium;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import cz.churchcrm.testframework.LoginPage;
import org.openqa.selenium.support.PageFactory;

public class testUserLogin {
    /*

   Test Suite - User Login
 TC#1: User logs into system using valid username and password. DONE

 TC#2: User cannot log into system using valid username and invalid password. DONE

 TC#3: Logged user loggs off. DONE

     */

    @Test
    public void loginPositive() {
        WebDriver driver = BrowserFactory.startBrowser("chrome","https://digitalnizena.cz/rukovoditel/");
        LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
        loginPage.loginIntoPortal("rukovoditel","vse456ru");
    }

    @Test
    public void loginNegative() {
        WebDriver driver = BrowserFactory.startBrowser("chrome","https://digitalnizena.cz/rukovoditel/");
        LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
        loginPage.loginIntoPortal("rukovoditel","SpatneHeslo");
    }

    @Test
    public void userLogout() {
        WebDriver driver = BrowserFactory.startBrowser("chrome","https://digitalnizena.cz/rukovoditel/");
        LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
        loginPage.loginIntoPortal("rukovoditel","vse456ru");
        loginPage.logout();
    }
}
