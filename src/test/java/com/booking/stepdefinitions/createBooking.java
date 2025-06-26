package com.booking.stepdefinitions;
import com.api.URL;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.support.SupportLibrary;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.internal.RestAssuredResponseImpl;
import io.restassured.response.Response;
import org.junit.Assert;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;

public class createBooking {
    TestContext context;

    public createBooking(TestContext context){
        this.context=context;
    }
    Response res;


    @When("I make a reservation inserting all the information")
    public void i_make_a_reservation_inserting_all_the_information() throws JsonProcessingException {
        String InfoClientJSONformat = "";
        InfoClientJSONformat = SupportLibrary.convertJavaObjectToJson(ClientInformation.getAllClientInfo());
        res = given()
                .log().all()
                .body(InfoClientJSONformat)
                .get(URL.post_url_to_create_reservation);

    }
    @Then("I receive the reservation and the process ends successfully")
    public void I_receive_the_reservation_and_the_process_ends_successfully() {
        /*
        res.then().statusCode(200)
                .body("bookings[0].firstname", equalTo("Daniele"))
                .body("bookings[0].lastname", equalTo("Marino"))
                .log().all(); */

        String tokenValue = context.getToken();
        System.out.println("Token created __>> "+tokenValue);
        res= given()
                .queryParam("roomid",5)
                .cookie("token", tokenValue)
                .log().all()
                .get(URL.get_url_to_retrive_booking_room);

        String name = res.jsonPath().get("bookings[0].firstname").toString();
        Assert.assertEquals(name,"Daniele");
        String lastname = res.jsonPath().get("bookings[0].lastname").toString();
        Assert.assertEquals(lastname,"Marino");

    }

}
