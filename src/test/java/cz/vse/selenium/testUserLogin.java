package cz.vse.selenium;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import cz.churchcrm.testframework.loginPage;
import org.openqa.selenium.support.PageFactory;

public class testUserLogin {
    /*

   Test Suite - User Login
 TC#1: User logs into system using valid username and password.

 TC#2: User cannot log into system using valid username and invalid password.

 TC#3: Logged user loggs off.

     */

    @Test
    public void loginPositive() {
        WebDriver driver = browserFactory.startBrowser("chrome","https://digitalnizena.cz/rukovoditel/");
        loginPage loginPage = PageFactory.initElements(driver, loginPage.class);
        loginPage.loginIntoPortal("rukovoditel","vse456ru");
    }

    @Test
    public void loginNegative() {
        WebDriver driver = browserFactory.startBrowser("chrome","https://digitalnizena.cz/rukovoditel/");
        loginPage loginPage = PageFactory.initElements(driver, loginPage.class);
        loginPage.loginIntoPortal("rukovoditel","SpatneHeslo");
    }

    @Test
    public void userLogoutFrontEnd() {
        WebDriver driver = browserFactory.startBrowser("chrome","https://digitalnizena.cz/rukovoditel/");
        loginPage loginPage = PageFactory.initElements(driver, loginPage.class);
        loginPage.loginIntoPortal("rukovoditel","vse456ru");
        loginPage.logout();
    }
}
