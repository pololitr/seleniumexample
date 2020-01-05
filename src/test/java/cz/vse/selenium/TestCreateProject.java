package cz.vse.selenium;

import cz.churchcrm.testframework.BrowserFactory;
import cz.churchcrm.testframework.DashboardPage;
import cz.churchcrm.testframework.LoginPage;
import cz.churchcrm.testframework.ProjectsPage;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import cz.churchcrm.testframework.ConfigFileReader;



import java.util.UUID;


public class TestCreateProject {
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

    @Test
    public void runProjectTests(){
        createProjectNegative();
        createProjectPositive();
    }

    @Test
    public void createProjectPositive() {

        WebDriver driver = BrowserFactory.startBrowser("chrome","");
        LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
        loginPage.loginIntoPortal("rukovoditel","vse456ru");

        //go to projects
        DashboardPage dashboardPage = PageFactory.initElements(driver, DashboardPage.class);
        dashboardPage.goToProjects();

//        //search form specific name
        ProjectsPage projectsPage = PageFactory.initElements(driver, ProjectsPage.class);
//        projectsPage.searchProjects("chms00");

        //Crate project
        projectsPage.createProject("35","37", "chms00-PROJECT-"+randomUUIDString);
    }

    @Test
    public void createProjectNegative() {

        WebDriver driver = BrowserFactory.startBrowser("chrome","");
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
