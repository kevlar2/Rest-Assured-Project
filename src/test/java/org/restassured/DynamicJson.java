package org.restassured;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.restassured.files.payload;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DynamicJson {
    @Test(dataProvider = "BooksData")
    public void addBook(String aisle, String isbn){

        // Dynamically build json payload with external data inputs

        RestAssured.baseURI ="http://216.10.245.166";
        String response = given().header("Content-Type", "application/json")
                .body(payload.addBook(aisle, isbn)).when().post("/Library/Addbook.php")
                .then().assertThat().statusCode(200)
                .extract().response().asString();

        JsonPath js =ReusableMethods.rawToJson(response);
        String id =js.get("ID");
        System.out.println(id);
    }

    @DataProvider(name="BooksData")
    public Object[][] getData(){
        // array = Collection of elements
        // multidimensional array = collection of arrays
        return new Object[][] {{"sdhdsds", "66337"}, {"jsdkdsjhdjh", "2848448"}, {"sdhhsj", "383838"}};
    }

}
