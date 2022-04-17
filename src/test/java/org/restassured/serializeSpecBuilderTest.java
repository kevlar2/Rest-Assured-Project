package org.restassured;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.restassured.pojo.AddPlace;
import org.restassured.pojo.Location;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class serializeSpecBuilderTest {
    public static void main(String[] args) {


        // Creating objects using the add place POJO class

        AddPlace addPlace = new AddPlace();
        addPlace.setAccuracy(50);
        addPlace.setAddress("29, side layout, cohen 09");
        addPlace.setLanguage("French-IN");
        addPlace.setPhone_number("(+91) 983 893 3937");
        addPlace.setWebsite("http://google.com");
        addPlace.setName("KO Academy");

        // Creating an object for list class
        List<String> myTypesList = new ArrayList<String>();
        myTypesList.add("shoe park");
        myTypesList.add("shop");
        addPlace.setTypes(myTypesList);

        // Creating a location class object for add-place class to use
        Location location=new Location();
        location.setLat(-38.383494);
        location.setLng(33.427362);
        
        //Pass the value add-place object
        addPlace.setLocation(location);

        // Example for Serialisation

        // Add place to google API
        // Using Request and Response spec Builder
        RequestSpecification requestSpecification =new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com/")
                .addQueryParam("key", "qaclick123")
                .setContentType(ContentType.JSON).build();

        ResponseSpecification responseSpecification =new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON).build();

        Response res = given().spec(requestSpecification).body(addPlace)
                .when().post("/maps/api/place/add/json")
                .then().spec(responseSpecification).extract().response();

        String responseString = res.asString();
        System.out.println(responseString);

    }
}
