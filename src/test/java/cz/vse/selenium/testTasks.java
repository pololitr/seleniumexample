package cz.vse.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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
    public void createTaskSimplePositive() {
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

        //search chms00 project
        WebElement searchTasks = driver.findElement(By.id("entity_items_listing66_21_search_keywords"));
        searchTasks.sendKeys("");

        //Pick found project
        WebDriverWait wait1 = new WebDriverWait(driver, 5);
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@class=\"fieldtype_input  field-158-td item_heading_td\"]")));
        WebElement foundProject = driver.findElement(By.xpath("//td[@class=\"fieldtype_input  field-158-td item_heading_td\"]"));
        foundProject.click();

        //Add task
        WebElement addTask = driver.findElement(By.xpath("//button[@class=\"btn btn-primary\"]"));
        addTask.click();

        //Task type
        WebDriverWait wait2 = new WebDriverWait(driver, 5);
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.id("fields_167")));
        Select type = new Select(driver.findElement(By.xpath("//select[@name=\"fields[167]\"]")));
        type.selectByVisibleText("Task");
        type.selectByValue("42");

        //Project Name
        WebElement taskName = driver.findElement(By.xpath("//input[@name=\"fields[168]\"]"));
        taskName.sendKeys("chms00-task-"+ randomUUIDString);

        //Task status 46-52
        Select taskStatus = new Select(driver.findElement(By.xpath("//select[@name=\"fields[169]\"]")));
        taskStatus.selectByVisibleText("New");
        taskStatus.selectByValue("46");


        //Task priority
        Select taskPriority = new Select(driver.findElement(By.xpath("//select[@name=\"fields[170]\"]")));
        taskPriority.selectByVisibleText("Medium");
        taskPriority.selectByValue("54");

        //Task description
        driver.switchTo().frame(0);
        WebElement taskDescription = driver.findElement(By.xpath("//body[@class=\"cke_editable cke_editable_themed cke_contents_ltr cke_show_borders\"]"));
        taskDescription.sendKeys("DRY");
        driver.switchTo().defaultContent();

        WebElement projectSubmit = driver.findElement(By.xpath("//button[@type=\"submit\" and @class=\"btn btn-primary btn-primary-modal-action\"]"));
        projectSubmit.click();

    }
}
