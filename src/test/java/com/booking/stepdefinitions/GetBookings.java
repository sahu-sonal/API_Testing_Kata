package com.booking.stepdefinitions;


import com.api.URL;
import com.payloads.Credentials;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;


import static io.restassured.RestAssured.given;

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
    public void adminReceiveToken(){




    }

   /* @And("The token is validated")
    public void the_token_is_validated() {
        System.out.println("to implement...2 ");
        payloadToken.setToken(token_created);
        res_validate = given().contentType(ContentType.JSON)

                .body(payloadToken.toString())
                .post(URL.post_url_to_validate_token);
    }*/

    @When("I want to read the bookings for a room")
    public void i_want_to_read_the_bookings_for_a_room() {
        token_created = token.validToken();
        given()
                .queryParam("roomid",2)
                .cookie("token=", token_created)
                .log().all()

                .get(URL.get_url_to_retrive_booking_room)
        .then()
                .statusCode(200)
                .log().all();
        System.out.println("to implement... 3");
    }
    @Then("I should receive all existing bookings")
    public void i_should_receive_all_existing_bookings() {
        System.out.println("to implement... 4");
    }

}
