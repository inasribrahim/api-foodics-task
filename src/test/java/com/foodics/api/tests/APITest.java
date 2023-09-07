package com.foodics.api.tests;

import com.foodics.api.common.BaseApi;
import com.foodics.api.endpoints.APIEndPoint;
import com.foodics.api.generate_data.GenerateData;
import com.foodics.api.credential.CredentialsBuilder;
import com.foodics.api.credential.CredentialsPayload;
import com.foodics.api.credential.Token;
import com.foodics.api.properties.PropertiesHandler;
import com.foodics.api.utility.HttpStatus;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;


import java.io.IOException;
import java.lang.reflect.Method;



public class APITest extends BaseTest {

    private static String tokenKey ;

    @Test(priority = 1)
    public void verifyThatTheUserAbleLoginWithValidCredentialsInFoodicsThroughAPI() throws IOException {
        tokenKey = Token.getTokenKey();
        Assert.assertFalse(tokenKey.isEmpty());
    }



    @Test
    public void verifyThatTheUserNotAbleLoginWithInValidCredentialsInFoodicsThroughAPI() throws IOException {
        CredentialsPayload credentialsPayload = CredentialsBuilder.builder()
                .setEmail(GenerateData.getEmailAddress())
                .setPassword(GenerateData.generatePassword())
                .setToken(GenerateData.getToken())
                .perform();

         BaseApi.init()
                .path(APIEndPoint.LOGIN)
                .contentType(ContentType.JSON)
                .body(credentialsPayload)
                .expectedStatusCode(HttpStatus.FOUND)
                .expectedResponseContentType(ContentType.HTML)
                .post();
    }

    @Test
    public void verifyThatTheUserNotAbleToLoginWithValidCredentialsButInTokenInFoodicsThroughAPI() throws IOException {
        CredentialsPayload credentialsPayload = CredentialsBuilder.builder()
                .setEmail(PropertiesHandler.setAPIConfig().getProperty("email"))
                .setPassword(PropertiesHandler.setAPIConfig().getProperty("password"))
                .setToken(GenerateData.getToken())
                .perform();

        BaseApi.init()
                .path(APIEndPoint.LOGIN)
                .contentType(ContentType.JSON)
                .body(credentialsPayload)
                .expectedStatusCode(HttpStatus.FOUND)
                .expectedResponseContentType(ContentType.HTML)
                .post();
    }

    @Test
    public void verifyThatTheAPISystemKnowUserFromTokenInFoodicsThroughAPI(Method method) throws IOException {

        String token = "{\"token\": \""+Token.getTokenKey()+"\"}";

        BaseApi.init()
                .path(APIEndPoint.WHOAMI)
                .contentType(ContentType.JSON)
                .body(token)
                .expectedStatusCode(HttpStatus.OK)
                .expectedResponseContentType(ContentType.fromContentType("text/html"))
                .get();
    }

    /*
         Negative Scenario
        Verify that delete method not allowed when user not authorized to use it in system or not have privillage
     */
    @Test
    public void verifyThatUserNoAbleToDeleteYourAccountUsingTokenInFoodicsThroughAPI(Method method) throws IOException {

        String token = "{\"token\": \""+Token.getTokenKey()+"\"}";

        BaseApi.init()
                .path(APIEndPoint.WHOAMI)
                .contentType(ContentType.JSON)
                .body(token)
                .expectedStatusCode(HttpStatus.METHOD_NOT_ALLOWED)
                .expectedResponseContentType(ContentType.fromContentType("text/html"))
                .delete();
    }

}
