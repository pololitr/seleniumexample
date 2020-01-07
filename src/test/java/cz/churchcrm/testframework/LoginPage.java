package cz.churchcrm.testframework;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    protected WebDriver driver;
    static ConfigFileReader configFileReader;

    public LoginPage(WebDriver localDriver){
        this.driver = localDriver;
    }
    @FindBy(name="username")
    WebElement usernameInput;

    @FindBy(name="password")
    WebElement passwordInput;

    @FindBy(xpath="//button[@type=\"submit\" and @class=\"btn btn-info pull-right\"]")
    WebElement loginButton;

    @FindBy(xpath="//span[@class=\"username\"]")
    WebElement userAccount;

    @FindBy(xpath="//i[@class=\"fa fa-sign-out\"]")
    WebElement userLogout;

    String welcomePageTitle="//h3[@class=\"page-title\"]";

    String noMatchPswdUsrnm = "//div[@class=\"alert alert-danger\"]";

    String loginPageTitle =  "//h3[@class=\"form-title\"]";


    public void loginIntoPortal(String username, String password){
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        loginButton.click();
    }

    public void logout(){
        userAccount.click();
        userLogout.click();
        verifySuccessfulLogout();
    }

    public void verifySuccessfulLogin(){
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(welcomePageTitle)));
        WebElement welcomePageTitleElement = driver.findElement(By.xpath(welcomePageTitle));
        //Assert.assertEquals(driver.getCurrentUrl(), configFileReader.getApplicationUrl());
        Assert.assertEquals("Welcome to the Rukovoditel – your new assistant in business management!", welcomePageTitleElement.getText());
    }

    public void verifyUnsuccessfulLogin(){
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(noMatchPswdUsrnm)));
        WebElement noMatchPswdUsrnmElement = driver.findElement(By.xpath(noMatchPswdUsrnm));
        //Assert.assertEquals(driver.getCurrentUrl(), configFileReader.getApplicationUrl());
        Assert.assertEquals("×\n"+"No match for Username and/or Password.", noMatchPswdUsrnmElement.getText());
    }

    public void verifySuccessfulLogout(){
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(loginPageTitle)));
        WebElement loginPageTitleElement = driver.findElement(By.xpath(loginPageTitle));
        //Assert.assertEquals(driver.getCurrentUrl(), configFileReader.getApplicationUrl());
        Assert.assertEquals("Login", loginPageTitleElement.getText());
    }
}
