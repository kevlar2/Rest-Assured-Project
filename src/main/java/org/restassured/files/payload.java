package org.restassured.files;

import java.util.HashMap;
import java.util.Map;

public class payload {

    public static final String EXPECTED_JIRA_COMMENT_TEXT = "Comment made from automation rest API test pc with an attachment.";


    public static final String  AUTHORISATION_CODE_URL = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AX4XfWiCzs7x2RWREHHDn4P8MZuiEcZk7Zck1OknyuWo_UY3xdIEU-pZabE9n4VvjtWfKg&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none";


    public static String AddPlace()
    {

        return "{\n" +
                "  \"location\": {\n" +
                "    \"lat\": -38.383494,\n" +
                "    \"lng\": 33.427362\n" +
                "  },\n" +
                "  \"accuracy\": 50,\n" +
                "  \"name\": \"Frontline house\",\n" +
                "  \"phone_number\": \"(+91) 983 893 3937\",\n" +
                "  \"address\": \"29, side layout, cohen 09\",\n" +
                "  \"types\": [\n" +
                "    \"shoe park\",\n" +
                "    \"shop\"\n" +
                "  ],\n" +
                "  \"website\": \"http://google.com\",\n" +
                "  \"language\": \"French-IN\"\n" +
                "}";
    }

    public static String CoursePrice()
    {

        return "{\n" +
                "    \"dashboard\": {\n" +
                "\t\"purchaseAmount\":1162,\n" +
                "\t\"website\":\"rahulshettyacademy.com\"\n" +
                "    },\n" +
                "    \"courses\": [\n" +
                "\t{\n" +
                "\t    \"title\":\"Selenium Python\",\n" +
                "\t    \"price\":50,\n" +
                "\t    \"copies\":6\n" +
                "\t},\n" +
                "\t{\n" +
                "\t    \"title\":\"Cypress\",\n" +
                "\t    \"price\":40,\n" +
                "\t    \"copies\":4\n" +
                "\t},\n" +
                "\t{\n" +
                "\t    \"title\":\"RPA\",\n" +
                "\t    \"price\":45,\n" +
                "\t    \"copies\":10\n" +
                "\t},\n" +
                "\t{\n" +
                "\t    \"title\":\"Appium\",\n" +
                "\t    \"price\":36,\n" +
                "\t    \"copies\":7\n" +
                "\t}\n" +
                "    ]\n" +
                "}";
    }

    public static String addBook(String aisle, String isbn)
    {

        return "{\n" +
                "  \"name\": \"Learn Appium Automation with Java\",\n" +
                "  \"isbn\": \""+isbn+"\",\n" +
                "  \"aisle\": \""+aisle+"\",\n" +
                "  \"author\": \"John foe\"\n" +
                "}";


    }

    public static String jiraUserAuthenticationCredentials()
    {

        return "{\n" +
                "    \"username\": \"korukele84\",\n" +
                "    \"password\": \"Tester@11\"\n" +
                "}";
    }

    public static String jiraUserComment()
    {

        return "{\n" +
                "    \"body\": \""+EXPECTED_JIRA_COMMENT_TEXT+"\",\n" +
                "    \"visibility\": {\n" +
                "        \"type\": \"role\",\n" +
                "        \"value\": \"Administrators\"\n" +
                "    }\n" +
                "}";
    }

    public static Map<String, String> getQueryParamData(String code)
    {
        Map<String, String> queryParamData = new HashMap<String, String>();
        queryParamData.put("code", code);
        queryParamData.put("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com");
        queryParamData.put("client_secret", "erZOWM9g3UtwNRj340YYaK_W");
        queryParamData.put("redirect_uri", "https://rahulshettyacademy.com/getCourse.php");
        queryParamData.put("grant_type", "authorization_code");

        return queryParamData;
    }
}
