package notebooks;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import logic.utils.BrowserUtility;
import logic.config.DefaultVariables;
import org.junit.Assert;

public class BasicFunctionality {
    private BrowserUtility browserUtility;
    private String testedNotebook;

    @Given("^that i am logged in zeppelin$")
    public void thatIAmLoggedInZeppelin() throws Throwable {
        browserUtility = new BrowserUtility();

        browserUtility.goToURL(DefaultVariables.ZEPPELIN_URL);
        browserUtility.setCookie(DefaultVariables.AUTHORIZATION_COOKIE, DefaultVariables.AUTHORIZATION_COOKIE_VALUE);
        browserUtility.goToURL(DefaultVariables.ZEPPELIN_URL);
    }

    @When("^i open the notebook called (.*)$")
    public void iOpenTheNotebookCalledBuildAGraph(String notebookName) throws Throwable {
        testedNotebook = notebookName;
        browserUtility.clickOnNotebook(notebookName);
    }
;
    @Then("^the notebook loads correctly$")
    public void theNotebookLoadsCorrectly() throws Throwable {
        browserUtility.waitForPageToLoad();
        Assert.assertEquals(testedNotebook, browserUtility.getPageTitle());
    }

    @Then("^close the driver$")
    public void closeTheDriver() throws Throwable {
        browserUtility.tearDown();
    }
}
