package cz.churchcrm.testframework;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

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

//    @FindBy(xpath = "//td[@class=\"fieldtype_input  field-168-td item_heading_td\"]")
//    WebElement foundTask;

    @FindBy(xpath = "//button[@class=\"btn btn-default btn-sm dropdown-toggle\" and @data-toggle=\"dropdown\"]")
    WebElement moreOptions;

    @FindBy(xpath = "//i[@class=\"fa fa-trash-o\"]")
    WebElement deleteButton;

    @FindBy(xpath = "//button[@type=\"submit\" and @class=\"btn btn-primary btn-primary-modal-action\"]")
    WebElement deleteButtonConfirm;

    public void searchTask (String taskName){
        searchTasks.clear();
        searchTasks.sendKeys(taskName);
        searchButtonTasks.click();
    }

    public void createTask (String taskType,String taskName, String taskStatus, String taskPriority, String taskDescription){
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

    public void openTask(String taskName){
        WebDriverWait wait1 = new WebDriverWait(driver, 5);
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),\""+taskName+"\")]")));
        WebElement foundTask = driver.findElement(By.xpath("//a[contains(text(),\""+taskName+"\")]"));
        foundTask.click();

    }


    public void deleteTask (String taskName){
        //open task
        openTask(taskName);
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
}
