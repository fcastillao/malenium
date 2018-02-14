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

    public void init() {
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

    public void waitForPageToLoad() {
        driver.findElement(By.className("ace_content"));
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
}
