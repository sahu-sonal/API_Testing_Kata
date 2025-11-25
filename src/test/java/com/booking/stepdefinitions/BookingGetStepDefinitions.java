package com.booking.stepdefinitions;

import com.booking.service.BookingService;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

public class BookingGetStepDefinitions {
    
    private Response response;
    private BookingService bookingService;
    private String authToken;
    private Integer createdBookingId;

    public BookingGetStepDefinitions() {
        bookingService = new BookingService();
    }

    private void retrieveAuthToken() {
        authToken = CommonStepDefinitions.getToken();
        Assertions.assertNotNull(authToken, "Authentication token should not be null. Make sure 'I have a valid authentication token' step is executed first.");
    }
    
    @When("I get the created booking")
    public void i_get_the_created_booking() {
        // Retrieve token and booking ID from common step definitions
        retrieveAuthToken();
        createdBookingId = CommonStepDefinitions.getCreatedBookingId();
        Assertions.assertNotNull(createdBookingId, "Created booking ID should not be null. Make sure 'I have created a booking' step is executed first.");
        
        // Get the booking we created earlier
        response = bookingService.getBookingById(createdBookingId, authToken);
        CommonStepDefinitions.setResponse(response);
    }

    @When("I get booking by ID {int}")
    public void i_get_booking_by_id(Integer bookingId) {
        // Retrieve token from common step definition
        retrieveAuthToken();
        // Get booking with authentication
        response = bookingService.getBookingById(bookingId, authToken);
        CommonStepDefinitions.setResponse(response);
    }

    @When("I get booking by ID {int} without authentication")
    public void i_get_booking_by_id_without_authentication(Integer bookingId) {
        // Get booking without authentication
        response = bookingService.getBookingByIdWithoutAuth(bookingId);
        CommonStepDefinitions.setResponse(response);
    }

    @When("I get booking by ID {int} with {string} token")
    public void i_get_booking_by_id_with_token_type(Integer bookingId, String tokenType) {
        if ("invalid".equals(tokenType)) {
            // Use an invalid token (random string that doesn't match any valid token)
            response = bookingService.getBookingByIdWithInvalidToken(bookingId, "invalid_token_12345");
        } else if ("empty".equals(tokenType)) {
            // Use an empty token
            response = bookingService.getBookingByIdWithEmptyToken(bookingId);
        } else {
            throw new IllegalArgumentException("Unknown token type: " + tokenType);
        }
        CommonStepDefinitions.setResponse(response);
    }
}

