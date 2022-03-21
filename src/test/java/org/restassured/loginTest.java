package org.restassured;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class loginTest {

        @Test(dataProvider = "TestData")
        public void logIn (String email, String password, boolean success)
        {
            System.out.println("Log In Credentials: " + "\n" +
                    "  Email = " + email + "\n" +
                    "  Password = " + password + "\n" +
                    "  Successful Log In = " + success + "\n" );
        }

        @Test(dataProvider = "TestData")
        public void validateLoginFeature(String email, String password, boolean success)
        {
            System.out.println("Start Login process: " + "\n" +
                    "  Email = " + email + "\n" +
                    "  Password = " + password);

            if(success)
            {
                System.out.println(email + ", Login is successfully." + "\n");
            }
            else
            {
                System.out.println(email + ", Login is unsuccessful" + "\n");
            }
        }

        @DataProvider(name="TestData")
        public Object [] [] logInData ()
        {
            Object [][] data = new Object [4][3];

            data [0][0] = "TestNG@Framework.com";		data [0][1] = "TestNG1234";		data [0][2] = true;
            data [1][0] = "Joe@Doe.com";			    data [1][1] = "DoeDoe34";		data [1][2] = false;
            data [2][0] = "Test@AutomationU.com";		data [2][1] = "TAU1234";		data [2][2] = true;
            data [3][0] = "kevin@huma.com";             data [3][1] = "Tester@11";      data [3][2] = false;

            return data;
        }

}
