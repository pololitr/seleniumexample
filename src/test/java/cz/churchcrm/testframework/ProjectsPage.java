package cz.churchcrm.testframework;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.UUID;

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

//    @FindBy(xpath="//select[@name=\"fields[156]\"]")
//    WebElement priority;

    @FindBy(id="fields_157")
    WebElement projectStatus;

    @FindBy(xpath="//input[@name=\"fields[158]\"]")
    WebElement projectName;

    @FindBy(xpath="//button[@class=\"btn btn-default date-set\"]")
    WebElement projectDate;

    @FindBy(xpath="//td[@class=\"active day\"]")
    WebElement projectDateSetToday;

    @FindBy(xpath="//button[@type=\"submit\" and @class=\"btn btn-primary btn-primary-modal-action\"]")
    WebElement projectSubmit;

    public void searchProjects (String projectName){
        searchProjects.clear();
        searchProjects.sendKeys(projectName);
        searchButton.click();
    }

    public void createProject(String priorityVal, String statusVal, String NameVal){
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
}
