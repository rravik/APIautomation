package userManagement;

import core.BaseTest;
import core.StatusCode;
import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.JsonReader;
import utils.PropertiesReader;
import utils.SoftAssertionUtil;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static utils.JsonReader.getJsonArray;

public class getUsers extends BaseTest {

    String serverAddress = PropertiesReader.getPropertyValue("config.properties", "server");
    String endpoint = JsonReader.getTestData("endpoint");
    String url = serverAddress + endpoint;
    SoftAssert softAssertion = SoftAssertionUtil.getInstance();

    public getUsers() throws IOException, ParseException {
    }

    @Test(groups = "Smoke")
    public void getUserData() {

        given().
                when().get("https://reqres.in/api/users?page=2").
                then().assertThat().statusCode(200);
    }

    @Test(groups = "Regression")
    public void validateGetResponseBody() {
        //Set base URI for the API
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        //Send a get request and validate the response body using 'then'
        given()
                .when()
                .get("/todos/1")
                .then()
                .assertThat()
                .statusCode(200)
                .body(not(isEmptyString()))
                .body(not(emptyString()))
                .body("title", equalTo("delectus aut autem"))
                .body("userId", equalTo(1));


    }

    @Test(groups = "Production")
    public void validateHasItems() {
        //Set base URI for the API
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        Response response1 =
                given()
                        .when()
                        .get("/posts")
                        .then()
                        .extract()
                        .response();

        assertThat(response1.jsonPath().getList("title"), hasItems("eum et est occaecati", "qui est esse"));

    }

    @Test(groups = {"Production", "Regression", "SIT"})
    public void validateHasSize() {
        //Set base URI for the API
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        Response response1 =
                given()
                        .when()
                        .get("/comments")
                        .then()
                        .extract()
                        .response();

        assertThat(response1.jsonPath().getList(""), hasSize(500));
    }

    @Test(groups = "SIT")
    public void validateListcontainsInOrder() {

        //Set base URI for the API

        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        Response response1 =
                given()
                        .when()
                        .get("/comments?postId=1")
                        .then()
                        .extract()
                        .response();

        List<String> expectedEmails = Arrays.asList("Eliseo@gardner.biz", "Jayne_Kuhic@sydney.com", "Nikita@garfield.biz", "Lew@alysha.tv", "Hayden@althea.biz");

        //Use Hamcrest to check the response body contains specific all items in a specific order (this is to check all the emails)
        assertThat(response1.jsonPath().getList("email"), contains(expectedEmails.toArray(new String[0])));
        assertThat(response1.jsonPath().getList("email"), hasItems("Eliseo@gardner.biz"));

    }

    @Test
    public void testGetUsersWithQueryParameters() {
        RestAssured.baseURI = "https://reqres.in/api";

        Response response = given()
                //.pathParams("api", "users")
                .queryParam("page", "2")
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .extract().response();

        //Assert that response body contains 6 users
        response.then().body("data", hasSize(6));

        //Assert that the first user in the list has the correct values
        response.then().body("data[0].id", is(7));
        response.then().body("data[0].email", is("michael.lawson@reqres.in"));
        response.then().body("data[0].first_name", is("Michael"));
        response.then().body("data[0].last_name", is("Lawson"));


    }

    @Test
    public void testQueryParams() {
        //https://jsonplaceholder.typicode.com/comments?postId=1&id=2
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        Response response = given()
                //.pathParams("api", "users")
                .queryParam("postId", 1)
                .queryParam("id", 2)
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .extract().response();
    }

    @Test(description = "Validate the status code for GET users endpoint")
    public void testPathParams() {

        //https://ergast.com/api/f1/2006/circuits.json
        String raceSeasonValue = "2017";
        Response resp = given().pathParam("raceSeason", raceSeasonValue)
                .when().get("https://ergast.com/api/f1/{raceSeason}/circuits.json");

        int actualStatusCode = resp.statusCode();
        assertEquals(actualStatusCode, 200);
        System.out.println(resp.body().asString());
    }

    @Test
    public void testCreateUserWithFormParam() {
        //RestAssured.baseURI = "https://reqres.in/api";

        Response response = given()
                //.pathParams("api", "users")
                .contentType("application/x-www-form-urlencoded")
                .formParam("name", "John Doe")
                .formParam("job", "Developer")
                //.queryParam("page", "2")
                .when()
                .post("https://reqres.in/users")
                .then()
                .statusCode(201)
                .extract().response();

    }

    @Test
    public void toGetUserListWithHeader() {
        RestAssured.baseURI = "https://reqres.in/api";

        Response response = given()
                //.pathParams("api", "users")
                .header("Content-Type", "application/json")
                .queryParam("page", "2")
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .extract().response();
    }

    @Test
    public void testTwoHeadersWithMap() {
        RestAssured.baseURI = "https://reqres.in/api";

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");


        Response response = given()
                //.pathParams("api", "users")
                .headers(headers)
                .queryParam("page", "2")
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .extract().response();
    }

    @Test
    public void testGetAllHeadersFromResponse() {
        RestAssured.baseURI = "https://reqres.in/api";

        Response response = given()
                //.pathParams("api", "users")
                .queryParam("page", "2")
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .extract().response();

        Headers headers = response.getHeaders();

        for (Header h : headers) {
            if (h.getName().equalsIgnoreCase("Server")) {
                System.out.println(h.getName() + " : " + h.getValue());
                Assert.assertEquals(h.getValue(), "cloudflare");
                System.out.println("testGetAllHeadersFromResponse executed successfully");
            }

        }
    }

    @Test
    public void testUseCookies() {

        RestAssured.baseURI = "https://reqres.in/api";
        Cookie cookies = new Cookie.Builder("cookieKey1", "cookieValue1")
                .setComment("using cookie key")
                .build();

        Response response = given()
                //.pathParams("api", "users")
                .cookie(cookies)
                .queryParam("page", "2")
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .extract().response();

        System.out.println("testUseCookies executed successfully");


    }

    @Test
    public void testFetchCookies() {
        RestAssured.baseURI = "https://reqres.in/api";

        Response response = given()
                //.pathParams("api", "users")
                .queryParam("page", "2")
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .extract().response();

        Map<String, String> cookies = response.getCookies();
        assertThat(cookies, hasKey("JSESSIONID"));

        Cookies cookies1 = response.getDetailedCookies();
        cookies1.getValue("server");

    }

    @Test
    public void testBasicAuth() {

        Response response = given()
                //.pathParams("api", "users")
                //.queryParam("page", "2")
                .auth().basic("postman", "password")
                .when()
                .get("https://postman-echo.com/basic-auth")
                .then()
                .statusCode(200)
                .extract().response();

    }

    @Test
    public void testDigestAuth() {

        Response response = given()
                //.pathParams("api", "users")
                //.queryParam("page", "2")
                .auth().digest("postman", "password")
                .when()
                .get("https://postman-echo.com/digest-auth")
                .then()
                .statusCode(StatusCode.SUCCESS.code)
                .extract().response();

    }

    @Test
    public void testDeleteRequest() {
        Response resp = given().delete("https://reqres.in/api/users/2");
        assertEquals(resp.getStatusCode(), StatusCode.NO_CONTENT.code);

    }




    @Test
    public void validateWithTestDataFromPropertiesFile() {


        String url = PropertiesReader.getPropertyValue("config.properties", "server");
        System.out.println(url);

        Response response = given()
                //.pathParams("api", "users")
                .contentType("application/x-www-form-urlencoded")
                .formParam("name", "John Doe")
                .formParam("job", "Developer")
                //.queryParam("page", "2")
                .when()
                .post(url)
                .then()
                .statusCode(415)
                .extract().response();
    }

    @Test
    public void validateFromProperties_Testdata() throws IOException, ParseException {


        Response response = given()
                //.pathParams("api", "users")
                .contentType("application/x-www-form-urlencoded")
                .formParam("name", "John Doe")
                .formParam("job", "Developer")
                //.queryParam("page", "2")
                .when()
                .post(url)
                .then()
                .statusCode(415)
                .extract().response();
    }

    @Test
    public void validateSoftAssertion() {
        RestAssured.baseURI = "https://reqres.in/api";

        Response response = given()
                //.pathParams("api", "users")
                .header("Content-Type", "application/json")
                .queryParam("page", "2")
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .extract().response();

        int actualStatusCode = response.statusCode();
        softAssertion.assertEquals(actualStatusCode, StatusCode.NO_CONTENT.code, "Status code is not 200");
        softAssertion.assertAll();
    }

    @DataProvider(name = "testdata")
    public Object[][] testData() {

        return new Object[][] {
                {"1", "Ravi"},
                {"2", "Karthick"},
                {"3", "John"}
        };
    }

    @Test(dataProvider = "testdata" )
    @Parameters({"id", "name"})
    public void testEndPoint(String id, String name) {
        given()
                .queryParam("id", id)
                .queryParam("name", name)
                .when()
                .get("https://reqres.in/api/users")
                .then().statusCode(200);
    }

    @Test
    public void testforFetchJsonData() throws IOException, ParseException {
        Object  t = JsonReader.getJsonArrayData("languages", 1);
        System.out.println(t);
    }

    @Test
    public void testforGetContactInfo() throws IOException, ParseException {
        JsonReader.getJsonArrayData("languages", 1);
        JSONArray jsonArray = getJsonArray("contact");
        Iterator<String> iterator = jsonArray.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

}