package cz.vse.selenium;

import cz.churchcrm.testframework.BrowserFactory;
import cz.churchcrm.testframework.DashboardPage;
import cz.churchcrm.testframework.LoginPage;
import cz.churchcrm.testframework.ProjectsPage;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;



import java.util.UUID;


public class TestProjects {
    UUID uuid = UUID.randomUUID();
    String randomUUIDString = uuid.toString();
    String projectNameUUID = "chms00-PROJECT-"+ randomUUIDString;

    /*
   Test Suite - Creating project
   TC#2: Project with status New, priority High and filled start date as today is created.
         Verify that there is new row in project table.
         Delete project after test.

   TC#1: Project without name is not created
     */


    @Test
    public void createProjectPositive() {
        //Login into page
        WebDriver driver = BrowserFactory.startBrowser("chrome","");
        LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
        loginPage.loginIntoPortal("rukovoditel","vse456ru");

        //go to projects
        DashboardPage dashboardPage = PageFactory.initElements(driver, DashboardPage.class);
        dashboardPage.goToProjects();

        //Crate project
        ProjectsPage projectsPage = PageFactory.initElements(driver, ProjectsPage.class);
        projectsPage.createProject("35","37", projectNameUUID);

        //There is one new row and we can fid the project by name
        projectsPage.checkProjectTableRecords(1);
        projectsPage.openProject(projectNameUUID);

        //Delete project
        projectsPage.deleteProject(projectNameUUID);
    }

    @Test
    public void createProjectNegative() {
        //Login into page
        WebDriver driver = BrowserFactory.startBrowser("chrome","");
        LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
        loginPage.loginIntoPortal("rukovoditel","vse456ru");

        //go to projects
        DashboardPage dashboardPage = PageFactory.initElements(driver, DashboardPage.class);
        dashboardPage.goToProjects();

        //Crate project with no name
        ProjectsPage projectsPage = PageFactory.initElements(driver, ProjectsPage.class);
        projectsPage.createProject("35","37", "");
        //Name is required
        projectsPage.missingNameError();
    }
}
