package com.booking.stepdefinitions;


import com.api.URL;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.payloads.Credentials;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.util.JSONPObject;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Assert;


import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

public class GetBookings {

    Token token;

    public GetBookings(Token token){
        this.token=token;
    }

    Credentials payload = new Credentials();
    Token payloadToken = new Token();
    private Response res;
    String token_created;
    Response res_validate;

    @Given("I want to see the list of rooms")
    public void i_want_to_see_the_list_of_rooms() throws JsonProcessingException {
        token_created = token.validToken();
        System.out.println("########### "+token_created);
        given()
                .queryParam("roomid",2)
                .cookie("token=", token_created)
                .log().all();

    }


    @When("I want to read the bookings for a room")
    public void i_want_to_read_the_bookings_for_a_room() throws JsonProcessingException {
        token_created = token.validToken();
        res = when()
                .get(URL.get_url_to_retrive_booking_room);

    }
    @Then("I should receive all existing bookings")
    public void i_should_receive_all_existing_bookings() {
        res.then().statusCode(200)
                .log().all();

        String name = res.jsonPath().get("bookings[0].firstname").toString();
        Assert.assertEquals(name,"Erica");
        String lastname = res.jsonPath().get("bookings[0].lastname").toString();
        Assert.assertEquals(lastname,"Bowthorpe");
        String checkout = res.jsonPath().get("bookings[0].checkout").toString();
        Assert.assertEquals(checkout,"2026-02-04");

        JSONObject jo = new JSONObject();
        String n = jo.getJSONArray("bookings").getJSONObject(2).get("firstname").toString();
        Assert.assertEquals(n,"James");
    }

}
