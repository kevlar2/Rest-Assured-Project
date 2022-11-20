package org.restassured;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.restassured.pojo.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class ECommerceAPITest {
    public static void main(String[] args) {

        // Login API

        RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .setContentType(ContentType.JSON).build();

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserEmail("Briana.Parisian24@gmail.com");
        loginRequest.setUserPassword("Tester@11");

        RequestSpecification reqLogin = given().log().all().spec(req).body(loginRequest);
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
        RequestSpecification createOrderBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("Authorization", loginResponse.getToken())
                .setContentType(ContentType.JSON)
                .build();

        // Set Oder Details
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setCountry("United Arab Emirates");
        orderDetail.setProductOrderedId(productId);

        // Create a list using the OrderDetail object
        List<OrderDetail> orderDetailsList = new ArrayList<>();
        orderDetailsList.add(orderDetail);

        Orders orders = new Orders();
        orders.setOrders(orderDetailsList);


        RequestSpecification createOrderReq = given().log().all().spec(createOrderBaseReq)
                .body(orders);

        String reqToCreateOrder = createOrderReq.when().post("/api/ecom/order/create-order").then().log().all()
                .extract().response()
                .asString();

        JsonPath js1 = new JsonPath(reqToCreateOrder);
        String orderId = js1.getString("orders[0]");

        System.out.println(reqToCreateOrder);
        System.out.println(orderId);

        // Get the order details API
        RequestSpecification getOrderDetailsBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("Authorization", loginResponse.getToken())
                .build();

        RequestSpecification getOrderDetailsReq = given().log().all().spec(getOrderDetailsBaseReq);

        OderDetailsResponse reqToGetOrderDetails = getOrderDetailsReq.when().get("/api/ecom/order/get-orders-details?id=" + orderId).then().log().all()
                .extract().response()
                .as(OderDetailsResponse.class);

        System.out.println(reqToGetOrderDetails.getData().getOrderById());
        System.out.println(reqToGetOrderDetails.getData().getOrderBy());
        System.out.println(reqToGetOrderDetails.getData().getProductOrderedId());
        System.out.println(reqToGetOrderDetails.getData().getProductName());
        System.out.println(reqToGetOrderDetails.getMessage());


        // Delete-product API

    }

}
