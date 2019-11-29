package cz.vse.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.UUID;

/**
 * Unit test for simple App.
 */
public class AppTest {
    private ChromeDriver driver;

    @Before
    public void init() {
        ChromeOptions cho = new ChromeOptions();

        boolean runOnTravis = true;
        if (runOnTravis) {
            cho.addArguments("headless");
        } else {
            System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
        }
//        ChromeDriverService service = new ChromeDriverService()
        driver = new ChromeDriver(cho);
//        driver.manage().window().maximize();
    }

    @After
    public void tearDown() {
//        driver.close();
    }

    @Test
    public void google1_should_pass() {
        driver.get("https://www.google.com/");
        WebElement searchInput = driver.findElement(By.name("q"));
        searchInput.sendKeys("travis");
        searchInput.sendKeys(Keys.ENTER);
        Assert.assertTrue(driver.getTitle().startsWith("travis - "));
        driver.quit();
    }

    @Test
    public void alzaTest() throws InterruptedException {
        driver.get("https://www.alza.cz/");
        WebElement searchInput = driver.findElement(By.cssSelector("#edtSearch"));
        searchInput.sendKeys("ubiquiti unifi");

        WebDriverWait wait = new WebDriverWait(driver, 1);
        wait.until(ExpectedConditions.presenceOfElementLocated( By.cssSelector("#ui-id-1>li.t7") ));

        List<WebElement> searchItems = driver.findElements(By.cssSelector("#ui-id-1>li.t3"));
        Assert.assertEquals(3, searchItems.size());
        driver.quit();
    }

    @Test
    public void google2_should_fail() {
        driver.get("https://www.google.com/");
       // WebElement searchInputNotExisting = driver.findElement(By.name("kdsfkladsjfas"));
        driver.quit();
    }

    @Test
    public void google3_should_fail() {
        driver.get("https://www.google.com/");
        Assert.assertEquals("one", "one");
        driver.quit();
    }

    public void shouldNotLoginUsingInvalidPassword() {
        // given
        driver.get("https://opensource-demo.orangehrmlive.com/");

        // when
        WebElement usernameInput = driver.findElement(By.id("txtUsername"));
        usernameInput.sendKeys("admin");
        WebElement passwordInput = driver.findElement(By.id("txtPassword"));
        passwordInput.sendKeys("invalidPassssssssword");
        WebElement loginButton = driver.findElement(By.id("btnLogin"));
        loginButton.click();

        // then
        WebElement errorMessageSpan = driver.findElement(By.id("spanMessage"));
        Assert.assertEquals("Invalid credentials", errorMessageSpan.getText());

        // validation error exists
        // url changed to https://opensource-demo.orangehrmlive.com/index.php/auth/validateCredentials
        // there is no menu
    }


    @Test
    public void shouldLoginUsingValidCredentials() {
        // given
        //driver.get("http://demo.churchcrm.io/master/");
        driver.get("http://digitalnizena.cz/church/");

        // when
        WebElement usernameInput = driver.findElement(By.id("UserBox"));
        usernameInput.sendKeys("church");
        WebElement passwordInput = driver.findElement(By.id("PasswordBox"));
        passwordInput.sendKeys("church12345");
        WebElement loginButton = driver.findElement(By.className("btn-primary"));
        loginButton.click();
    }

    public void shouldCreateNewUser() throws InterruptedException {
        // Given
        shouldLoginUsingValidCredentials();

        // When
        driver.get("http://digitalnizena.cz/church/PersonEditor.php");

        WebElement genderSelectElement = driver.findElement(By.name("Gender"));
        Select genderSelect = new Select(genderSelectElement);
        genderSelect.selectByVisibleText("Male");

        WebElement firstNameInput = driver.findElement(By.id("FirstName"));
        firstNameInput.sendKeys("John");
        WebElement lastNameInput = driver.findElement(By.id("LastName"));
        String uuid = UUID.randomUUID().toString();
        lastNameInput.sendKeys("Doe " + uuid);
        WebElement emailInput = driver.findElement(By.name("Email"));
        emailInput.sendKeys("john.doe@gmail.com");

        WebElement classificationSelectElement = driver.findElement(By.name("Classification"));
        Select classificationSelect = new Select(classificationSelectElement);
        classificationSelect.selectByIndex(4);

        WebElement personSaveButton = driver.findElement(By.id("PersonSaveButton"));
        personSaveButton.click();

        // Then
        driver.get("http://digitalnizena.cz/church/v2/people");

        WebElement searchInput = driver.findElement(By.cssSelector("#members_filter input"));
        searchInput.sendKeys(uuid);
        Thread.sleep(500);

        // to verify if record is shown in table grid, we first filter the whole table to get exactly one data row
        // that row should contain previously generated UUID value (in last name
        // UKOL...opravit, doplnit tak, aby se provedla verifikace ze kontakt, ktery jsme vytvorili opravdu existuje
        //    (jde vyhledat a zobrazi se v tabulce)
        //    doporucuji radek tabulky s danou osobou projit (traverzovat), nebo jinym zpusobem v nem najit retezec UUID, ktery jednoznacne identifikuje pridanou osobu
        List<WebElement> elements = driver.findElements(By.cssSelector("table#members tr"));
        Assert.assertEquals(2, elements.size());

        // data row is at index 0, header row is at index 1  (because in ChurchCRM html code there is tbody before thead)
        WebElement personTableRow = elements.get(0);


        // option1
        Assert.assertTrue(personTableRow.getText().contains(uuid));

        // option2 - traverse all cells in table grid
        List<WebElement> cells = personTableRow.findElements(By.tagName("td"));
        final int EXPECTED_COLUMNS = 9;
        Assert.assertEquals(EXPECTED_COLUMNS, cells.size());
        for (int i = 0; i < cells.size(); i++) {
            WebElement cell = cells.get(i);
            if (cell.getText().contains(uuid)) {
                //
            }

            System.out.println(cells.get(i).getText());
        }
    }


    @Test
    public void given_userIsLoggedIn_when_userAddsNewDeposit_then_depositRecordIsShownInDepositTableGrid() throws InterruptedException {
        // GIVEN user is logged in

        shouldLoginUsingValidCredentials();

        // WHEN user adds deposit comment

        driver.get("http://digitalnizena.cz/church/FindDepositSlip.php");

        WebElement depositCommentInput = driver.findElement(By.cssSelector("#depositComment"));
        String uuid = UUID.randomUUID().toString();
        String depositComment = "deposit-PavelG-" + uuid;
        depositCommentInput.sendKeys(depositComment);

        WebElement depositDateInput = driver.findElement(By.cssSelector("#depositDate"));
        depositDateInput.click();
        depositDateInput.clear();
        depositDateInput.sendKeys("2018-10-30");

        WebElement addDepositButton = driver.findElement(By.cssSelector("#addNewDeposit"));
        addDepositButton.click();

        // THEN newly added deposit should be shown in deposits table grid

        // option1 - wait exactly 2 seconds, blocks the thread ....not recommended
        // Thread.sleep(2000);

        // option2 - use custom "expected condition" of WebDriver framework
        WebDriverWait wait = new WebDriverWait(driver, 2);     // timeout after 2 seconds
        wait.until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                // each time, we try to get the very first row from table grid and check, if contains the last record

                List<WebElement> depositRows = driver.findElements(By.cssSelector("#depositsTable_wrapper #depositsTable tbody tr"));
                WebElement firstRow = depositRows.get(0);
                String innerHTML = firstRow.getAttribute("innerHTML");

                if (innerHTML.contains(uuid)) {
                    Assert.assertTrue(innerHTML.contains("10-30-18"));    // beware, different date format in table grid vs. input field
                    Assert.assertTrue(innerHTML.contains(depositComment));
                    return true;     // expected condition is met
                } else {
                    return false;    // selenium webdriver will continue polling the DOM each 500ms and check the expected condition by calling method apply(webDriver) again
                }
            }
        });
    }

    public void deleteDeposits() throws InterruptedException {
        shouldLoginUsingValidCredentials();

        driver.get("http://digitalnizena.cz/church/FindDepositSlip.php");

        Thread.sleep(1000);

        List<WebElement> depositRows = driver.findElements(By.cssSelector("#depositsTable tbody tr"));

        for (WebElement row : depositRows) {
            row.click();
        }

//
        WebElement deleteButton = driver.findElement(By.cssSelector("#deleteSelectedRows"));
        deleteButton.click();
//
//        //TODO compare this WebElement confirmDeleteButton = driver.findElement(By.cssSelector(".modal-dialog .btn-primary"));
        WebElement confirmDeleteButton = driver.findElement(By.cssSelector(".modal-content > .modal-footer .btn-primary"));
        WebDriverWait wait = new WebDriverWait(driver, 1);
        wait.until(ExpectedConditions.visibilityOf(confirmDeleteButton));
        confirmDeleteButton.click();

//        // actually the application behaves incorrect => when delete all rows, Delete button should be disabled
//        // we have our test correct, so it good that test fails!
        Assert.assertFalse(deleteButton.isEnabled());
    }

    public void loadingExample() {
        driver.get("http://digitalnizena.cz/priklad/loading1.html");

        WebElement button = driver.findElement(By.cssSelector("#my-button"));

        WebDriverWait wait = new WebDriverWait(driver, 12);
        wait.until(ExpectedConditions.visibilityOf(button));

        // here in code, we are 100% sure, that button is visible
    }


}
