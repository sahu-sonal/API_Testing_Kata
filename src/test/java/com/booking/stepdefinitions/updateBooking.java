package com.booking.stepdefinitions;
import com.api.URL;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.support.SupportLibrary;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;

import static io.restassured.RestAssured.given;

public class updateBooking {

    TestContext context;

    public updateBooking(TestContext context){
        this.context=context;
    }
    Response res;

    @Given("I want to update a booking for a holiday")
    public void user_has_access_to_endpoint() {

    }
    @When("I make a request to update first name and Last name")
    public void user_makes_a_request_to_update_first_name_and_last_name() throws JsonProcessingException {
        String updateInfoClientJSONformat = "";
        updateInfoClientJSONformat = SupportLibrary.convertJavaObjectToJson(ClientInformation.updateClientNameAndLastName());
        res = given()
                .log().all()
                .pathParam("roomid",5)
                .body(updateInfoClientJSONformat)
                .put(URL.put_url_to_update_booking_reservation);
    }
    @Then("I should get the success status code")
    public void user_should_get_the_success_status_code() {

        String tokenValue = context.getToken();
        System.out.println("Token created after update__>> "+tokenValue);
        res= given()
                .queryParam("roomid",5)
                .cookie("token", tokenValue)
                .get(URL.get_url_to_retrive_booking_room);

        String name = res.jsonPath().get("bookings[0].firstname").toString();
        //Assert.assertEquals(name,"Jimmy");
        System.out.println(name);
        String lastname = res.jsonPath().get("bookings[0].lastname").toString();
        //Assert.assertEquals(lastname,"Love");
        System.out.println(lastname);
    }

}
