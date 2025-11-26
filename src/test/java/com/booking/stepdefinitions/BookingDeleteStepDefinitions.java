package com.booking.stepdefinitions;

import com.booking.service.BookingService;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;

public class BookingDeleteStepDefinitions {

    private static final Logger logger = LogManager.getLogger(BookingDeleteStepDefinitions.class);

    private Response response;
    private BookingService bookingService;
    private String authToken;
    private Integer createdBookingId;

    public BookingDeleteStepDefinitions() {
        bookingService = new BookingService();
        logger.info("BookingDeleteStepDefinitions initialized");
    }

    private void retrieveAuthToken() {
        logger.info("Retrieving authentication token for delete operation");
        authToken = CommonStepDefinitions.getToken();

        logger.debug("Retrieved token: {}", authToken);

        Assertions.assertNotNull(authToken,
                "Authentication token should not be null. Make sure 'I have a valid authentication token' step is executed first.");
    }

    @When("I delete the created booking")
    public void i_delete_the_created_booking() {
        logger.info("Executing delete operation for the created booking");

        retrieveAuthToken();
        createdBookingId = CommonStepDefinitions.getCreatedBookingId();

        logger.debug("Retrieved booking ID to delete: {}", createdBookingId);
        Assertions.assertNotNull(createdBookingId,
                "Created booking ID should not be null. Make sure 'I have created a booking' step is executed first.");

        response = bookingService.deleteBooking(createdBookingId, authToken);
        logger.info("Delete request sent. Response status: {}", response.getStatusCode());
        logger.debug("Response body: {}", response.asString());

        CommonStepDefinitions.setResponse(response);
    }

    @When("I delete booking ID {int}")
    public void i_delete_booking_id(Integer bookingId) {
        logger.info("Deleting booking with ID: {}", bookingId);

        retrieveAuthToken();
        response = bookingService.deleteBooking(bookingId, authToken);

        logger.info("Delete request completed with status code: {}", response.getStatusCode());
        logger.debug("Response body: {}", response.asString());

        CommonStepDefinitions.setResponse(response);
    }

    @When("I delete booking ID {int} without authentication")
    public void i_delete_booking_id_without_authentication(Integer bookingId) {
        logger.info("Attempting to delete booking ID {} without authentication", bookingId);

        response = bookingService.deleteBookingWithoutAuth(bookingId);

        logger.info("Response status: {}", response.getStatusCode());
        logger.debug("Response body: {}", response.asString());

        CommonStepDefinitions.setResponse(response);
    }

    @When("I delete booking ID {int} with {string} token")
    public void i_delete_booking_id_with_token_type(Integer bookingId, String tokenType) {
        logger.info("Deleting booking ID {} using '{}' token", bookingId, tokenType);

        if ("invalid".equalsIgnoreCase(tokenType)) {
            logger.debug("Using invalid token for deletion attempt");
            response = bookingService.deleteBookingWithInvalidToken(bookingId, "invalid_token_12345");
        } else if ("empty".equalsIgnoreCase(tokenType)) {
            logger.debug("Using empty token for deletion attempt");
            response = bookingService.deleteBookingWithEmptyToken(bookingId);
        } else {
            logger.error("Unknown token type provided: {}", tokenType);
            throw new IllegalArgumentException("Unknown token type: " + tokenType);
        }

        logger.info("Response status after delete attempt: {}", response.getStatusCode());
        logger.debug("Response body: {}", response.asString());

        CommonStepDefinitions.setResponse(response);
    }
}
