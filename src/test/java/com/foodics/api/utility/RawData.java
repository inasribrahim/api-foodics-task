package com.foodics.api.utility;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class RawData {

    public static JsonPath rawToJson(Response response)
    {
        String respon= response.asString();
        JsonPath jsonPath =new JsonPath(respon);
            return jsonPath;
    }
}
