package org.restassured;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.restassured.pojo.LoginRequest;
import org.restassured.pojo.LoginResponse;

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

    }

}
