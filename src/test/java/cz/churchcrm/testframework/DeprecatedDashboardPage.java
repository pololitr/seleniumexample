package cz.churchcrm.testframework;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DeprecatedDashboardPage extends Page {

    public DeprecatedDashboardPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void shouldBeOpen() {
        Assert.assertEquals(driver.getCurrentUrl(), "http://digitalnizena.cz/church/Menu.php");
//        assertThat(driver.getTitle()).contains("ChurchCRM: Welcome to");
        driver.findElement(By.cssSelector("aside.main-sidebar"));
    }
}
