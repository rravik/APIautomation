package userManagement;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class BuilderPatternImplementation {

    private RequestSpecification requestSpecification;
    private ResponseSpecification responseSpecification;

    @Test
    public void testRestAssuredNormalApproach() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        given()
                .contentType(ContentType.JSON)
                .queryParam("userId","1")
                .when()
                .get("/posts")
                .then()
                .spec(setResponseSpecificationBuilder(200, "application/json"));
    }

    //Readability, Maintainability, Reusability
    public RequestSpecification getRequestSpecificationBuilder(String contentType, String queryParam) {
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri("https://jsonplaceholder.typicode.com")
                .setContentType(contentType)
                .addQueryParam(queryParam)
                .build();
        return requestSpecification;
    }

    @Test
    public void testRestAssuredBuilderPattern() {
        requestSpecification = getRequestSpecificationBuilder("application/json", "1");
        given()
                .spec(requestSpecification)
                .when()
                .get("/posts")
                .then()
                .spec(setResponseSpecificationBuilder(200, "application/json"));
    }

    public ResponseSpecification setResponseSpecificationBuilder(int statusCode, String contentType) {
        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .expectContentType(contentType)
                .build();
        return responseSpecification;

    }

}
