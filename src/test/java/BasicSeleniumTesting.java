import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;


public class BasicSeleniumTesting {

    private static WebDriver driver;
    private String cookieName;
    private String cookieValue;

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
        if (cookieName.equals("placeYourCookieNameHere"))throw new ConfigurationException("cookie not setted up");
        cookieValue = config.getString("cookieValue");
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

    @Test
    public void getToOlpClickNotebookAndWaitForLoad() throws InterruptedException {
        //go first so cookie can be set
        driver.get("https://zepp-dev.analytics.in.here.com/#/");

        //manually set cookie
        Cookie cookie1 = new Cookie(cookieName, cookieValue);
        driver.manage().addCookie(cookie1);

        //go again with the aut cookie
        driver.get("https://zepp-dev.analytics.in.here.com/#/");

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

}
