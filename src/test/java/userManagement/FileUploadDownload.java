package userManagement;

import com.fasterxml.jackson.databind.ser.Serializers;
import core.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class FileUploadDownload extends BaseTest {

    @Test
    public void fileUploadExample() {
        File file = new File("resources/TestData/upload.txt");
        Response response = given()
                .multiPart(file)
                .when()
                .post("https://example.com/upload");
        System.out.println(response.getStatusCode());

    }
}
