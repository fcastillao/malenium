import io.github.bonigarcia.wdm.WebDriverManager;
import logic.config.DefaultVariables;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.fail;

public class BasicSeleniumTesting {

    private static WebDriver driver;
    private String cookieName;
    private String cookieValue;
    private String zeppelinUrl;

    @BeforeClass
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @Before
    public void setupTest() throws ConfigurationException {
        driver = new ChromeDriver();
        //this is how much time should we wait for a element to be loaded
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //load configurations
        PropertiesConfiguration config = new PropertiesConfiguration();
        config.load("application.properties");

        cookieName = config.getString("cookieName");
        cookieValue = config.getString("cookieValue");
        zeppelinUrl = config.getString("zeppelinUrl");

        if (cookieName.isEmpty() || cookieValue.isEmpty()|| zeppelinUrl.isEmpty())throw  new ConfigurationException("parameter values setted incorrectly");
    }

    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    //@Test
    public void getToGoogle(){
        driver.get("http://www.google.com");
        String title = driver.getTitle();
        Assert.assertEquals("Google", title);
    }

    //@Test
    public void getToOlpClickNotebookAndWaitForLoad() throws InterruptedException {
        //go first so cookie can be set
        driver.get(zeppelinUrl);

        //manually set cookie
        Cookie cookie1 = new Cookie(cookieName, cookieValue);
        driver.manage().addCookie(cookie1);

        //go again with the aut cookie
        driver.get(zeppelinUrl);

        //get 14 notebook and click it
        //WebElement element = driver.findElement(By.xpath("//*[@id=\"notebook-names\"]/div/li[]/div/div/a[1]"));

        //search for a link with text
        //get the notebook called Build a Graph and click it
        WebElement element = driver.findElement(By.xpath("//a[contains(., 'Build a Graph')]"));
        element.click();

        //wait for page to load and get title
        driver.findElement(By.xpath("//*[@id=\"20171212-135643_27552144_container\"]"));
        String title = driver.getTitle();

        Assert.assertEquals("Build a Graph", title);


    }

    @Test
    public void logIntoOLP(){
        WebElement element = null;
        try {
            driver.get(DefaultVariables.OLP_STAGING);

        element = driver.findElement(By.xpath("//*[@id=\"button-sign-in\"]"));

        } catch (NoSuchElementException e){
            System.out.print("staging olp is down");
            fail();
        }
        element.click();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);

        /*
        WebElement emailBox = driver.findElement(By.xpath("//*[@id=\"sign-in-email\"]"));
        WebElement realmBox = driver.findElement(By.xpath("//*[@id=\"realm-input\"]"));
        WebElement passwordBox = driver.findElement(By.xpath("//*[@id=\"sign-in-password-encrypted\"]"));

        emailBox.sendKeys("some text");
        realmBox.sendKeys("sometest 2");
        passwordBox.sendKeys("halp");

        */
        try{
            driver.findElement(By.className("sign-in-email"));
        }catch (NoSuchElementException e){
            System.out.print("not by className");
        }

        try{
            driver.findElement(By.id("sign-in-email"));
        }catch (NoSuchElementException e){
            System.out.print("not by id");
        }

        try{
            driver.findElement(By.name("sign-in-email"));
        }catch (NoSuchElementException e){
            System.out.print("not by name");
        }

        try{
            driver.findElement(By.tagName("sign-in-email"));
        }catch (NoSuchElementException e){
            System.out.print("not by tagName");
        }


    }

}
