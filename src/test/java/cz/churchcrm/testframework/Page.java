package cz.churchcrm.testframework;

import org.openqa.selenium.WebDriver;

public abstract class Page {
    protected WebDriver driver;

    public Page(WebDriver driver) {
        this.driver = driver;
    }

    public void shouldBeOpen() {

    }

    public void open(String url) {
        driver.get(url);
    }
}
