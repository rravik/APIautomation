package userManagement;

import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class JsonSchemaValidation {

    @Test
    public void getUserData() {

        given().
                when().get("https://reqres.in/api/users?page=2").
                then().assertThat().statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchema(""));
    }

}
