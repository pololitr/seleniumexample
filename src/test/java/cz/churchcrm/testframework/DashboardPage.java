package cz.churchcrm.testframework;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DashboardPage {
    protected WebDriver driver;
    public DashboardPage(WebDriver localDriver){
        this.driver = localDriver;
    }

    @FindBy(xpath="//i[@class=\"fa fa-reorder\"]")
    WebElement sidebarProjects;

    public void goToProjects (){
        sidebarProjects.click();
    }
}
