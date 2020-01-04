package cz.vse.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;

public class browserFactory {
    static WebDriver driver;

    public static WebDriver startBrowser(String browserName, String url)
    {
        switch (browserName) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
                ChromeOptions cho = new ChromeOptions();
                driver = new ChromeDriver(cho);
                //cho.addArguments("headless");
                break;
            case "opera":
                System.setProperty("webdriver.opera.driver", "src/test/resources/drivers/operadriver.exe");
                driver = new OperaDriver();
                break;
            case "IE":
                System.setProperty("webdriver.IE.driver", "src/test/resources/drivers/IEDriverServer.exe");
                driver = new InternetExplorerDriver();
                break;
        }
        driver.manage().window().maximize();
        driver.get(url);

        return driver;
    }

}
