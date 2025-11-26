package com.booking.stepdefinitions;

import com.booking.service.BookingService;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;

public class BookingGetStepDefinitions {

    private static final Logger logger = LogManager.getLogger(BookingGetStepDefinitions.class);

    private Response response;
    private BookingService bookingService;
    private String authToken;
    private Integer createdBookingId;

    public BookingGetStepDefinitions() {
        bookingService = new BookingService();
        logger.info("BookingGetStepDefinitions initialized");
    }

    private void retrieveAuthToken() {
        logger.info("Retrieving authentication token for GET operation");
        authToken = CommonStepDefinitions.getToken();

        logger.debug("Retrieved token: {}", authToken);
        Assertions.assertNotNull(authToken,
                "Authentication token should not be null. Make sure 'I have a valid authentication token' step is executed first.");
    }

    @When("I get the created booking")
    public void i_get_the_created_booking() {
        logger.info("Attempting to retrieve the previously created booking");

        retrieveAuthToken();
        createdBookingId = CommonStepDefinitions.getCreatedBookingId();

        logger.debug("Retrieved booking ID to fetch: {}", createdBookingId);
        Assertions.assertNotNull(createdBookingId,
                "Created booking ID should not be null. Make sure 'I have created a booking' step is executed first.");

        response = bookingService.getBookingById(createdBookingId, authToken);
        logger.info("GET request completed with status code: {}", response.getStatusCode());
        logger.debug("Response body: {}", response.asString());

        CommonStepDefinitions.setResponse(response);
    }

    @When("I get booking by ID {int}")
    public void i_get_booking_by_id(Integer bookingId) {
        logger.info("Fetching booking with ID: {}", bookingId);

        retrieveAuthToken();
        response = bookingService.getBookingById(bookingId, authToken);

        logger.info("GET request completed with status code: {}", response.getStatusCode());
        logger.debug("Response body: {}", response.asString());

        CommonStepDefinitions.setResponse(response);
    }

    @When("I get booking by ID {int} without authentication")
    public void i_get_booking_by_id_without_authentication(Integer bookingId) {
        logger.info("Attempting to fetch booking ID {} without authentication", bookingId);

        response = bookingService.getBookingByIdWithoutAuth(bookingId);

        logger.info("GET request completed with status code: {}", response.getStatusCode());
        logger.debug("Response body: {}", response.asString());

        CommonStepDefinitions.setResponse(response);
    }

    @When("I get booking by ID {int} with {string} token")
    public void i_get_booking_by_id_with_token_type(Integer bookingId, String tokenType) {
        logger.info("Attempting to fetch booking ID {} using '{}' token scenario", bookingId, tokenType);

        if ("invalid".equalsIgnoreCase(tokenType)) {
            logger.debug("Using invalid token");
            response = bookingService.getBookingByIdWithInvalidToken(bookingId, "invalid_token_12345");
        } else if ("empty".equalsIgnoreCase(tokenType)) {
            logger.debug("Using empty token");
            response = bookingService.getBookingByIdWithEmptyToken(bookingId);
        } else {
            logger.error("Unknown token type received: {}", tokenType);
            throw new IllegalArgumentException("Unknown token type: " + tokenType);
        }

        logger.info("GET request completed with status code: {}", response.getStatusCode());
        logger.debug("Response body: {}", response.asString());

        CommonStepDefinitions.setResponse(response);
    }
}
