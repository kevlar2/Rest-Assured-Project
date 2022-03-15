package org.restassured;

import io.restassured.path.json.JsonPath;
import org.restassured.files.payload;


public class complexJsonParse {
    public static void main(String[] args) {

        JsonPath js =new JsonPath(payload.CoursePrice());

        // Print No of courses returned by API
        int count = js.getInt("courses.size()");

        System.out.println("Course Count: " + count);


        // Print Purchase Amount

        int totalPurchaseAmount = js.getInt("dashboard.purchaseAmount");

        System.out.println("\nTotal Purchase Amount: " + totalPurchaseAmount);


        // Print Title of the first course

        String title_of_first_course = js.getString("courses.title[0]");

        System.out.println("\nTitle of the first course: " + title_of_first_course);

        // Print All course titles and their respective Prices

        System.out.println();

        for (int i=0; i <count; i++){
            // Gets individual courses
           String course = js.getString("courses["+i+"].title");
           // Gets the price linked to the individual courses
           int coursePrice = js.getInt("courses["+i+"].price");

            System.out.println("Title: " + course + "\nPrice: " + coursePrice + "\n");
        }

        // Print no of copies sold by RPA Course

        for (int i=0; i <count; i++){

            String course = js.getString("courses["+i+"].title");

            if (course.equalsIgnoreCase("APPIUM")){

                // Return Copies of course sold
                int courseCopies = js.getInt("courses["+i+"].copies");

                System.out.println("Title: " + course + " : " + "No of sold copies: " + courseCopies);
                break;
            }
        }

        // Verify if Sum of all Course prices matches with Purchase Amount


    }

}
