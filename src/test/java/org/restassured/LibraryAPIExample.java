package org.restassured;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class LibraryAPIExample {
    public static void main(String[] args) throws IOException {

        // Get data from Excel file
        ExcelDataDrivenTest dataDrivenTest = new ExcelDataDrivenTest();
        ArrayList<String> data = dataDrivenTest.getData("add-book");

        // Now use that as part of the HashMap
        HashMap<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("name", data.get(1));
        jsonAsMap.put("isbn", data.get(2));
        jsonAsMap.put("aisle", data.get(3));
        jsonAsMap.put("author", data.get(4));

        // Call API to Add Book
        RequestSpecification req = new RequestSpecBuilder().setBaseUri("http://216.10.245.166")
                .setContentType(ContentType.JSON).build();

        RequestSpecification addBook = given().header("Content-Type","application/json").log().all().spec(req)
                .body(jsonAsMap);

        String addBookResponse = addBook.when().post("/Library/Addbook.php")
                .then().assertThat().statusCode(200).log().all().extract().response().asString();

        JsonPath js=ReusableMethods.rawToJson(addBookResponse);
        String msg = js.get("Msg");
        String id = js.get("ID");
        System.out.println(msg);

         // Get Added Book By ID
        RequestSpecification getOrderDetailsReq = given().log().all().spec(req).queryParam("ID", id);

        String bookById =  getOrderDetailsReq.when().get("/Library/GetBook.php")
                .then().assertThat().statusCode(200).log().all().extract().response().asString();


        JsonPath js2 = ReusableMethods.rawToJson(bookById);
        String bookName = js2.getString("book_name[0]");
        String isbn = js2.getString("isbn[0]");
        String aisle = js2.getString("aisle[0]");
        String authorName = js2.getString("author[0]");

        // Get Added Book By Author name

        // Delete book



    }



}
