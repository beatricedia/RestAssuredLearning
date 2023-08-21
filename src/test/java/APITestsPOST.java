import io.restassured.http.ContentType;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class APITestsPOST {

    @Test
    public void test1() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "Rayen");
        map.put("job", "dog");

        System.out.println(map);

        JSONObject request = new JSONObject(map);

        System.out.println(request);
        System.out.println(request.toJSONString());

        request.put("name", "Bobo");
        request.put("job", "bear");

        System.out.println(request);

        given().
                header("Content-Type", "application/json").
                contentType(ContentType.JSON).accept(ContentType.JSON).
                body(request.toJSONString()).
                when().
                post("https://reqres.in/api/users").
                then().
                statusCode(201).log().all();
    }


    @Test
    public void test2() {
        Map<String, Object> map = new HashMap<String, Object>();
        JSONObject request = new JSONObject(map);

        request.put("name", "Rayen");
        request.put("job", "dog");

        System.out.println(request);

        given().
                header("Content-Type", "application/json").
                contentType(ContentType.JSON).accept(ContentType.JSON).
                body(request.toJSONString()).
                when().
                put("https://reqres.in/api/users/2").
                then().
                statusCode(200).log().all();
    }


    @Test
    public void test3() {
        Map<String, Object> map = new HashMap<String, Object>();
        JSONObject request = new JSONObject(map);

        request.put("name", "Rayen");
        request.put("job", "dog");

        System.out.println(request);

        given().
                header("Content-Type", "application/json").
                contentType(ContentType.JSON).accept(ContentType.JSON).
                body(request.toJSONString()).
                when().
                patch("https://reqres.in/api/users/2").
                then().
                statusCode(200).
                log().all();

//        given().get("https://reqres.in/api/users?page=2").then().
//                statusCode(200).log().all();
    }

    @Test
    public void test4() {


        when().
                delete("https://reqres.in/api/users/2").
                then().
                statusCode(204).
                log().all();

//        given().get("https://reqres.in/api/users?page=2").then().
//                statusCode(200).log().all();
    }

    @Test
    public void test5() {
        Map<String, Object> map = new HashMap<String, Object>();
        JSONObject request = new JSONObject(map);

        request.put("username", "admin");
        request.put("password", "password123");

        System.out.println(request);

        given().
                header("Content-Type", "application/json").
//                contentType(ContentType.JSON).accept(ContentType.JSON).
                body(request.toJSONString()).
                when().
                post("https://restful-booker.herokuapp.com/auth").
                then().
                statusCode(200).log().all();

//        https://restful-booker.herokuapp.com/apidoc/index.html#api-Booking-CreateBooking
    }
}
