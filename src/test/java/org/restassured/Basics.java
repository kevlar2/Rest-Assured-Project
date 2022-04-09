package org.restassured;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.hamcrest.Matcher;
import org.hamcrest.core.StringContains;
import org.restassured.files.payload;
import org.testng.Assert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class Basics {
    public static void main(String[] args) throws IOException {

        // Validate of add place API is working as expected

        // Rest Assured API follows the below principles
        // Given - All input Details
        // When - Submit the API - resource and http methods
        // Then - Validate the response
        // Convert the content of the file to String-> content of file can convert into Byte->Byte data to String

        RestAssured.baseURI = "https://rahulshettyacademy.com";
        Matcher<String> m = new StringContains("");

        // Add place
        String response =given().log().all().queryParam("key", "qaclick123").header("Content-type", "application/json")
                .body(new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "//src//main//java//org//restassured//files//addPlace.json"))))
                .when().post("maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"))
                .header("server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();

        // Payload for .body above payload.AddPlace()

                // Add place -> Update Place with New Address -> Get Place to validate if new address is present in response

        System.out.println("\n" + response);
        JsonPath js = ReusableMethods.rawToJson(response); // for parsing json
        String placeId =js.getString("place_id");
        System.out.println("\n" + placeId);

        // Update place
        String newAddress = "70 Summer Walk, Africa Nigeria";
        given().log().all().queryParam("key", "qaclick123")
                .header("Content-type", "application/json")
                .body("{\n" +
                        "\"place_id\":\""+placeId+"\",\n" +
                        "\"address\":\""+newAddress+"\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}\n")
                .when().put("maps/api/place/update/json")
                .then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));


        // Get Place
        String getPlaceResponse =given().log().all().queryParam("key", "qaclick123")
                .queryParam("place_id", placeId)
                .when().get("maps/api/place/get/json")
                .then().assertThat().log().all().statusCode(200).extract().response().asString();

        // You can also use .statusCode(200).body("address", equalTo(newAddress)); for simplicity

        JsonPath pR= ReusableMethods.rawToJson(getPlaceResponse); // for parsing json
        String placeResponseAddress =pR.getString("address");
        System.out.println("\n" + placeResponseAddress);

        Assert.assertEquals(newAddress,placeResponseAddress,"newAddress is not equal to placeResponseAddress");

    }
}
