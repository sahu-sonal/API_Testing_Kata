package com.booking.stepdefinitions;

import com.booking.service.BookingService;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

public class BookingDeleteStepDefinitions {

    private Response response;
    private BookingService bookingService;
    private String authToken;
    private Integer createdBookingId;

    public BookingDeleteStepDefinitions() {
        bookingService = new BookingService();
    }

    private void retrieveAuthToken() {
        authToken = CommonStepDefinitions.getToken();
        Assertions.assertNotNull(authToken, "Authentication token should not be null. Make sure 'I have a valid authentication token' step is executed first.");
    }

    @When("I delete the created booking")
    public void i_delete_the_created_booking() {
        // Retrieve token and booking ID from common step definitions
        retrieveAuthToken();
        createdBookingId = CommonStepDefinitions.getCreatedBookingId();
        Assertions.assertNotNull(createdBookingId, "Created booking ID should not be null. Make sure 'I have created a booking' step is executed first.");

        response = bookingService.deleteBooking(createdBookingId, authToken);
        CommonStepDefinitions.setResponse(response);
    }

    @When("I delete booking ID {int}")
    public void i_delete_booking_id(Integer bookingId) {
        // Retrieve token from common step definition
        retrieveAuthToken();
        response = bookingService.deleteBooking(bookingId, authToken);
        CommonStepDefinitions.setResponse(response);
    }

    @When("I delete booking ID {int} without authentication")
    public void i_delete_booking_id_without_authentication(Integer bookingId) {
        // No need to retrieve token for this negative test case
        response = bookingService.deleteBookingWithoutAuth(bookingId);
        CommonStepDefinitions.setResponse(response);
    }

    @When("I delete booking ID {int} with {string} token")
    public void i_delete_booking_id_with_token_type(Integer bookingId, String tokenType) {
        if ("invalid".equals(tokenType)) {
            // Use an invalid token (random string that doesn't match any valid token)
            response = bookingService.deleteBookingWithInvalidToken(bookingId, "invalid_token_12345");
        } else if ("empty".equals(tokenType)) {
            // Use an empty token
            response = bookingService.deleteBookingWithEmptyToken(bookingId);
        } else {
            throw new IllegalArgumentException("Unknown token type: " + tokenType);
        }
        CommonStepDefinitions.setResponse(response);
    }
}
