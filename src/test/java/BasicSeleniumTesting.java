import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;


public class BasicSeleniumTesting {

    private static WebDriver driver;

    @BeforeClass
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @Before
    public void setupTest() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void getToGoogle(){
        driver.get("http://www.google.com");
        String title = driver.getTitle();
        Assert.assertEquals("Google", title);
    }

    @Test
    public void getToOlpDemoWithCookieAndCheckNotebook14() throws InterruptedException {
        //go first so cookie can be set
        driver.get("https://zepp-dev.analytics.in.here.com/#/");

        //manually set cookie
        Cookie cookie1 = new Cookie(HereCookie.getInstance().getName(), HereCookie.getInstance().getValue());
        driver.manage().addCookie(cookie1);

        //go again with the aut cookie
        driver.get("https://zepp-dev.analytics.in.here.com/#/");

        //get 14 notebook and click it
        WebElement element = driver.findElement(By.xpath("//*[@id=\"notebook-names\"]/div/li[14]/div/div/a[1]"));
        element.click();

        //wait for page to load and get title
        driver.findElement(By.xpath("//*[@id=\"20171212-135643_27552144_container\"]"));
        String title = driver.getTitle();

        //finally the fkin assert
        Assert.assertEquals("Build a Graph", title);


    }

}
