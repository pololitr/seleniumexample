package cz.churchcrm.testframework;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage {
    protected WebDriver driver;

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


    public void loginIntoPortal(String username, String password){
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        loginButton.click();

    }
    public void logout(){
        userAccount.click();
        userLogout.click();
    }

}
