package com.foodics.api.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodics.api.endpoints.APIEndPoint;
import com.foodics.api.utility.HttpStatus;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class BaseApi {
    private RequestSpecBuilder requestSpecBuilder;
    private RequestSpecification requestSpecification;
    private Response apiResponse;

    private HttpStatus expectedStatusCode = HttpStatus.OK;
    private String expectedResponseContentType;

    public static BaseApi init() {
        return new BaseApi();
    }

    public BaseApi(){
        initializeRequestSpec();
    }

    private void initializeRequestSpec() {

        EncoderConfig encoderconfig = new EncoderConfig();
        requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri(APIEndPoint.FOODICS_URL);
            requestSpecBuilder.setConfig(RestAssured.config().encoderConfig(encoderconfig.appendDefaultContentCharsetToContentTypeIfUndefined(false)));
    }

    public BaseApi path(String path) {
        requestSpecBuilder.setBasePath(path);
        return this;
    }

    public BaseApi pathParam(String key, String value) {
        requestSpecBuilder.addPathParam(key, value);
        return this;
    }

    public BaseApi queryParam(String key, String value) {
        requestSpecBuilder.addQueryParam(key, value);
        return this;
    }

    public BaseApi contentType(ContentType contentType) {
        requestSpecBuilder.setContentType(contentType);
        return this;
    }

    public BaseApi headers(Map<String, String> headers) {
        requestSpecBuilder.addHeaders(headers);
        return this;
    }

    public BaseApi header(String headerName , String token){
        requestSpecBuilder.addHeader(headerName,token);
        return this;
    }
    public BaseApi body(Object body) {
        requestSpecBuilder.setBody(body);
        return this;
    }
    public BaseApi expectedStatusCode(HttpStatus expectedStatusCode) {
        this.expectedStatusCode = expectedStatusCode;
        return this;
    }

    public BaseApi expectedResponseContentType(ContentType contentType) {
        this.expectedResponseContentType = contentType.toString();
        return this;
    }

    public BaseApi expectedResponseContentType(String contentType) {
        this.expectedResponseContentType = contentType;
        return this;
    }

    public BaseApi put() {
        requestSpecification = requestSpecBuilder.build();
        apiResponse =
                given()
                        .log().all()
                        .filter(new APIResponseFilter())
                        .spec(requestSpecification)
                        .when()
                        .put()
                        .then()
                        .assertThat()
                        .statusCode(expectedStatusCode.getCode())
                        .contentType(expectedResponseContentType)
                        .and()
                        .extract()
                        .response();

        return this;
    }

    public BaseApi delete() {
        requestSpecification = requestSpecBuilder.build();
        apiResponse =
                given()
                        .log().all()
                        .filter(new APIResponseFilter())
                        .spec(requestSpecification)
                        .when()
                        .delete()
                        .then()
                        .assertThat()
                        .statusCode(expectedStatusCode.getCode())
                        .contentType(expectedResponseContentType)
                        .and()
                        .extract()
                        .response();

        return this;
    }

    public BaseApi post() {
        requestSpecification = requestSpecBuilder.build();
        apiResponse =
                given()
                        .log().all()
                        .filter(new APIResponseFilter())
                        .spec(requestSpecification)
                        .when()
                        .post()
                        .then()
                        .assertThat()
                        .statusCode(expectedStatusCode.getCode())
                        .contentType(expectedResponseContentType)
                        .and()
                        .extract()
                        .response();

        return this;
    }
    public BaseApi get() {
        requestSpecification = requestSpecBuilder.build();
        apiResponse =
                given()
                        .log().all()
                        .filter(new APIResponseFilter())
                        .spec(requestSpecification)
                        .when()
                        .get()
                        .then()
                        .assertThat()
                        .statusCode(expectedStatusCode.getCode())
                        .contentType(expectedResponseContentType)
                        .and()
                        .extract()
                        .response();
        return this;
    }
    public Response response() {
        return apiResponse;
    }
    public String getApiResponseAsString() {
        return apiResponse.asString();
    }
    public <T> T responseToPojo(Class<T> type) throws JsonProcessingException {
            return new ObjectMapper().enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY).readValue(getApiResponseAsString(), type);
    }

}
