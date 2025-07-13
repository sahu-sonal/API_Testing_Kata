package com.booking.utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.Map;

public class ApiUtils extends TestSuiteSetup{

    public static Response post(String baseURI, String postUrl, Map<String, String> headers, Map<String, String> cookies,
                                Object bodyString) {
        Response resObj = null;
        if(baseURI !=null && !baseURI.equals("")) {
            RestAssured.baseURI = baseURI;
        }

        RequestSpecification requestSpecificationObj = RestAssured.given().log().all();

        if (headers != null && !headers.isEmpty()) {
            requestSpecificationObj = requestSpecificationObj.headers(headers);
        }

        if (cookies != null && !cookies.isEmpty()) {
            requestSpecificationObj = requestSpecificationObj.cookies(cookies);
        }

        if(bodyString != null && !bodyString.equals("")) {
            requestSpecificationObj = requestSpecificationObj.body(bodyString);
        }

        resObj = requestSpecificationObj.log().all().post(postUrl).then().log().all().extract().response();

        return resObj;
    }

    public static Response put(String baseURI, String putUrl, Map<String, String> headers, Map<String, String> cookies,
                                Object bodyString) {
        Response resObj = null;
        RestAssured.baseURI = baseURI;
        RequestSpecification requestSpecificationObj = RestAssured.given();
        resObj = requestSpecificationObj.headers(headers).cookies(cookies).body(bodyString).put(putUrl).then().log().all().extract().response();
        return resObj;
    }

    public static Response get(String baseURI, String getUrl, Map<String, Integer> params, Map<String, String> cookies, Map<String, String> headers) {
        Response resObj = null;
        RestAssured.baseURI = baseURI;
        RequestSpecification requestSpecificationObj = RestAssured.given();
        resObj = requestSpecificationObj.headers(headers).params(params).cookies(cookies).when().log().all().get(getUrl).then().log().all().extract().response();
        return resObj;
    }

    public static Response get(String baseURI, String getUrl, Map<String, String> cookies, Map<String, String> headers) {
        Response resObj = null;
        RestAssured.baseURI = baseURI;
        RequestSpecification requestSpecificationObj = RestAssured.given();
        resObj = requestSpecificationObj.headers(headers).cookies(cookies).when().log().all().delete(getUrl).then().log().all().extract().response();
        return resObj;
    }
}
