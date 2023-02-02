package org.restassured;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class LibraryAPIExample {
    public static void main(String[] args) {

        // Call API to Add Book
        RequestSpecification req = new RequestSpecBuilder().setBaseUri("http://216.10.245.166")
                .setContentType(ContentType.JSON).build();

        RequestSpecification addBook = given().header("Content-Type","application/json").log().all().spec(req)
                .body("{\n" +
                        "  \"name\": \"Learn Appium Automation with Java\",\n" +
                        "  \"isbn\": \"bc887d\",\n" +
                        "  \"aisle\": \"1000\",\n" +
                        "  \"author\": \"Kevin O\"\n" +
                        "}");

        String addBookResponse = addBook.when().post("/Library/Addbook.php")
                .then().assertThat().statusCode(200).log().all().extract().response().asString();

        JsonPath js= new JsonPath(addBookResponse);
        String msg = js.get("Msg");
        String id = js.get("ID");
        System.out.println(msg);
        System.out.println(id);


    }



}
