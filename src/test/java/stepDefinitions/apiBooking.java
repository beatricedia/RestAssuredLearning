package stepDefinitions;

import io.cucumber.java.en.Given;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class apiBooking {


//    @Given("that I authorize with username {string} and password {string}")
//    public void thatIAuthorizeWithUsernameAndPassword(String username, String password) {
//
//        Map<String, Object> map = new HashMap<String, Object>();
//        JSONObject request = new JSONObject(map);
//
//        request.put("username", username);
//        request.put("password", password);
//
//        System.out.println(request);
//
//        given().
//                header("Content-Type", "application/json").
////                contentType(ContentType.JSON).accept(ContentType.JSON).
//        body(request.toJSONString()).
//                when().
//                post("https://restful-booker.herokuapp.com/auth").
//                then().
//                statusCode(200).log().all();
//
//    }
@Given("I perform GET operation for {string}")
public void i_perform_GET_operation_for(String string) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
}
    @Given("that I authorize with username {string} and password {string}")
    public void thatIAuthorizeWithUsernameAndPassword(String arg0, String arg1) {
        throw new io.cucumber.java.PendingException();
    }
}
