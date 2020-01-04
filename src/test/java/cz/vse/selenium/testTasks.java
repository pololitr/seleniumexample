package cz.vse.selenium;

import cz.churchcrm.testframework.DashboardPage;
import cz.churchcrm.testframework.LoginPage;
import cz.churchcrm.testframework.ProjectsPage;
import cz.churchcrm.testframework.TasksPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.UUID;

public class testTasks {
    UUID uuid = UUID.randomUUID();
    String randomUUIDString = uuid.toString();
        /*

    TC#1: (Precondition - there exists project yourname already in the system.)
    New Task will be created with type Task, name, status New, priority Medium and some description.
    Verify task attributes (Type Task, description, name, priority, status) on task info page (icon i). Delete that task.

    TC#2: (Precondition - there exists project yourname already in the system.)
    Create new 7 tasks with different statuses New, Open, Waiting, Done, Closed, Paid, Canceled.
    Verify that using default filter (New, Open, Waiting) only 3 tasks will be shown.
    Change applied filter in Filter info dialog to only contain (New, Waiting) ...there are more ways how to do it
    (you can click small x on Open "label" to delete it, or you can deal with writing into "suggestion box").
    Verify only New and Waiting tasks are displayed. Now remove all filters and verify all created tasks are displayed.
    Delete all tasks using Select all and batch delete.


     */

    @Test
    public void createTaskSimplePositive() {
        String taskNameUUID = "chms00-task-"+ randomUUIDString;

        WebDriver driver = BrowserFactory.startBrowser("chrome","https://digitalnizena.cz/rukovoditel/");
        LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
        loginPage.loginIntoPortal("rukovoditel","vse456ru");

        //go to projects
        DashboardPage dashboardPage = PageFactory.initElements(driver, DashboardPage.class);
        dashboardPage.goToProjects();

        //search form specific project name
        ProjectsPage projectsPage = PageFactory.initElements(driver, ProjectsPage.class);
        projectsPage.searchProjects("chms00");

        //todo kdyz nic nenajdu tak vytvor projekt a menu predej do contains pickeru

        //Pick found project
        projectsPage.openProject("chms00");

//        Add task
        TasksPage tasksPage = PageFactory.initElements(driver, TasksPage.class);
        tasksPage.createTask("42", ""+taskNameUUID,"46","54","DRY");

        //Task search new
        tasksPage.searchTask(taskNameUUID);

        tasksPage.deleteTask(taskNameUUID);

        tasksPage.searchTask(taskNameUUID);
        //todo kontrola ze je to empty
    }
}
