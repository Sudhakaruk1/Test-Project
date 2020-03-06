package steps;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import io.restassured.response.Response;
import org.junit.Assert;

import utilities.GenericFunctions;
import utilities.PetStatus;

import java.util.List;
import java.util.Map;

public class PetScenarioSteps {

    private static Response response;
    private static int count;

    @Given("^The Pet Store API service is available$")
    public void the_Swagger_Petstore_API_service_is_available() throws Throwable {
        GenericFunctions.getAPIServiceStatus();
    }

    @Then("^I set the appropriate URL for the service$")
    public void ascertainTheAppropriatePetStatusURL() throws Exception {
        String temp = GenericFunctions.getServiceURL();
    }

    @Given("^I request PetStore application for the status data below$")
    public void iRequestPetStoreApplicationForTheStatusDataBelow(DataTable dt) throws Exception {
        List<Map<String, String>> list = dt.asMaps(String.class, String.class);
        String petQuery = list.get(0).get("Status");
        PetStatus petStatus = PetStatus.getByName(petQuery.toUpperCase());
        GenericFunctions.getPetsByStatus(petStatus);
    }

    @Then("^I verify the response for the Status , Pet name and Pet count for the data below$")
    public void iVerifyTheResponseForTheStatusPetNameAndPetCountForTheDataBelow(DataTable dt) {
        List<Map<String, String>> list = dt.asMaps(String.class, String.class);
        String petStatus = list.get(0).get("Status");
        String petName = list.get(0).get("PetName");
        String expCount = list.get(0).get("PetCount");
        int cnt = Integer.parseInt(expCount);
        int actCount = GenericFunctions.validatePETResponse(petName, petStatus, expCount);
        Assert.assertEquals("**** Pet count not matched...", cnt, actCount);
    }

}
