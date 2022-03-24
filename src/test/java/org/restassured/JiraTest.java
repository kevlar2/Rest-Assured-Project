package org.restassured;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import org.restassured.files.payload;
import org.testng.Assert;

import java.io.File;

import static io.restassured.RestAssured.given;

public class JiraTest {
    public static void main(String[] args) {

        String BASE_URL_MBPRO = "http://192.168.8.105:8081";
        String BASE_URL_PC = "http://localhost:8081/";

        RestAssured.baseURI = BASE_URL_MBPRO;

        // Login Scenario
        SessionFilter sessionFilter = new SessionFilter(); // SessionFilter class is used to store session information after Authentication

        String response = given().header("Content-Type", "application/json")
                .body(payload.jiraUserAuthenticationCredentials()).log().all().filter(sessionFilter)
                .when().post("rest/auth/1/session")
                .then().log().all().extract().response().asString();

        // Add comment scenario
        String addCommentResponse = given().pathParam("id", "10007").log().all().header("Content-Type", "application/json")
                .body(payload.jiraUserComment()).filter(sessionFilter).when().post("rest/api/2/issue/{id}/comment")
                .then().log().all().assertThat().statusCode(201).extract().response().asString();

        // Extracting the comment ID
        JsonPath js= new JsonPath(addCommentResponse);
        String commentId= js.getString("id");
        System.out.println("\nComment ID: " + commentId);

        // Add Attachments
        // key point - Using multipart file class
        given().header("X-Atlassian-Token", "no-check").filter(sessionFilter).pathParam("id","10007")
                .multiPart("file", new File("jira-test-file.txt")).when()
                .post("/rest/api/2/issue/{id}/attachments").then().log().all().assertThat().statusCode(200);
        
        // Get issue details and verify if added comment and attachment exists using Get issue API
        // pathParam re-routes you to the sub-resources
        // queryParam helps you filter your resources
        // keY point: Parsing complex Json and limiting Json response through query parameters
         String issueDetails = given().filter(sessionFilter).pathParam("id","10007")
                 .queryParam("fields", "comment")
                 .log().all().when().get("rest/api/2/issue/{id}/")
                 .then().log().all().extract().response().asString();

        System.out.println("\n" + issueDetails);

        // Validating extracted comment Ids
        JsonPath jsCommentdetails = new JsonPath(issueDetails);
        int commentsCount = jsCommentdetails.get("fields.comment.comments.size()");
        for (int i=0; i<commentsCount; i++)
        {
            String commentIdIssue = jsCommentdetails.get("fields.comment.comments["+i+"].id").toString();
            if (commentIdIssue.equalsIgnoreCase(commentId))
            {
                String actualComment = jsCommentdetails.get("fields.comment.comments["+i+"].body").toString();
                System.out.println("\n" + actualComment);
                Assert.assertEquals(actualComment, payload.EXPECTED_JIRA_COMMENT_TEXT,
                        "Actual Comment not equal to Expected Jira Comment Text");
            }
        }



    }
}
