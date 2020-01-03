package cz.vse.selenium;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.util.UUID;


public class testCreateProject {
    UUID uuid = UUID.randomUUID();
    String randomUUIDString = uuid.toString();
    /*
    Test Suite - Creating project

   TC#2: Project with status New, priority High and filled start date as today is created.
         Verify that there is new row in project table.
         Delete project after test.

   TC#1: Project without name is not created


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

    @Test
    public void createProjectPositive() {
        // given
        driver.get("https://digitalnizena.cz/rukovoditel/");

        // when
        WebElement usernameInput = driver.findElement(By.name("username"));
        usernameInput.sendKeys("rukovoditel");
        WebElement passwordInput = driver.findElement(By.name("password"));
        passwordInput.sendKeys("vse456ru");
        WebElement loginButton = driver.findElement(By.xpath("//button[@type=\"submit\" and @class=\"btn btn-info pull-right\"]"));
        loginButton.click();

        //go to projects
        WebElement sidebarProjects = driver.findElement(By.xpath("//i[@class=\"fa fa-reorder\"]"));
        sidebarProjects.click();

       //add project
        WebElement addProject = driver.findElement(By.xpath("//button[@class=\"btn btn-primary\" and @type=\"button\"]"));
        addProject.click();

        //Project priority
        WebDriverWait wait1 = new WebDriverWait(driver, 5);
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("fields_156")));
        Select priority = new Select(driver.findElement(By.xpath("//select[@name=\"fields[156]\"]")));
        priority.selectByVisibleText("Urgent");
        priority.selectByValue("35");

        //Project status
        Select projectStatus = new Select(driver.findElement(By.id("fields_157")));
        projectStatus.selectByValue("37");


        //Project Name

        WebElement projectName = driver.findElement(By.xpath("//input[@name=\"fields[158]\"]"));
        projectName.sendKeys("chms00-project-"+ randomUUIDString);

        //Project start date
        WebElement projectDate = driver.findElement(By.xpath("//button[@class=\"btn btn-default date-set\"]"));
        projectDate.click();

        //Project start date set today
        WebElement projectDateSetToday = driver.findElement(By.xpath("//td[@class=\"active day\"]"));
        projectDate.click();

        WebElement projectSubmit = driver.findElement(By.xpath("//button[@type=\"submit\" and @class=\"btn btn-primary btn-primary-modal-action\"]"));
        projectSubmit.click();

    }

    @Test
    public void createProjectNegative() {
        // given
        driver.get("https://digitalnizena.cz/rukovoditel/");

        // when
        WebElement usernameInput = driver.findElement(By.name("username"));
        usernameInput.sendKeys("rukovoditel");
        WebElement passwordInput = driver.findElement(By.name("password"));
        passwordInput.sendKeys("vse456ru");
        WebElement loginButton = driver.findElement(By.xpath("//button[@type=\"submit\" and @class=\"btn btn-info pull-right\"]"));
        loginButton.click();

        //go to projects
        WebElement sidebarProjects = driver.findElement(By.xpath("//i[@class=\"fa fa-reorder\"]"));
        sidebarProjects.click();

        //add project
        WebElement addProject = driver.findElement(By.xpath("//button[@class=\"btn btn-primary\" and @type=\"button\"]"));
        addProject.click();

        //Project priority
        WebDriverWait wait1 = new WebDriverWait(driver, 5);
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("fields_156")));
        Select priority = new Select(driver.findElement(By.xpath("//select[@name=\"fields[156]\"]")));
        priority.selectByVisibleText("Urgent");
        priority.selectByValue("35");

        //Project status
        Select projectStatus = new Select(driver.findElement(By.id("fields_157")));
        projectStatus.selectByValue("37");


        //Project Name
        WebElement projectName = driver.findElement(By.xpath("//input[@name=\"fields[158]\"]"));
        projectName.sendKeys("");

        //Project start date
        WebElement projectDate = driver.findElement(By.xpath("//button[@class=\"btn btn-default date-set\"]"));
        projectDate.click();

        //Project start date set today
        WebElement projectDateSetToday = driver.findElement(By.xpath("//td[@class=\"active day\"]"));
        projectDate.click();

        WebElement projectSubmit = driver.findElement(By.xpath("//button[@type=\"submit\" and @class=\"btn btn-primary btn-primary-modal-action\"]"));
        projectSubmit.click();

        WebElement required = driver.findElement(By.xpath("//label[@id=\"fields_158-error\"]"));
    }
}
