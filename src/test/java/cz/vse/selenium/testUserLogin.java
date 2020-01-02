package cz.vse.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class testUserLogin {
    /*

   Test Suite - User Login
 TC#1: User logs into system using valid username and password.

 TC#2: User cannot log into system using valid username and invalid password.

 TC#3: Logged user loggs off.

     */

    private ChromeDriver driver;

    @Before
    public void init() {
        ChromeOptions cho = new ChromeOptions();
        boolean runOnTravis = false;
        if (runOnTravis) {
            cho.addArguments("headless");
        } else {
            System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
        }
//      ChromeDriverService service = new ChromeDriverService();
        driver = new ChromeDriver(cho);
//      driver.manage().window().maximize();
    }

    @After
    public void tearDown() {
       // driver.close();
    }

    @Test @Ignore
    public void loginPositive() {
        // given
        driver.get("https://digitalnizena.cz/rukovoditel/");

        // when
        WebElement usernameInput = driver.findElement(By.name("username"));
        usernameInput.sendKeys("rukovoditel");
        WebElement passwordInput = driver.findElement(By.name("password"));
        passwordInput.sendKeys("vse456ru");
        WebElement loginButton = driver.findElement(By.xpath("//button[@type=\"submit\" and @class=\"btn btn-info pull-right\"]"));
        loginButton.click();
    }

    @Test @Ignore
    public void loginNegative() {
        // given
        driver.get("https://digitalnizena.cz/rukovoditel/");

        // when
        WebElement usernameInput = driver.findElement(By.name("username"));
        usernameInput.sendKeys("rukovoditel");
        WebElement passwordInput = driver.findElement(By.name("password"));
        passwordInput.sendKeys("spatneHeslo");
        WebElement loginButton = driver.findElement(By.xpath("//button[@type=\"submit\" and @class=\"btn btn-info pull-right\"]"));
        loginButton.click();
    }

    @Test
    public void userLogoutFrontEnd() {
        // given
        driver.get("https://digitalnizena.cz/rukovoditel/");

        // when
        WebElement usernameInput = driver.findElement(By.name("username"));
        usernameInput.sendKeys("rukovoditel");
        WebElement passwordInput = driver.findElement(By.name("password"));
        passwordInput.sendKeys("vse456ru");

        WebElement loginButton = driver.findElement(By.xpath("//button[@type=\"submit\" and @class=\"btn btn-info pull-right\"]"));
        loginButton.click();

        WebElement userAccount = driver.findElement(By.xpath("//span[@class=\"username\"]"));
        userAccount.click();

        WebElement userLogout = driver.findElement(By.xpath("//i[@class=\"fa fa-sign-out\"]"));
        userLogout.click();

    }

    @Test
    public void userLogoutLink(){

        driver.get("https://digitalnizena.cz/rukovoditel/");

        // when
        WebElement usernameInput = driver.findElement(By.name("username"));
        usernameInput.sendKeys("rukovoditel");
        WebElement passwordInput = driver.findElement(By.name("password"));
        passwordInput.sendKeys("vse456ru");


        driver.get("https://digitalnizena.cz/rukovoditel/index.php?module=users/login&action=logoff");

    }

}
