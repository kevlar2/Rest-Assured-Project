package org.restassured;

import io.restassured.path.json.JsonPath;
import org.restassured.files.payload;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SumValidationTest {

    @Test
    public void sumOfCourses(){

        JsonPath js =new JsonPath(payload.CoursePrice());

        // Print No of courses returned by API
        int count = js.getInt("courses.size()");

        // Total of all price
        int totalAmount = 0;

        for(int i=0; i<count; i++){
            int coursePrice = js.getInt("courses["+i+"].price");
            int courseCopies = js.getInt("courses["+i+"].copies");
            int amount = coursePrice * courseCopies;
            System.out.println(amount);
            totalAmount+=amount;

        }

        System.out.println("Total Amount = £" + totalAmount);

        int purchaseAmount = js.getInt("dashboard.purchaseAmount");
        Assert.assertEquals(totalAmount,purchaseAmount,"totalAmount not equal to purchaseAmount");
    }
}
