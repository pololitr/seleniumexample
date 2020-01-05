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
        DashboardPage dashboardPage = PageFactory.initElements(driver, DashboardPage.class);
        dashboardPage.goToProjects();

        ProjectsPage projectsPage = PageFactory.initElements(driver, ProjectsPage.class);
        projectsPage.searchProjects(projectName);
        //todo kdyz nic nenajdu tak vytvor projekt a menu predej do contains pickeru
        projectsPage.openProject(projectName);


        addTask.click();

        //Task priority
        WebDriverWait wait2 = new WebDriverWait(driver, 5);
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.id("fields_167")));
        Select taskTypeElement = new Select(driver.findElement(By.xpath("//select[@name=\"fields[167]\"]")));
        taskTypeElement.selectByVisibleText("Task");
        taskTypeElement.selectByValue(taskType);

        //task name
        taskNameElement.sendKeys(taskName);

        //task status
        Select taskStatusElement = new Select(driver.findElement(By.xpath("//select[@name=\"fields[169]\"]")));
        taskStatusElement.selectByVisibleText("New");
        taskStatusElement.selectByValue(taskStatus);

        //task priority
        Select taskPriorityElement = new Select(driver.findElement(By.xpath("//select[@name=\"fields[170]\"]")));
        taskPriorityElement.selectByVisibleText("Medium");
        taskPriorityElement.selectByValue(taskPriority);

        //task description
        driver.switchTo().frame(0);
        taskDescriptionElement.sendKeys(taskDescription);
        driver.switchTo().defaultContent();

        //task submit
        taskSubmit.click();


    }

    public void openTask(String projectName, String taskName){
        DashboardPage dashboardPage = PageFactory.initElements(driver, DashboardPage.class);
        dashboardPage.goToProjects();

//        ProjectsPage projectsPage = PageFactory.initElements(driver, ProjectsPage.class);
//       // projectsPage.searchProjects(projectName);
//        projectsPage.openProject(projectName);

        searchTask(projectName, taskName);
//todo proc to nefacha
//        org.openqa.selenium.WebDriverException: unknown error: Element <a title="Info" class="btn btn-default btn-xs purple" href="https://digitalnizena.cz/rukovoditel/index.php?module=items/info&amp;path=21-1279/22-5073&amp;gotopage[1356]=1">...</a> is not clickable at point (357, 452). Other element would receive the click: <div class="data_listing_processing"></div>

        WebDriverWait wait1 = new WebDriverWait(driver, 5);
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class=\"btn btn-default btn-xs purple\" and @title=\"Info\"]")));
        WebElement foundTask = driver.findElement(By.xpath("//a[@class=\"btn btn-default btn-xs purple\" and @title=\"Info\"]"));
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

    public void checkTaskTableRecords (int Expected){

        WebDriverWait wait4 = new WebDriverWait(driver, 5);
        wait4.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody")));
        WebElement table = driver.findElement(By.xpath("//tbody"));
        List<WebElement> txt = table.findElements(By.tagName("tr"));
        Assert.assertEquals(Expected, txt.size());
    }
    public void taskBulkDelete (String projectName){
        WebDriverWait wait = new WebDriverWait(driver, 5);
        DashboardPage dashboardPage = PageFactory.initElements(driver, DashboardPage.class);
        dashboardPage.goToProjects();

        ProjectsPage projectsPage = PageFactory.initElements(driver, ProjectsPage.class);
        projectsPage.searchProjects(projectName);
        projectsPage.openProject(projectName);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id=\"uniform-select_all_items\"]")));
        WebElement deleteAllTasks = driver.findElement(By.xpath("//div[@id=\"uniform-select_all_items\"]"));
        deleteAllTasks.click();

        WebElement withSelected = driver.findElement(By.cssSelector("body>div.page-container>div.page-content-wrapper>div>div>div.row>div>div:nth-child(7)>div:nth-child(1)>div>div>button"));
        withSelected.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body>div.page-container>div.page-content-wrapper>div>div>div.row>div>div:nth-child(7)>div:nth-child(1)>div>div>ul>li:nth-child(2)>a")));
        WebElement deleteBulk = driver.findElement(By.cssSelector("body>div.page-container>div.page-content-wrapper>div>div>div.row>div>div:nth-child(7)>div:nth-child(1)>div>div>ul>li:nth-child(2)>a"));
        deleteBulk.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"delete_selected_form\"]/div[2]/button[1]")));
        WebElement confirmDeleteBulk = driver.findElement(By.xpath(" //*[@id=\"delete_selected_form\"]/div[2]/button[1]"));
        confirmDeleteBulk.click();
    }

    public void defaultFilters(String projectName){
        DashboardPage dashboardPage = PageFactory.initElements(driver, DashboardPage.class);
        dashboardPage.goToProjects();

        ProjectsPage projectsPage = PageFactory.initElements(driver, ProjectsPage.class);
        projectsPage.searchProjects(projectName);
        projectsPage.openProject(projectName);

        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[3]/div[2]/div/div/div[2]/div/div[2]/div[1]/div[1]/div[1]/button")));
        WebElement filterButton = driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/div/div[2]/div/div[2]/div[1]/div[1]/div[1]/button"));
        filterButton.click();

        WebDriverWait wait7 = new WebDriverWait(driver, 10);
        wait7.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@href,'users_filters&action=use&id=default')]")));
        WebElement getFilters = driver.findElement(By.xpath("//a[contains(@href,'users_filters&action=use&id=default')]"));
        getFilters.click();
    }

}
