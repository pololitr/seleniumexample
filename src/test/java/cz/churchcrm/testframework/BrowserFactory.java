package cz.churchcrm.testframework;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;

public class BrowserFactory {
    static WebDriver driver;
    static ConfigFileReader configFileReader;

    public static WebDriver startBrowser(String browserName, String browserArgs )
    {
        configFileReader= new ConfigFileReader();

        switch (browserName) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
                ChromeOptions cho = new ChromeOptions();
                driver = new ChromeDriver(cho);
                cho.addArguments(browserArgs);
                break;
            case "opera":
                System.setProperty("webdriver.opera.driver", "src/test/resources/drivers/operadriver.exe");
                driver = new OperaDriver();
                break;
            case "IE":
                System.setProperty("webdriver.ie.driver", "src/test/resources/drivers/IEDriverServer.exe");
                driver = new InternetExplorerDriver();
                break;
        }
        driver.manage().window().maximize();
        driver.get(configFileReader.getApplicationUrl());

        return driver;
    }

}
