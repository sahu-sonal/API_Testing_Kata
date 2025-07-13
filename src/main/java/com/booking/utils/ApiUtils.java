package com.booking.utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import com.booking.models.ConfigReader;

import java.util.List;
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

    public static Response put(String baseURI, String postUrl, Map<String, String> headers, Map<String, String> cookies,
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

        resObj = requestSpecificationObj.log().all().put(postUrl).then().log().all().extract().response();

        return resObj;
    }
}
