import io.github.bonigarcia.wdm.WebDriverManager;
import logic.config.DefaultVariables;
import org.apache.commons.configuration.ConfigurationException;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.fail;

public class BasicSeleniumTesting {

    private static WebDriver driver;

    @BeforeClass
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @Before
    public void setupTest() throws ConfigurationException {
        driver = new ChromeDriver();
        //this is how much time should we wait for a element to be loaded
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
    public void getToOlpClickNotebookAndWaitForLoad() throws InterruptedException {
        //go first so cookie can be set
        driver.get(DefaultVariables.ZEPPELIN_URL);

        //manually set cookie
        Cookie cookie1 = new Cookie(DefaultVariables.AUTHORIZATION_COOKIE, DefaultVariables.AUTHORIZATION_COOKIE_VALUE);
        driver.manage().addCookie(cookie1);

        //go again with the aut cookie
        driver.get(DefaultVariables.ZEPPELIN_URL);

        //get 14 notebook and click it
        //WebElement element = driver.findElement(By.xpath("//*[@id=\"notebook-names\"]/div/li[]/div/div/a[1]"));

        //search for a link with text
        //get the notebook called Build a Graph and click it
        try {
            WebElement element = driver.findElement(By.xpath("//a[contains(., 'Build a Graph')]"));
            element.click();
        } catch (NoSuchElementException e) {
            System.out.println("the Build a Graph notebook was not found in zeppelin");
            System.out.println("check that the cookie is valid");
            fail();
        }

        //wait for page to load and get title
        driver.findElement(By.xpath("//*[@id=\"20171212-135643_27552144_container\"]"));
        String title = driver.getTitle();

        Assert.assertEquals("Build a Graph", title);

    }

    @Test
    public void logIntoOLP(){
        WebElement signInButton;

        try {
            //staging olp has been unstable, lets use a try for this
            driver.get(DefaultVariables.OLP_STAGING);
            signInButton = driver.findElement(By.xpath("//*[@id=\"button-sign-in\"]"));
            signInButton.click();

        } catch (NoSuchElementException e){
            System.out.print("staging olp is down");
            fail();
        }

        //olp uses an iFrame, so we must switch to it
        driver.switchTo().frame("here-account-sdk");

        //find the respective text areas
        WebElement emailBox = driver.findElement(By.xpath("//*[@id=\"sign-in-email\"]"));
        WebElement realmBox = driver.findElement(By.xpath("//*[@id=\"realm-input\"]"));
        WebElement passwordBox = driver.findElement(By.xpath("//*[@id=\"sign-in-password-encrypted\"]"));

        //input the text
        emailBox.sendKeys(DefaultVariables.OLP_EMAIL);
        realmBox.sendKeys(DefaultVariables.OLP_REALM);
        passwordBox.sendKeys(DefaultVariables.OLP_PASSWORD);

        //actually and finally log in
        WebElement sigInButton = driver.findElement(By.xpath("//*[@id=\"signInBtn\"]"));
        sigInButton.click();

        //since previous iframe is dead, back to default
        driver.switchTo().defaultContent();

        //there is this acceptance button
        driver.findElement(By.id("testing-notice-agree"));

        //get the cookies obtained on login
        Set<Cookie> cookies = driver.manage().getCookies();
        boolean cookieGotten = false;

        //look for the access cookie, pass if found
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("here_access_st")) cookieGotten = true;
        }

        Assert.assertTrue(cookieGotten);
    }

}
