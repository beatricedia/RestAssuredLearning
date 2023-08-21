import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class APITestsGET {
    @Test
    void test1(){
        Response response = RestAssured.get("https://petstore.swagger.io/v2/user/logout");

        System.out.printf("reponse: " + response.asString() + "\n");
        System.out.printf( "status code: " + response.getStatusCode() + "\n");
        System.out.printf("body: " + response.getBody().asString() + "\n");
        System.out.printf("time taker: " + response.getTime() + "\n");
        System.out.printf("header: " + response.getHeader("content-type") + "\n");

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 400);
    }

    @Test
    void test2(){
        given().
                get("https://demoqa.com/BookStore/v1/Books").
        then().
                statusCode(200).
                log().all();
    }

    @Test
    void test3(){
        given().
                get("https://demoqa.com/BookStore/v1/Books").
        then().
                statusCode(200).
                body("books.subTitle[2]", equalTo("Harnessing the Power of the Web")).
                // I want to check the values of publisher
                body("books.author", hasItems("Richard E. Silverman", "Addy Osmani", "Glenn Block et al.","Axel Rauschmayer")).
                log().all();
    }

    @Test
    void test4(){
        given().
                header("Content-Type","application/json").
//                param().
                get("https://demoqa.com/BookStore/v1/Books").
        then().
                statusCode(200).
                body("books.subTitle[2]", equalTo("Harnessing the Power of the Web")).
                // I want to check the values of publisher
                        body("books.author", hasItems("Richard E. Silverman", "Addy Osmani", "Glenn Block et al.","Axel Rauschmayer")).

                log().all();
    }

    @Test
    void test5(){
        given().
                header("Content-Type","application/json").
//                param().
        get("https://demoqa.com/BookStore/v1/Books").
                then().
                statusCode(200).
                body("books.subTitle[2]", equalTo("Harnessing the Power of the Web")).
                // I want to check the values of publisher
                        body("books.author", hasItems("Richard E. Silverman", "Addy Osmani", "Glenn Block et al.","Axel Rauschmayer")).
                log().all();
    }
}
