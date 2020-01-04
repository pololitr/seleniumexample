package cz.vse.selenium;

import cz.churchcrm.testframework.DashboardPage;
import cz.churchcrm.testframework.LoginPage;
import cz.churchcrm.testframework.ProjectsPage;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.util.UUID;


public class testCreateProject {
    UUID uuid = UUID.randomUUID();
    String randomUUIDString = uuid.toString();
//    UUID uuid = UUID.randomUUID();
//    String randomUUIDString = uuid.toString();
    /*
    Test Suite - Creating project

   TC#2: Project with status New, priority High and filled start date as today is created.
         Verify that there is new row in project table.
         Delete project after test.

   TC#1: Project without name is not created


     */


//    private ChromeDriver driver;

//    @Before
//    public void init() {
//        ChromeOptions cho = new ChromeOptions();
//        boolean runOnTravis = false;
//        if (runOnTravis) {
//            cho.addArguments("headless");
//        } else {
//            System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
//        }
////      ChromeDriverService service = new ChromeDriverService();
//        driver = new ChromeDriver(cho);
////      driver.manage().window().maximize();
//    }
//
//    @After
//    public void tearDown() {
//        // driver.close();
//    }

    @Test @Ignore
    public void createProjectPositive() {

        WebDriver driver = BrowserFactory.startBrowser("chrome","https://digitalnizena.cz/rukovoditel/");
        LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
        loginPage.loginIntoPortal("rukovoditel","vse456ru");

        //go to projects
        DashboardPage dashboardPage = PageFactory.initElements(driver, DashboardPage.class);
        dashboardPage.goToProjects();

        //search form specific name
        ProjectsPage projectsPage = PageFactory.initElements(driver, ProjectsPage.class);
        projectsPage.searchProjects("chms00");

        //Crate project
        projectsPage.createProject("35","37", "chms00-PROJECT-"+randomUUIDString);
    }

    @Test
    public void createProjectNegative() {

        WebDriver driver = BrowserFactory.startBrowser("chrome","https://digitalnizena.cz/rukovoditel/");
        LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
        loginPage.loginIntoPortal("rukovoditel","vse456ru");

        //go to projects
        DashboardPage dashboardPage = PageFactory.initElements(driver, DashboardPage.class);
        dashboardPage.goToProjects();

        //Crate project
        ProjectsPage projectsPage = PageFactory.initElements(driver, ProjectsPage.class);
        projectsPage.createProject("35","37", "");

    }
}
