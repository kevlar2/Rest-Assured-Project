package org.restassured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.Test;

public class LibraryAPIExampleTest {

    RequestSpecification req;
    String id;
    String authorName;
    ExcelDataDrivenTest dataDrivenTest;

    @Test()
    public void addbookapitest() throws IOException {
        // Get data from Excel file
        dataDrivenTest = new ExcelDataDrivenTest();
        ArrayList<String> data = dataDrivenTest.getData("add-book");

        // Now use that as part of the HashMap
        HashMap<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("name", data.get(1));
        jsonAsMap.put("isbn", data.get(2));
        jsonAsMap.put("aisle", data.get(3));
        jsonAsMap.put("author", data.get(4));

        // Call API to Add Book
        req = new RequestSpecBuilder().setBaseUri("http://216.10.245.166")
                .setContentType(ContentType.JSON).build();

        RequestSpecification addBook = given().header("Content-Type","application/json").log().all().spec(req)
                .body(jsonAsMap);

        String addBookResponse = addBook.when().post("/Library/Addbook.php")
                .then().assertThat().statusCode(200).log().all().extract().response().asString();

        JsonPath js=ReusableMethods.rawToJson(addBookResponse);
        id = js.get("ID");
        Assert.assertEquals(js.get("Msg"),"successfully added");
        Assert.assertEquals(js.get("ID"),"bc887d1000");
    }

    @Test(priority = 1)
    public void getbookbyid()
    {
        RequestSpecification getBookByIdReq = given().log().all().spec(req).queryParam("ID", id);

        String bookById =  getBookByIdReq.when().get("/Library/GetBook.php")
                .then().assertThat().statusCode(200).log().all().extract().response().asString();


        JsonPath js2 = ReusableMethods.rawToJson(bookById);
        authorName = js2.getString("author[0]");
        Assert.assertEquals(js2.getString("book_name[0]"),"Learn Appium Automation with Java");
        Assert.assertEquals(js2.getString("isbn[0]"),"bc887d");
        Assert.assertEquals(js2.getString("aisle[0]"),"1000");
        Assert.assertEquals(js2.getString("author[0]"),"Kevin O");
    }

    @Test(priority = 2)
    public void getbookbyauthor()
    {
        RequestSpecification getBookByAuthorReq = given().log().all().spec(req).queryParam("AuthorName", authorName);

        String bookByAuthor = getBookByAuthorReq.when().get("/Library/GetBook.php")
                .then().assertThat().statusCode(200).log().all().extract().response().asString();

        JsonPath bookByAuthorName = ReusableMethods.rawToJson(bookByAuthor);
        String bookNameByAuthor = bookByAuthorName.getString("book_name[0]");
        Assert.assertEquals(bookNameByAuthor, "Learn Appium Automation with Java");
    }

    @Test(priority = 3)
    public void deletebook() throws IOException {
        // Delete book
        // Get book ID from Excel
        ArrayList<String> bookId = dataDrivenTest.getData("delete-book");

        // Use its as part of the HashMap
        HashMap<String, Object> bookIdMap = new HashMap<>();
        bookIdMap.put("ID", bookId.get(1));

        RequestSpecification deleteBookReq = given().header("Content-Type","application/json")
                .log().all().spec(req).body(bookIdMap);

        String deleteBookResponse = deleteBookReq.when().delete("/Library/DeleteBook.php")
                .then().assertThat().statusCode(200).log().all().extract().response().asString();

        JsonPath dBr = ReusableMethods.rawToJson(deleteBookResponse);
        String deleterBookResponseMessage = dBr.get("msg");
        Assert.assertEquals(deleterBookResponseMessage, "book is successfully deleted");
    }
}
