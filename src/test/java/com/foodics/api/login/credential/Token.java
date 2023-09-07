package com.foodics.api.login.credential;


import com.foodics.api.endpoints.APIEndPoint;
import com.foodics.api.properties.PropertiesHandler;
import com.foodics.api.utility.RawData;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class Token {

    protected final static String token_value = "token";

    public static String getTokenKey() throws IOException {
            CredentialsPayload credentialsPayload = CredentialsBuilder.builder()
                    .setEmail(PropertiesHandler.setAPIConfig().getProperty("email"))
                    .setPassword(PropertiesHandler.setAPIConfig().getProperty("password"))
                    .setToken(PropertiesHandler.setAPIConfig().getProperty("token"))
                    .perform();

            RestAssured.baseURI= APIEndPoint.FOODICS_URL;
            Response res= given().
                    header("Content-Type", ContentType.JSON)
                    .body(credentialsPayload)
                    .when()
                    .post(APIEndPoint.LOGIN)
                    .then().statusCode(200)
                    .extract()
                    .response();
            return RawData.rawToJson(res).get(token_value);
    }
}
