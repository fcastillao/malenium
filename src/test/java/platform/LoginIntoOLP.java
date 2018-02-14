package platform;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import logic.config.DefaultVariables;
import logic.utils.BrowserUtility;

public class LoginIntoOLP {

    private BrowserUtility browserUtility;

    @When("^i navigate to olp page$")
    public void iNavigateToOlpPage() throws Throwable {
        browserUtility = new BrowserUtility();

        browserUtility.goToURL(DefaultVariables.OLP_STAGING);
        browserUtility.clickOnElement("//*[@id=\"button-sign-in\"]");
    }

    @Then("^fill the login inputs$")
    public void fillTheLoginInputs() throws Throwable {
        browserUtility.inputTextInFieldById(DefaultVariables.OLP_EMAIL, "sign-in-email");
        browserUtility.inputTextInFieldById(DefaultVariables.OLP_REALM, "realm-input");
        browserUtility.inputTextInFieldById(DefaultVariables.OLP_PASSWORD, "sign-in-password-encrypted");
    }
}
