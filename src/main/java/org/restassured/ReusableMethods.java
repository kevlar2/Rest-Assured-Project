package org.restassured;
import io.restassured.path.json.JsonPath;
public class ReusableMethods {

    public static JsonPath rawToJson(String response){
        JsonPath jp =new JsonPath(response); // for parsing json

        return jp;
    }

}
