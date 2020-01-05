package cz.churchcrm.testframework;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

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

    public void searchProjects (String projectName){
        DashboardPage dashboardPage = PageFactory.initElements(driver, DashboardPage.class);
        dashboardPage.goToProjects();
        searchProjects.clear();
        searchProjects.sendKeys(projectName);
        searchButton.click();
    }

    public void createProject(String priorityVal, String statusVal, String NameVal){
        DashboardPage dashboardPage = PageFactory.initElements(driver, DashboardPage.class);
        dashboardPage.goToProjects();

        addProject.click();
        //Project priority
        WebDriverWait wait1 = new WebDriverWait(driver, 5);
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("fields_156")));
        Select priority = new Select(driver.findElement(By.xpath("//select[@name=\"fields[156]\"]")));
        priority.selectByVisibleText("Urgent");
        priority.selectByValue(priorityVal);

        Select projectStatus = new Select(driver.findElement(By.id("fields_157")));
        projectStatus.selectByValue(statusVal);

        projectName.sendKeys(NameVal);

        projectDate.click();

        projectDateSetToday.click();

        projectSubmit.click();

    }
    public void openProject(String projectName){
        DashboardPage dashboardPage = PageFactory.initElements(driver, DashboardPage.class);
        dashboardPage.goToProjects();
        searchProjects(projectName);
        WebDriverWait wait1 = new WebDriverWait(driver, 5);
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),\""+projectName+"\")]")));
        WebElement foundProject = driver.findElement(By.xpath("//a[contains(text(),\""+projectName+"\")]"));
        foundProject.click();
    }
}
