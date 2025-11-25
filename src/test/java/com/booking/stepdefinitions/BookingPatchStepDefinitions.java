package com.booking.stepdefinitions;

import com.booking.models.Booking;
import com.booking.service.BookingService;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import utils.TestDataBuilder;

public class BookingPatchStepDefinitions {

    private Response response;
    private Booking bookingRequest;
    private BookingService bookingService;
    private String authToken;

    public BookingPatchStepDefinitions() {
        bookingService = new BookingService();
    }

    private void retrieveAuthToken() {
        authToken = CommonStepDefinitions.getToken();
        Assertions.assertNotNull(authToken, "Authentication token should not be null. Make sure 'I have a valid authentication token' step is executed first.");
    }


    @When("I partially update booking ID {int} with firstname {string}")
    public void i_partially_update_booking_id_with_firstname(Integer bookingId, String firstname) {
        // Retrieve token from common step definition
        retrieveAuthToken();
        // Create partial update with only firstname
        bookingRequest = TestDataBuilder.createPartialBookingUpdate(firstname, null, null);

        response = bookingService.partialUpdateBooking(bookingId, bookingRequest, authToken);
        CommonStepDefinitions.setResponse(response);
    }

    @When("I partially update booking ID {int} with lastname {string}")
    public void i_partially_update_booking_id_with_lastname(Integer bookingId, String lastname) {
        // Retrieve token from common step definition
        retrieveAuthToken();
        bookingRequest = TestDataBuilder.createPartialBookingUpdate(null, lastname, null);

        response = bookingService.partialUpdateBooking(bookingId, bookingRequest, authToken);
        CommonStepDefinitions.setResponse(response);
    }

    @When("I partially update booking ID {int} with depositpaid {string}")
    public void i_partially_update_booking_id_with_depositpaid(Integer bookingId, String depositpaid) {
        // Retrieve token from common step definition
        retrieveAuthToken();
        Boolean depositpaidValue = Boolean.parseBoolean(depositpaid);
        bookingRequest = TestDataBuilder.createPartialBookingUpdate(null, null, depositpaidValue);

        response = bookingService.partialUpdateBooking(bookingId, bookingRequest, authToken);
        CommonStepDefinitions.setResponse(response);
    }

    @When("I partially update booking ID {int} without authentication")
    public void i_partially_update_booking_id_without_authentication(Integer bookingId) {
        // No need to retrieve token for this negative test case
        bookingRequest = TestDataBuilder.createPartialBookingUpdate("NewName", null, null);

        response = bookingService.partialUpdateBookingWithoutAuth(bookingId, bookingRequest);
        CommonStepDefinitions.setResponse(response);
    }

    @When("I partially update booking ID {int} with firstname {string} and {string} token")
    public void i_partially_update_booking_id_with_firstname_and_token_type(Integer bookingId, String firstname, String tokenType) {
        bookingRequest = TestDataBuilder.createPartialBookingUpdate(firstname, null, null);

        if ("invalid".equals(tokenType)) {
            // Use an invalid token (random string that doesn't match any valid token)
            response = bookingService.partialUpdateBookingWithInvalidToken(bookingId, bookingRequest, "invalid_token_12345");
        } else if ("empty".equals(tokenType)) {
            // Use an empty token
            response = bookingService.partialUpdateBookingWithEmptyToken(bookingId, bookingRequest);
        } else {
            throw new IllegalArgumentException("Unknown token type: " + tokenType);
        }
        CommonStepDefinitions.setResponse(response);
    }

    @When("I partially update the created booking with firstname {string}")
    public void i_partially_update_the_created_booking_with_firstname(String firstname) {
        retrieveAuthToken();
        Integer createdBookingId = CommonStepDefinitions.getCreatedBookingId();
        Assertions.assertNotNull(createdBookingId, "Created booking ID should not be null");

        bookingRequest = TestDataBuilder.createPartialBookingUpdate(firstname, null, null);
        response = bookingService.partialUpdateBooking(createdBookingId, bookingRequest, authToken);
        CommonStepDefinitions.setResponse(response);
    }

    @When("I partially update the created booking with lastname {string}")
    public void i_partially_update_the_created_booking_with_lastname(String lastname) {
        retrieveAuthToken();
        Integer createdBookingId = CommonStepDefinitions.getCreatedBookingId();
        Assertions.assertNotNull(createdBookingId, "Created booking ID should not be null");

        bookingRequest = TestDataBuilder.createPartialBookingUpdate(null, lastname, null);
        response = bookingService.partialUpdateBooking(createdBookingId, bookingRequest, authToken);
        CommonStepDefinitions.setResponse(response);
    }

    @When("I partially update the created booking with depositpaid {string}")
    public void i_partially_update_the_created_booking_with_depositpaid(String depositpaid) {
        retrieveAuthToken();
        Integer createdBookingId = CommonStepDefinitions.getCreatedBookingId();
        Assertions.assertNotNull(createdBookingId, "Created booking ID should not be null");

        Boolean depositpaidValue = Boolean.parseBoolean(depositpaid);
        bookingRequest = TestDataBuilder.createPartialBookingUpdate(null, null, depositpaidValue);
        response = bookingService.partialUpdateBooking(createdBookingId, bookingRequest, authToken);
        CommonStepDefinitions.setResponse(response);
    }

    @When("I partially update the created booking with firstname {string} and lastname {string}")
    public void i_partially_update_the_created_booking_with_firstname_and_lastname(String firstname, String lastname) {
        retrieveAuthToken();
        Integer createdBookingId = CommonStepDefinitions.getCreatedBookingId();
        Assertions.assertNotNull(createdBookingId, "Created booking ID should not be null");

        bookingRequest = TestDataBuilder.createPartialBookingUpdate(firstname, lastname, null);
        response = bookingService.partialUpdateBooking(createdBookingId, bookingRequest, authToken);
        CommonStepDefinitions.setResponse(response);
    }
}


