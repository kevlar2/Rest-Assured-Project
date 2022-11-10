package org.restassured;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.restassured.pojo.LoginRequest;
import org.restassured.pojo.LoginResponse;

import java.io.File;

import static io.restassured.RestAssured.given;

public class ECommerceAPITest {
    public static void main(String[] args) {

        // Login API

        RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .setContentType(ContentType.JSON).build();

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserEmail("Briana.Parisian24@gmail.com");
        loginRequest.setUserPassword("Tester@11");

        RequestSpecification reqLogin =given().log().all().spec(req).body(loginRequest);
        LoginResponse loginResponse = reqLogin.when().post("/api/ecom/auth/login").then().log().all()
                .extract().response()
                .as(LoginResponse.class);

        System.out.println(loginResponse.getToken());
        System.out.println(loginResponse.getUserId());
        System.out.println(loginResponse.getMessage());


        // Add Product API

        RequestSpecification addProductBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("Authorization", loginResponse.getToken())
                .build();

        RequestSpecification addProductReq = given().log().all().spec(addProductBaseReq)
                .param("productName", "qwerty")
                .param("productAddedBy", loginResponse.getUserId())
                .param("productCategory", "fashion")
                .param("productSubCategory", "shirts")
                .param("productPrice", "11500")
                .param("productDescription", "Addias Originals")
                .param("productFor", "men")
                .multiPart("productImage", new File(System.getProperty("user.dir") +
                        "//src//main//java//org//restassured//files//adidas.png"));



        String reqToAddProduct = addProductReq.when().post("/api/ecom/product/add-product").then().log().all()
                .extract().response()
                .asString();
        JsonPath js = new JsonPath(reqToAddProduct);

        String productId = js.getString("productId");

        // Create Order API

        // Get the order details API

        // Delete-product API

    }

}
