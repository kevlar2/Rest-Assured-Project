package org.restassured;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.restassured.pojo.AddPlace;
import org.restassured.pojo.Location;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class serializeTest {
    public static void main(String[] args) {

        RestAssured.baseURI ="https://rahulshettyacademy.com/";

        // Creating objects using the add place POJO class

        AddPlace ap = new AddPlace();
        ap.setAccuracy(50);
        ap.setAddress("29, side layout, cohen 09");
        ap.setLanguage("French-IN");
        ap.setPhone_number("(+91) 983 893 3937");
        ap.setWebsite("http://google.com");
        ap.setName("KO Academy");

        // Creating an object for list class
        List<String> myTypesList = new ArrayList<String>();
        myTypesList.add("shoe park");
        myTypesList.add("shop");
        ap.setTypes(myTypesList);

        // Creating a location class object for add-place class to use
        Location location=new Location();
        location.setLat(-38.383494);
        location.setLng(33.427362);

        //Pass the value add-place object
        ap.setLocation(location);

        // Example for Serialisation

        // Add place to google API

        Response res = given().queryParam("key", "qaclick123").body(ap)
                .when().post("/maps/api/place/add/json")
                .then().assertThat().statusCode(200).extract().response();

        String responseString = res.asString();
        System.out.println(responseString);

    }
}
