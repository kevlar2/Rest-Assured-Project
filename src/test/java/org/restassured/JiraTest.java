package org.restassured;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;

import static io.restassured.RestAssured.given;

public class JiraTest {
    public static void main(String[] args) {

        String BASE_URL_MBPRO = "http://192.168.8.105:8081";
        String BASE_URL_PC = "http://localhost:8081/";

        RestAssured.baseURI = BASE_URL_PC;

        // Login Scenario
        SessionFilter sessionFilter = new SessionFilter(); // SessionFilter class is used to store session information after Authentication

        String response = given().header("Content-Type", "application/json").body("{\n" +
                "  \"username\": \"korukele84\",\n" +
                "  \"password\": \"Tester@11\"\n" +
                "}").log().all().filter(sessionFilter).when().post("rest/auth/1/session")
                .then().log().all().extract().response().asString();

        // Add comment scenario
        given().pathParam("id", "10007").log().all().header("Content-Type", "application/json")
                .body("{\n" +
                "    \"body\": \"Comment made from automation rest API test MPBPro.\",\n" +
                "    \"visibility\": {\n" +
                "        \"type\": \"role\",\n" +
                "        \"value\": \"Administrators\"\n" +
                "    }\n" +
                "}").filter(sessionFilter).when().post("rest/api/2/issue/{id}/comment")
                .then().log().all().assertThat().statusCode(201);
    }
}
