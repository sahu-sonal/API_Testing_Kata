package com.booking.stepdefinitions;

import com.api.URL;
import com.payloads.Credentials;
import io.cucumber.java.en.Given;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static com.api.access.getCredentials;
import static io.restassured.RestAssured.given;

public class Token {


    Credentials payload = new Credentials();
    private Response res;
    String token_created;


    @Given("I have a valid token")
    public String validToken(){
        payload.setUsername(getCredentials().getString("username"));
        payload.setPassword(getCredentials().getString("password"));


        res = given().contentType(ContentType.JSON)
                .accept("*/*")
                .body(payload.toString())
                .log().all()
                .post(URL.post_url_to_create_token);

        token_created = res.jsonPath().get("token").toString();
       return token_created;

    }
}
