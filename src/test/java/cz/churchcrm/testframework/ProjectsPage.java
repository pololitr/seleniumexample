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

public class ProjectsPage {
    protected WebDriver driver;
    public ProjectsPage(WebDriver localDriver){
        this.driver = localDriver;
    }


    @FindBy(id="entity_items_listing66_21_search_keywords")
    WebElement  searchProjects;

    @FindBy(xpath="//button[@title=\"Search\" and @class=\"btn btn-info\"]")
    WebElement searchButton;

    @FindBy(xpath="//button[@class=\"btn btn-primary\" and @type=\"button\"]")
    WebElement addProject;

    @FindBy(xpath="//input[@name=\"fields[158]\"]")
    WebElement projectName;

    @FindBy(xpath="//button[@class=\"btn btn-default date-set\"]")
    WebElement projectDate;

    @FindBy(xpath="//td[@class=\"active day\"]")
    WebElement projectDateSetToday;

    @FindBy(xpath="//button[@type=\"submit\" and @class=\"btn btn-primary btn-primary-modal-action\"]")
    WebElement projectSubmit;

    @FindBy(xpath = "//label[@id=\"fields_158-error\"]")
    WebElement noProjectNameError;

    @FindBy(xpath = "//i[@class=\"fa fa-info\"]")
    WebElement info;

//    @FindBy(xpath = "//button[@class=\"btn btn-default btn-sm dropdown-toggle\" and @data-hover=\"dropdown\"]")
//    WebElement moreOptions;
//
//    @FindBy(xpath = "//i[@class=\"fa fa-trash-o\"]")
//    WebElement deleteButton;
//
//    @FindBy(xpath = "//button[@type=\"submit\" and @class=\"btn btn-primary btn-primary-modal-action\"]")
//    WebElement deleteButtonConfirm;
//
//    @FindBy(xpath = "//input[@name=\"delete_confirm\"]")
//    WebElement deleteButtonCheckBox;

    static ConfigFileReader configFileReader;



    public void searchProjects (String projectName){
        //Go to project page page
        DashboardPage dashboardPage = PageFactory.initElements(driver, DashboardPage.class);
        dashboardPage.goToProjects();

        //Search for specific project
        searchProjects.clear();
        searchProjects.sendKeys(projectName);
        searchButton.click();
    }

    public void createProject(String priorityVal, String statusVal, String NameVal){
        //Go to project page
        DashboardPage dashboardPage = PageFactory.initElements(driver, DashboardPage.class);
        dashboardPage.goToProjects();

        addProject.click();
        //Project priority
        WebDriverWait wait1 = new WebDriverWait(driver, 5);
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("fields_156")));
        Select priority = new Select(driver.findElement(By.xpath("//select[@name=\"fields[156]\"]")));
        priority.selectByVisibleText("Urgent");
        priority.selectByValue(priorityVal);

        //Project status
        Select projectStatus = new Select(driver.findElement(By.id("fields_157")));
        projectStatus.selectByValue(statusVal);

        //Project name
        projectName.sendKeys(NameVal);

        //Project Date
        projectDate.click();
        projectDateSetToday.click();

        //Submit project
        projectSubmit.click();

    }
    public void openProject(String projectName){
        //Go to project page
        DashboardPage dashboardPage = PageFactory.initElements(driver, DashboardPage.class);
        dashboardPage.goToProjects();

        //Search for a specific project and open it
        searchProjects(projectName);
        WebDriverWait wait1 = new WebDriverWait(driver, 5);
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),\""+projectName+"\")]")));
        WebElement foundProject = driver.findElement(By.xpath("//a[contains(text(),\""+projectName+"\")]"));
        foundProject.click();
    }

    public void checkProjectTableRecords (int Expected) {
        //Pick a table with projects and compare it's number of rows with value from input
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody")));
        WebElement table = driver.findElement(By.xpath("//tbody"));
        List<WebElement> txt = table.findElements(By.tagName("tr"));

        //Final assert of found number of rows and value form input
        Assert.assertEquals(Expected, txt.size());
    }
    public void missingNameError(){
        //Check error message
        Assert.assertEquals("This field is required!", noProjectNameError.getText());

    }

    public void deleteProject (String projectName){
        configFileReader= new ConfigFileReader();

        WebDriverWait wait = new WebDriverWait(driver, 5);
        searchProjects(projectName);
        //open more options
        info.click();

        // Xpath nebyla schopna zachytit submit button, xpath //div[@it="uniform-delete_confirm"] ci //input[@it="delete_confirm]
        //Expected condition failed: waiting for visibility of element located by By.cssSelector: #delete_confirm (tried for 5 second(s) with 500 milliseconds interval)
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body>div.page-container>div.page-content-wrapper>div>div>div.row>div>div:nth-child(3)>div.col-md-4>div>div>div:nth-child(2)>table>tbody>tr.form-group-153>td")));
        String getTaskID = driver.findElement(By.cssSelector("body>div.page-container>div.page-content-wrapper>div>div>div.row>div>div:nth-child(3)>div.col-md-4>div>div>div:nth-child(2)>table>tbody>tr.form-group-153>td")).getText();
        driver.get(configFileReader.getApplicationUrl()+"index.php?module=items/&action=delete&id="+getTaskID+"&path=21-"+getTaskID);

        //Final assert
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"slimScroll\"]/table/tbody/tr/td")));
        String noRecords = driver.findElement(By.xpath("//*[@id=\"slimScroll\"]/table/tbody/tr/td")).getText();
        Assert.assertEquals("No Records Found",noRecords);

//        moreOptions.click();
//        //Delete
//        WebDriverWait wait4 = new WebDriverWait(driver, 5);
//        wait4.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//i[@class=\"fa fa-trash-o\"]")));
//        deleteButton.click();
//
//        wait4.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#delete_confirm")));
//        WebElement deleteButtonCheckBoxElement = driver.findElement(By.cssSelector("#delete_confirm"));
//        deleteButtonCheckBoxElement.click();
//
//        //confirm deletion
//        WebDriverWait waitForDeleteButtonConfirm = new WebDriverWait(driver, 5);
//        waitForDeleteButtonConfirm.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@type=\"submit\" and @class=\"btn btn-primary btn-primary-modal-action\"]")));
//        deleteButtonConfirm.click();

    }


}
