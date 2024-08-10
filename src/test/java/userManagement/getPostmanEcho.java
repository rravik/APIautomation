package userManagement;

import core.BaseTest;
import core.StatusCode;
import io.restassured.response.Response;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ExtentReport;
import utils.JsonReader;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class getPostmanEcho extends BaseTest {

    @Test
    public void validateWithTestDataFromJson() throws IOException, ParseException {

        ExtentReport.extentlog =
                ExtentReport.extentReports
                        .startTest("validateWithTestDataFromJson", "Validate 200 status code");


        String userName = JsonReader.getTestData("username");
        String password = JsonReader.getTestData("password");

        System.out.println(userName);
        System.out.println(password);
        Response response = given()
                //.pathParams("api", "users")
                //.queryParam("page", "2")
                .auth().digest(userName, password)
                .when()
                .get("https://postman-echo.com/digest-auth")
                .then()
                .statusCode(StatusCode.NO_CONTENT.code)
                .extract().response();

    }

    @Test
    public void verifyStatusCodeDelete() {

        ExtentReport.extentlog =
                ExtentReport.extentReports
                        .startTest("verigyStatusCodeDelete", "Validate 204 status code for delete method");


        Response response = given()
                .delete("https://reqres.in/api/users/2");
        Assert.assertEquals(response.getStatusCode(), StatusCode.NO_CONTENT.code);
        System.out.println("verifyStatusCodeDelete executed successfully");
    }
}
