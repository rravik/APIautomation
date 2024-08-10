package userManagement;

import core.BaseTest;
import core.StatusCode;
import io.restassured.response.Response;
import org.apache.commons.io.IOUtils;
import org.testng.annotations.Test;
import pojo.CityRequest;
import pojo.PostRequestBody;
import utils.ExtentReport;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.testng.AssertJUnit.assertEquals;

public class postUsers extends BaseTest {

    private static FileInputStream fileInputStreamMethod(String requestBodyFileName) {
        FileInputStream fileInputStream ;
        try {
           fileInputStream =  new FileInputStream(System.getProperty("user.dir")+"/resources/TestData/"+requestBodyFileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return fileInputStream;
    }

    @Test
    public void validatePostWithJsonFile() throws IOException {
        Response response = given()
                .header("Content-Type", "application/json")
                .body(IOUtils.toString(fileInputStreamMethod("postRequestBody.json")))
                .when()
                .post("https://reqres.in/api/users");
        assertEquals(response.getStatusCode(), StatusCode.CREATED.code);
        System.out.println("validatePostwithJsonFile executed successfully");
        System.out.println(response.getBody().asString());
    }

    @Test
    public void validatePostWithPojo() throws IOException {
        ExtentReport.extentlog =
                ExtentReport.extentReports
                        .startTest("validatePostWithPojo", "Validate Pojo post request");

        PostRequestBody postRequest = new PostRequestBody();
        postRequest.setName("Ravi");
        postRequest.setJob("Testing");
        Response response = given()
                .header("Content-Type", "application/json")
                .body(postRequest)
                .when()
                .post("https://reqres.in/api/users");
        assertEquals(response.getStatusCode(), StatusCode.CREATED.code);
        System.out.println("validatePostwithPojo executed successfully");
        System.out.println(response.getBody().asString());
    }

    @Test
    public void validatePostWithPojoList() throws IOException {
        ExtentReport.extentlog =
                ExtentReport.extentReports
                        .startTest("validatePostWithPojoList", "Validate Pojo List post request");
        List<String> listOfLanguages = new ArrayList<>();
        listOfLanguages.add("Java");
        listOfLanguages.add("Python");
        PostRequestBody postRequest = new PostRequestBody();
        postRequest.setName("Ravi");
        postRequest.setJob("Testing");
        postRequest.setLanguages(listOfLanguages);
        Response response = given()
                .header("Content-Type", "application/json")
                .body(postRequest)
                .when()
                .post("https://reqres.in/api/users");
        assertEquals(response.getStatusCode(), StatusCode.CREATED.code);
        System.out.println("validatePostwithPojoList executed successfully");
        System.out.println(response.getBody().asString());
    }

    @Test
    public void validatePostWithPojoListComplex() throws IOException {
        ExtentReport.extentlog =
                ExtentReport.extentReports
                        .startTest("validatePostWithPojoList", "Validate Pojo List post request");
        List<String> listOfLanguages = new ArrayList<>();
        listOfLanguages.add("Java");
        listOfLanguages.add("Python");

        CityRequest cityRequest1 = new CityRequest();
        cityRequest1.setName("Bangalore");
        cityRequest1.setTemperature("30");

        CityRequest cityRequest2 = new CityRequest();
        cityRequest2.setName("Delhi");
        cityRequest2.setTemperature("40");

        List<CityRequest> cityRequests = new ArrayList<>();
        cityRequests.add(cityRequest1);
        cityRequests.add(cityRequest2);

        PostRequestBody postRequest = new PostRequestBody();
        postRequest.setName("Ravi");
        postRequest.setJob("Testing");
        postRequest.setLanguages(listOfLanguages);
        postRequest.setCityRequestBody(cityRequests);


        Response response = given()
                .header("Content-Type", "application/json")
                .body(postRequest)
                .when()
                .post("https://reqres.in/api/users");
        assertEquals(response.getStatusCode(), StatusCode.CREATED.code);
        System.out.println("validatePostwithPojoList executed successfully");
        System.out.println(response.getBody().asString());
    }

    @Test
    public void validatePathWithResponsePojo() {
        String job = "Morpheus";
        ExtentReport.extentlog =
                ExtentReport.extentReports
                        .startTest("validatePostWithPojoList", "Validate Pojo List post request");
        PostRequestBody patchRequest = new PostRequestBody();
        patchRequest.setJob(job);

        Response response = given()
                .header("Content-Type", "application/json")
                .body(patchRequest)
                .when()
                .patch("https://reqres.in/api/users/2");
        PostRequestBody responseBody = response.as(PostRequestBody.class);
        System.out.println(responseBody.getJob());

        System.out.println("validatePatchwithPojo executed successfully");
        System.out.println(response.getBody().asString());
    }

    @Test
    public void validatePostWithPojoListComplex1() throws IOException {
        ExtentReport.extentlog =
                ExtentReport.extentReports
                        .startTest("validatePostWithPojoList", "Validate Pojo List post request");
        List<String> listOfLanguages = new ArrayList<>();
        listOfLanguages.add("Java");
        listOfLanguages.add("Python");

        CityRequest cityRequest1 = new CityRequest();
        cityRequest1.setName("Bangalore");
        cityRequest1.setTemperature("30");

        CityRequest cityRequest2 = new CityRequest();
        cityRequest2.setName("Delhi");
        cityRequest2.setTemperature("40");

        List<CityRequest> cityRequests = new ArrayList<>();
        cityRequests.add(cityRequest1);
        cityRequests.add(cityRequest2);

        PostRequestBody postRequest = new PostRequestBody();
        postRequest.setName("Ravi");
        postRequest.setJob("Testing");
        postRequest.setLanguages(listOfLanguages);
        postRequest.setCityRequestBody(cityRequests);


        Response response = given()
                .header("Content-Type", "application/json")
                .body(postRequest)
                .when()
                .post("https://reqres.in/api/users");
        PostRequestBody responseBody = response.as(PostRequestBody.class);

        System.out.println(responseBody.getCityRequestBody());
        System.out.println(responseBody.getLanguages());
        assertEquals(response.getStatusCode(), StatusCode.CREATED.code);
        System.out.println("validatePostwithPojoList executed successfully");
        System.out.println(response.getBody().asString());
    }
}
