package steps;

//import cucumber.api.PendingException;
//import cucumber.api.java.en.And;
//import cucumber.api.java.en.Given;
//import cucumber.api.java.en.Then;
//import io.restassured.http.ContentType;
import io.restassured.response.Response;
//import org.apache.http.HttpStatus;
//import org.junit.Assert;


import static io.restassured.RestAssured.*;

public class APIServices {
    public Response getMethod(String url) {
        Response response=when().get(url);
        return response;
    }
}
