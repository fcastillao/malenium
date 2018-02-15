package logic.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class BrowserUtility {
    private ChromeDriver driver;

    public BrowserUtility() {
        WebDriverManager.chromedriver().setup();
        init();
    }

    private void init() {
        driver = new ChromeDriver();
        //this is how much time should we wait for a element to be loaded
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public void goToURL(String zeppelinUrl) {
        driver.get(zeppelinUrl);
    }

    public void setCookie(String name, String value) {
        driver.manage().addCookie(new Cookie(name, value));
    }

    public void clickOnNotebook(String notebookName) {
        WebElement element = driver.findElement(By.xpath("//a[contains(., '" + notebookName + "')]"));
        element.click();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    /**
     * making this search for a nonexistent class will force the waiting,
     * also catching the exception to prevent the test stop
     */
    public void waitForPageToLoad() {

        try {
            driver.findElement(By.className("some nonexistent class name"));
        } catch (Exception ignored) {

        }
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }


    public void clickOnElement(String xpath) {
        WebElement element = driver.findElement(By.xpath(xpath));
        element.click();
    }

    public void inputTextInFieldById(String text, String elementClass) {
        WebElement element = driver.findElement(By.id(elementClass));
        element.sendKeys(text);
    }

    public void driverSwitch(String frameId) {
        driver.switchTo().frame(frameId);
    }

    public void driverSwitchDefault() {
        driver.switchTo().defaultContent();
    }

    public void findElementByXpath(String elementXpath) {
        driver.findElement(By.xpath(elementXpath));
    }

    public void findElementById(String elementId) {
        driver.findElement(By.id(elementId));
    }
}
