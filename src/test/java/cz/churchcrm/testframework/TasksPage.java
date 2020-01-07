package cz.churchcrm.testframework;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class TasksPage {
    protected WebDriver driver;
    public TasksPage(WebDriver localDriver){
        this.driver = localDriver;
    }


    @FindBy(xpath = "//button[@class=\"btn btn-primary\"]")
    WebElement addTask;

    @FindBy(xpath = "//input[@name=\"fields[168]\"]")
    WebElement taskNameElement;

    @FindBy(xpath = "//body[@class=\"cke_editable cke_editable_themed cke_contents_ltr cke_show_borders\"]")
    WebElement taskDescriptionElement;

    @FindBy(xpath = "//button[@type=\"submit\" and @class=\"btn btn-primary btn-primary-modal-action\"]")
    WebElement taskSubmit;

    @FindBy(xpath = "//input[@type=\"text\" and @placeholder=\"Search\" and @class=\"form-control input-medium\"]")
    WebElement searchTasks;

    @FindBy(xpath = "//button[@title=\"Search\" and @class=\"btn btn-info\"]")
    WebElement searchButtonTasks;

    @FindBy(xpath = "//button[@class=\"btn btn-default btn-sm dropdown-toggle\" and @data-toggle=\"dropdown\"]")
    WebElement moreOptions;

    @FindBy(xpath = "//i[@class=\"fa fa-trash-o\"]")
    WebElement deleteButton;

    @FindBy(xpath = "//button[@type=\"submit\" and @class=\"btn btn-primary btn-primary-modal-action\"]")
    WebElement deleteButtonConfirm;

    String foundTaskXpath = "//a[@class=\"btn btn-default btn-xs purple\" and @title=\"Info\"]";
    String bulkOperationsCheckbox = "//div[@id=\"uniform-select_all_items\"]";
    String bulkDelete ="body>div.page-container>div.page-content-wrapper>div>div>div.row>div>div:nth-child(7)>div:nth-child(1)>div>div>ul>li:nth-child(2)>a";
    String confirmDelete = "//button[@class=\"btn btn-primary btn-primary-modal-action\"]";
    String filterPicker = "/html/body/div[3]/div[2]/div/div/div[2]/div/div[2]/div[1]/div[1]/div[1]/button";
    String defaultFilters = "//a[contains(@href,'users_filters&action=use&id=default')]";

    public void searchTask (String projectName, String taskName){
        DashboardPage dashboardPage = PageFactory.initElements(driver, DashboardPage.class);
        dashboardPage.goToProjects();

        ProjectsPage projectsPage = PageFactory.initElements(driver, ProjectsPage.class);
        projectsPage.openProject(projectName);

        searchTasks.clear();
        searchTasks.sendKeys(taskName);
        searchButtonTasks.click();
    }

    public void createTask (String projectName, String taskType,String taskName, String taskStatus, String taskPriority, String taskDescription){
        //Go to projects page
        DashboardPage dashboardPage = PageFactory.initElements(driver, DashboardPage.class);
        dashboardPage.goToProjects();

        //Find a specific project and open it
        ProjectsPage projectsPage = PageFactory.initElements(driver, ProjectsPage.class);
        projectsPage.searchProjects(projectName);
        projectsPage.openProject(projectName);

        //Initiate adding task
        addTask.click();

        //Task priority
        WebDriverWait wait2 = new WebDriverWait(driver, 5);
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.id("fields_167")));
        Select taskTypeElement = new Select(driver.findElement(By.xpath("//select[@name=\"fields[167]\"]")));
        taskTypeElement.selectByVisibleText("Task");
        taskTypeElement.selectByValue(taskType);

        //Task name
        taskNameElement.sendKeys(taskName);

        //Task status
        Select taskStatusElement = new Select(driver.findElement(By.xpath("//select[@name=\"fields[169]\"]")));
        taskStatusElement.selectByVisibleText("New");
        taskStatusElement.selectByValue(taskStatus);

        //Task priority
        Select taskPriorityElement = new Select(driver.findElement(By.xpath("//select[@name=\"fields[170]\"]")));
        taskPriorityElement.selectByVisibleText("Medium");
        taskPriorityElement.selectByValue(taskPriority);

        //Task description
        driver.switchTo().frame(0);
        taskDescriptionElement.sendKeys(taskDescription);
        driver.switchTo().defaultContent();

        //Task submit
        taskSubmit.click();


    }

    public void openTask(String projectName, String taskName){
        //Go to projects page
        DashboardPage dashboardPage = PageFactory.initElements(driver, DashboardPage.class);
        dashboardPage.goToProjects();

        //Search fro a specific task in a specific project
        searchTask(projectName, taskName);
        WebDriverWait wait1 = new WebDriverWait(driver, 5);
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(foundTaskXpath)));
        WebElement foundTask = driver.findElement(By.xpath(foundTaskXpath));
        foundTask.sendKeys(Keys.RETURN);

    }

    public void deleteTask (String projectName, String taskName){
        //open task
        openTask(projectName, taskName);
        //open more options
        moreOptions.click();
        //Delete
        WebDriverWait wait4 = new WebDriverWait(driver, 5);
        wait4.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//i[@class=\"fa fa-trash-o\"]")));
        deleteButton.click();
        //confirm deletion
        WebDriverWait waitForDeleteButtonConfirm = new WebDriverWait(driver, 5);
        waitForDeleteButtonConfirm.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@type=\"submit\" and @class=\"btn btn-primary btn-primary-modal-action\"]")));
        deleteButtonConfirm.click();
        //Final assert

        wait4.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"slimScroll\"]/table/tbody/tr/td")));
        String noRecords = driver.findElement(By.xpath("//*[@id=\"slimScroll\"]/table/tbody/tr/td")).getText();
        Assert.assertEquals("No Records Found",noRecords);

    }

    public void validateTask(String projectName, String taskName,String taskDescription,String taskType,String taskStatus,String taskPriority){
        openTask(projectName, taskName);
        // name,
        String taskNameGet = driver.findElement(By.xpath("//div[@class=\"caption\"]")).getText();
        Assert.assertEquals(taskNameGet, taskName);
        // description,
        String taskDescriptionGet = driver.findElement(By.xpath(" //div[@class=\"content_box_content fieldtype_textarea_wysiwyg\"]")).getText();
        Assert.assertEquals(taskDescriptionGet, taskDescription);
        //type Task,
        String taskTypeGet = driver.findElement(By.xpath("//tr[@class=\"form-group-167\"]")).getText();
        Assert.assertEquals(taskTypeGet, "Type\n"+taskType);
        // status
        String taskStatusGet = driver.findElement(By.xpath("//tr[@class=\"form-group-169\"]")).getText();
        Assert.assertEquals(taskStatusGet, "Status\n"+taskStatus);
        // priority
        String taskPriorityGet = driver.findElement(By.xpath("//tr[@class=\"form-group-170\"]")).getText();
        Assert.assertEquals(taskPriorityGet, "Priority\n"+taskPriority);
    }

    public void checkTaskTableRecords (int Expected, List<String> statusExpected){
        //Pick a table with task and compare it's number of rows with value from input
        WebDriverWait wait4 = new WebDriverWait(driver, 5);
        wait4.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody")));
        WebElement table = driver.findElement(By.xpath("//tbody"));
        List<WebElement> txt = table.findElements(By.tagName("tr"));
        //Final assert of found number of rows and value form input
        Assert.assertEquals(Expected, txt.size());

        //List fo comparasion crated
        List<String> statusActual = new ArrayList<>();

        //In this cycle, wich is running the same time as input table has rows, each iteration status is added to a list.
        for (int i = 1; i < Expected+1; i++) {
            String cell = driver.findElement(By.xpath("//table[@class=\"table table-striped table-bordered table-hover\"]/tbody/tr[" + i + "]/td[7]")).getText();
            statusActual.add(cell);
        }
        //Finnaly two lists are compared.  One from input which is declared and filled in test directly and the other filled in cycle.
        Assert.assertEquals(statusExpected,statusActual);


    }

    public void taskBulkDelete (String projectName){
        //Go to project page
        WebDriverWait wait = new WebDriverWait(driver, 5);
        DashboardPage dashboardPage = PageFactory.initElements(driver, DashboardPage.class);
        dashboardPage.goToProjects();

        //Open specific project
        ProjectsPage projectsPage = PageFactory.initElements(driver, ProjectsPage.class);
        projectsPage.searchProjects(projectName);
        projectsPage.openProject(projectName);

        //Click on bulk operation checkbox
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(bulkOperationsCheckbox)));
        WebElement deleteAllTasks = driver.findElement(By.xpath(bulkOperationsCheckbox));
        deleteAllTasks.click();

        //Click on specific bulk operation picker
        WebElement withSelected = driver.findElement(By.cssSelector("body>div.page-container>div.page-content-wrapper>div>div>div.row>div>div:nth-child(7)>div:nth-child(1)>div>div>button"));
        withSelected.click();

        //Pick bulk delete
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(bulkDelete)));
        WebElement deleteBulk = driver.findElement(By.cssSelector(bulkDelete));
        deleteBulk.click();

        //Confirm delete in modal
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(confirmDelete)));
        WebElement confirmDeleteBulk = driver.findElement(By.xpath(confirmDelete));
        confirmDeleteBulk.click();
    }

    public void defaultFilters(String projectName){
        //Go to projects page
        DashboardPage dashboardPage = PageFactory.initElements(driver, DashboardPage.class);
        dashboardPage.goToProjects();

        //Open a specific project
        ProjectsPage projectsPage = PageFactory.initElements(driver, ProjectsPage.class);
        projectsPage.openProject(projectName);

        //Click on filter picker
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(filterPicker)));
        WebElement filterButton = driver.findElement(By.xpath(filterPicker));
        filterButton.click();

        //Pick default filters
        WebDriverWait wait7 = new WebDriverWait(driver, 10);
        wait7.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(defaultFilters)));
        WebElement getFilters = driver.findElement(By.xpath(defaultFilters));
        getFilters.click();
    }

}
