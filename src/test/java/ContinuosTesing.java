import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;
import io.restassured.response.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;
import static java.lang.Integer.compare;
import static java.lang.Integer.parseInt;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.*;

public class ContinuosTesing {

//    BookStore

    private Integer bookingId = 0;
    private String authToken = "";

    public JSONObject transformJsonFileToJsonObject(String path) throws IOException {
        //converting json to Map
        byte[] mapData = Files.readAllBytes(Paths.get(path));
        Map<String,String> map = new HashMap<String, String>();

        ObjectMapper objectMapper = new ObjectMapper();
        map = objectMapper.readValue(mapData, HashMap.class);
        System.out.println("Map is: "+map);
        JSONObject request = new JSONObject(map);
        System.out.println(request);
        return request;
    }

    @Test
    public void getAuthorisationToken() throws IOException {
        JSONObject request = transformJsonFileToJsonObject("C:\\Users\\bmatoianu\\IdeaProjects\\RestAssuredProject\\src\\test\\resources\\credentials.json");
        Response response = given().
                header("Content-Type", "application/json").
//                contentType(ContentType.JSON).accept(ContentType.JSON).
                body(request.toJSONString()).
        when().
                post("https://restful-booker.herokuapp.com/auth").
        then().
                statusCode(200).
                body("size()",is(1) ).
//                body("$.*", equalTo("token")).
                log().all().
                extract().response();
        authToken = response.jsonPath().getString("token");
        System.out.println(authToken);
    }

    public void getAPICall(String host){
        Response response = RestAssured.get(host);


    }
    @Test
    public void getBookingIds(){
        given().
                header("Content-Type","application/json").
//                param().
        get("https://restful-booker.herokuapp.com/booking").
                then().
                statusCode(200).
                body("size()", is(3939)).
                log().all();
//        get a status id from here
    }

    @Test
    public void getBooking(){
        given().
                header("Content-Type","application/json").
//                param().
        get("https://restful-booker.herokuapp.com/booking/33").
                then().
                statusCode(200).
                body("size()",is(6) ).
                body("bookingdates.size()",is(2) ).
                body("firstname",equalTo("John")).
                body("lastname",equalTo("Smith")).
                body("totalprice",equalTo(111)).
                body("depositpaid",equalTo(true)).
                body("bookingdates.checkin",equalTo("2018-01-01")).
                body("bookingdates.checkout",equalTo("2019-01-01")).
                body("additionalneeds",equalTo("Breakfast")).

                log().all();
    }


    @Test
    public void createBooking() throws IOException {
        String path = "C:\\Users\\bmatoianu\\IdeaProjects\\RestAssuredProject\\src\\test\\resources\\bookingCreate.json";
        JSONObject request = transformJsonFileToJsonObject(path);

        Response response =      given().
                header("Content-Type", "application/json").
                accept("application/json").
                body(request.toJSONString()).
                when().
                post("https://restful-booker.herokuapp.com/booking").
                then().
                statusCode(200).
//                body("size()",is(1) ).
//                body("$.*", equalTo("token")).
//        log().all().
          extract().response();
        System.out.println(response.asString());
        System.out.println(response.jsonPath().getString("bookingid"));
        bookingId = parseInt(response.jsonPath().getString("bookingid"));
        System.out.println(bookingId);

//        int bookingId = Integer.parseInt(response.jsonPath().getString("bookingid"));
//        return bookingId;
        Map<String, Object> booking = response.jsonPath().getMap("booking");
        System.out.println(booking);

        byte[] mapData = Files.readAllBytes(Paths.get(path));
        Map<String,String> map = new HashMap<String, String>();

        ObjectMapper objectMapper = new ObjectMapper();
        map = objectMapper.readValue(mapData, HashMap.class);
        System.out.println(map);
        assertTrue(booking.equals(map));
        
    }

    @Test
    public void getCreatedBooking() throws IOException {
        createBooking();
        String path = "C:\\Users\\bmatoianu\\IdeaProjects\\RestAssuredProject\\src\\test\\resources\\bookingCreate.json";

        System.out.println(bookingId);
        Response response = given().
                header("Content-Type","application/json").
//                param().
        get("https://restful-booker.herokuapp.com/booking/" +bookingId).
                then().
                statusCode(200).
                log().all().extract().response();

        Map<String, Object> booking = response.jsonPath().getMap("$");
        System.out.println(booking);

        byte[] mapData = Files.readAllBytes(Paths.get(path));
        Map<String,String> map = new HashMap<String, String>();

        ObjectMapper objectMapper = new ObjectMapper();
        map = objectMapper.readValue(mapData, HashMap.class);
        System.out.println(map);
        assertTrue(booking.equals(map));
    }

    @Test
    public void updateBooking() throws IOException {
        getAuthorisationToken();
        String token = authToken;
        createBooking();
        System.out.println(authToken);
        String path = "C:\\Users\\bmatoianu\\IdeaProjects\\RestAssuredProject\\src\\test\\resources\\bookingCreate.json";
        JSONObject request = transformJsonFileToJsonObject(path);

        Response response =      given().
//                header("Authorization", "Basic " + authToken).
                header("Content-Type", "application/json").
                accept("application/json").
                cookie("token", token).
                body(request.toJSONString()).
                when().
                put("https://restful-booker.herokuapp.com/booking/" + bookingId).
                then().
                statusCode(200).
                log().all().

        extract().response();
//        System.out.println(response.asString());
//        System.out.println(response.jsonPath().getString("bookingid"));
//        bookingId = parseInt(response.jsonPath().getString("bookingid"));
//        System.out.println(bookingId);
//
////        int bookingId = Integer.parseInt(response.jsonPath().getString("bookingid"));
////        return bookingId;
        Map<String, Object> booking = response.jsonPath().getMap("$");
//        System.out.println(booking);
//
        byte[] mapData = Files.readAllBytes(Paths.get(path));
        Map<String,String> map = new HashMap<String, String>();

        ObjectMapper objectMapper = new ObjectMapper();
        map = objectMapper.readValue(mapData, HashMap.class);
//        System.out.println(map);
        assertTrue(booking.equals(map));

    }


    public void partialUpdateBooking() throws IOException {
        getAuthorisationToken();
        String token = authToken;
        createBooking();
        System.out.println(authToken);

        Map<String, Object> map = new HashMap<String, Object>();
        JSONObject request = new JSONObject(map);

        request.put("firstname" , "Gigi");
        request.put( "lastname" , "Bush");

        System.out.println(request);

        Response response =      given().
//                header("Authorization", "Basic " + authToken).
                header("Content-Type", "application/json").
                accept("application/json").
                cookie("token", token).
                body(request.toJSONString()).
                when().
                patch("https://restful-booker.herokuapp.com/booking/" + bookingId).
                then().
                statusCode(200).
                log().all().

                extract().response();

        Map<String, Object> booking = response.jsonPath().getMap("$");

        String path = "C:\\Users\\bmatoianu\\IdeaProjects\\RestAssuredProject\\src\\test\\resources\\bookingCreate.json";
        byte[] mapData = Files.readAllBytes(Paths.get(path));
        Map<String,String> updatedMap = new HashMap<String, String>();

        ObjectMapper objectMapper = new ObjectMapper();
        updatedMap = objectMapper.readValue(mapData, HashMap.class);
        System.out.println(updatedMap);

//        System.out.println(map);
        assertTrue(booking.equals(updatedMap));
    }
}
