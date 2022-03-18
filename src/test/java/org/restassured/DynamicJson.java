package org.restassured;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.restassured.files.payload;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DynamicJson {
    @Test
    public void addBook(){
        // Dynamically build json payload with external data inputs

        RestAssured.baseURI ="http://216.10.245.166";
        String response = given().header("Content-Type", "application/json")
                .body(payload.Addbook()).when().post("/Library/Addbook.php")
                .then().assertThat().statusCode(200)
                .extract().response().asString();

        JsonPath js =ReusableMethods.rawToJson(response);
        String id =js.get("ID");
        System.out.println(id);
    }


}
