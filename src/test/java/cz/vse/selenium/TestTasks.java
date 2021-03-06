package cz.vse.selenium;

import cz.churchcrm.testframework.*;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class TestTasks {
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

    Verify only New and Waiting tasks are displayed.
    Now remove all filters and verify all created tasks are displayed.

    Delete all tasks using Select all and batch delete.


     */

    @Test
    public void createTaskSimplePositive() {
        //Set name for project and task, UUID is same for both
        String taskNameUUID = "chms00-TASK-"+ randomUUIDString;
        String projectNameUUID = "chms00-PROJECT-"+ randomUUIDString;

        //Log into a page
        WebDriver driver = BrowserFactory.startBrowser("chrome", "");
        LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
        loginPage.loginIntoPortal("rukovoditel","vse456ru");

        //Create a project
        ProjectsPage projectsPage = PageFactory.initElements(driver, ProjectsPage.class);
        projectsPage.createProject("35", "37", projectNameUUID);

        //Create a task
        TasksPage tasksPage = PageFactory.initElements(driver, TasksPage.class);
        tasksPage.createTask(projectNameUUID,"42", ""+taskNameUUID,"46","54","DRY");

        //Validate Crated task task
        tasksPage.validateTask(projectNameUUID, taskNameUUID,"DRY","Task","New","High");

        //Delete task
        tasksPage.deleteTask(projectNameUUID, taskNameUUID);
    }

    @Test
    public void testMultipleTaskCration(){
        String taskNameUUID = "chms00-task-"+ randomUUIDString;
        String projectNameUUID = "chms00-PROJECT-"+ randomUUIDString;
        WebDriver driver = BrowserFactory.startBrowser("chrome", "");

        LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
        loginPage.loginIntoPortal("rukovoditel","vse456ru");

        ProjectsPage projectsPage = PageFactory.initElements(driver, ProjectsPage.class);
        projectsPage.createProject("35", "37", projectNameUUID);

        TasksPage tasksPage = PageFactory.initElements(driver, TasksPage.class);
        tasksPage.createTask(projectNameUUID, "42", ""+taskNameUUID,"46","54","DRY-New");
        tasksPage.createTask(projectNameUUID, "42", ""+taskNameUUID,"47","54","DRY-Open");
        tasksPage.createTask(projectNameUUID, "42", ""+taskNameUUID,"48","54","DRY-Waiting");
        tasksPage.createTask(projectNameUUID, "42", ""+taskNameUUID,"49","54","DRY-Done");
        tasksPage.createTask(projectNameUUID, "42", ""+taskNameUUID,"50","54","DRY-Closed");
        tasksPage.createTask(projectNameUUID, "42", ""+taskNameUUID,"51","54","DRY-Paid");
        tasksPage.createTask(projectNameUUID, "42", ""+taskNameUUID,"52","54","DRY-Canceled");

        //default filetr is on
        projectsPage.openProject(projectNameUUID);

        //Filter
        String appliedFilterGet = driver.findElement(By.xpath("//span[@class=\"filters-preview-condition-include\"]")).getText();
        Assert.assertEquals(appliedFilterGet, "New, Open, Waiting");

        //see 3
        List<String> statusExpected = new ArrayList<String>(Arrays.asList("New", "Open", "Waiting"));
        tasksPage.checkTaskTableRecords(3, statusExpected);

        //Change filter -  New and Waiting
        WebElement getFilterButton = driver.findElement(By.xpath("//span[@class=\"filters-preview-condition-include\"]"));
        getFilterButton.click();

        WebDriverWait wait6 = new WebDriverWait(driver, 5);
        wait6.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"values_chosen\"]/ul/li[2]/a")));
        WebElement removeFilter = driver.findElement(By.xpath("//*[@id=\"values_chosen\"]/ul/li[2]/a"));
        removeFilter.click();

        //Save filter
        WebElement saveButton = driver.findElement(By.xpath("//button[@class=\"btn btn-primary btn-primary-modal-action\"]"));
        saveButton.click();

        //Validate
        List<String> statusExpected2 = new ArrayList<String>(Arrays.asList("New", "Waiting"));
        tasksPage.checkTaskTableRecords(2, statusExpected2);

        //Change filter - all
        WebElement deleteFiltersButton = driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/div/div[2]/div/div[2]/div[2]/ul/li[1]/a[1]/i"));
        deleteFiltersButton.click();

        //Check number of records
        List<String> statusExpected3 = new ArrayList<String>(Arrays.asList("New","Open","Waiting","Done","Closed","Paid","Canceled"));
        tasksPage.checkTaskTableRecords(7, statusExpected3);

        //Bulk delete
        tasksPage.taskBulkDelete(projectNameUUID);

        //Return default filters
        tasksPage.defaultFilters(projectNameUUID);
    }
}
