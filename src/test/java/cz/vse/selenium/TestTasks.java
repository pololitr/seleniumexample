package cz.vse.selenium;

import cz.churchcrm.testframework.BrowserFactory;
import cz.churchcrm.testframework.ConfigFileReader;
import cz.churchcrm.testframework.LoginPage;
import cz.churchcrm.testframework.TasksPage;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
    Verify only New and Waiting tasks are displayed. Now remove all filters and verify all created tasks are displayed.

    Delete all tasks using Select all and batch delete.


     */

    @Test @Ignore
    public void createTaskSimplePositive() {
        String taskNameUUID = "chms00-task-"+ randomUUIDString;

        WebDriver driver = BrowserFactory.startBrowser("chrome", "");

        LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
        loginPage.loginIntoPortal("rukovoditel","vse456ru");

//        Add task
        TasksPage tasksPage = PageFactory.initElements(driver, TasksPage.class);
        tasksPage.createTask("chms00","42", ""+taskNameUUID,"46","54","DRY");

        //Validate task
        tasksPage.validateTask("chms00", taskNameUUID,"DRY","Task","New","High");

        //Delete task
        tasksPage.deleteTask("chms00", taskNameUUID);
        //todo kontrola ze je to empty
    }

    @Test
    public void testMultipleTaskCration(){
        String taskNameUUID = "chms00-task-"+ randomUUIDString;
        WebDriver driver = BrowserFactory.startBrowser("chrome", "");

        LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
        loginPage.loginIntoPortal("rukovoditel","vse456ru");

        TasksPage tasksPage = PageFactory.initElements(driver, TasksPage.class);
        tasksPage.createTask("chms00", "42", ""+taskNameUUID,"46","54","DRY-New");
        tasksPage.createTask("chms00", "42", ""+taskNameUUID,"47","54","DRY-Open");
        tasksPage.createTask("chms00", "42", ""+taskNameUUID,"48","54","DRY-Waiting");
        tasksPage.createTask("chms00", "42", ""+taskNameUUID,"49","54","DRY-Done");
        tasksPage.createTask("chms00", "42", ""+taskNameUUID,"50","54","DRY-Closed");
        tasksPage.createTask("chms00", "42", ""+taskNameUUID,"51","54","DRY-Paid");
        tasksPage.createTask("chms00", "42", ""+taskNameUUID,"52","54","DRY-Canceled");

        //default filetr is on
        tasksPage.openTask("chms00", "chms00");

        //Filter
        String appliedFilterGet = driver.findElement(By.xpath("//span[@class=\"filters-preview-condition-include\"]")).getText();
        Assert.assertEquals(appliedFilterGet, "New, Open, Waiting");

        //see 3
        tasksPage.checkTaskTableRecords(3);

        //Chnage filter -  New and Waitin
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
        tasksPage.checkTaskTableRecords(2);

        //Change filter - all
        WebElement deleteFiltersButton = driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/div/div[2]/div/div[2]/div[2]/ul/li[1]/a[1]/i"));
        deleteFiltersButton.click();

        tasksPage.checkTaskTableRecords(7);
        tasksPage.taskBulkDelete("chms00");

        //Return default filters
        tasksPage.defaultFilters("chms00");
    }
}
