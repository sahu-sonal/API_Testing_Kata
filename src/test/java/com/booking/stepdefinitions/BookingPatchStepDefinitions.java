package com.booking.stepdefinitions;

import com.booking.models.Booking;
import com.booking.service.BookingService;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import utils.TestDataBuilder;

public class BookingPatchStepDefinitions {

    private static final Logger logger = LogManager.getLogger(BookingPatchStepDefinitions.class);

    private Response response;
    private Booking bookingRequest;
    private BookingService bookingService;
    private String authToken;

    public BookingPatchStepDefinitions() {
        bookingService = new BookingService();
        logger.info("BookingPatchStepDefinitions initialized");
    }

    private void retrieveAuthToken() {
        logger.info("Retrieving authentication token for PATCH operation");
        authToken = CommonStepDefinitions.getToken();
        logger.debug("Retrieved token: {}", authToken);

        Assertions.assertNotNull(authToken,
                "Authentication token should not be null. Make sure 'I have a valid authentication token' step is executed first.");
    }

    @When("I partially update booking ID {int} with firstname {string}")
    public void i_partially_update_booking_id_with_firstname(Integer bookingId, String firstname) {
        logger.info("Partially updating booking ID {} with firstname '{}'", bookingId, firstname);
        retrieveAuthToken();

        bookingRequest = TestDataBuilder.createPartialBookingUpdate(firstname, null, null);
        logger.debug("Request payload: {}", bookingRequest);

        response = bookingService.partialUpdateBooking(bookingId, bookingRequest, authToken);
        logResponse();
        CommonStepDefinitions.setResponse(response);
    }

    @When("I partially update booking ID {int} with lastname {string}")
    public void i_partially_update_booking_id_with_lastname(Integer bookingId, String lastname) {
        logger.info("Partially updating booking ID {} with lastname '{}'", bookingId, lastname);
        retrieveAuthToken();

        bookingRequest = TestDataBuilder.createPartialBookingUpdate(null, lastname, null);
        logger.debug("Request payload: {}", bookingRequest);

        response = bookingService.partialUpdateBooking(bookingId, bookingRequest, authToken);
        logResponse();
        CommonStepDefinitions.setResponse(response);
    }

    @When("I partially update booking ID {int} with depositpaid {string}")
    public void i_partially_update_booking_id_with_depositpaid(Integer bookingId, String depositpaid) {
        logger.info("Partially updating booking ID {} with depositpaid '{}'", bookingId, depositpaid);
        retrieveAuthToken();

        Boolean depositpaidValue = Boolean.parseBoolean(depositpaid);
        bookingRequest = TestDataBuilder.createPartialBookingUpdate(null, null, depositpaidValue);
        logger.debug("Request payload: {}", bookingRequest);

        response = bookingService.partialUpdateBooking(bookingId, bookingRequest, authToken);
        logResponse();
        CommonStepDefinitions.setResponse(response);
    }

    @When("I partially update booking ID {int} without authentication")
    public void i_partially_update_booking_id_without_authentication(Integer bookingId) {
        logger.info("Attempting PATCH operation without auth token on booking ID {}", bookingId);

        bookingRequest = TestDataBuilder.createPartialBookingUpdate("NewName", null, null);
        logger.debug("Request payload: {}", bookingRequest);

        response = bookingService.partialUpdateBookingWithoutAuth(bookingId, bookingRequest);
        logResponse();
        CommonStepDefinitions.setResponse(response);
    }

    @When("I partially update booking ID {int} with firstname {string} and {string} token")
    public void i_partially_update_booking_id_with_firstname_and_token_type(Integer bookingId, String firstname, String tokenType) {
        logger.info("Updating booking ID {} with token type '{}'", bookingId, tokenType);

        bookingRequest = TestDataBuilder.createPartialBookingUpdate(firstname, null, null);
        logger.debug("Request payload: {}", bookingRequest);

        if ("invalid".equalsIgnoreCase(tokenType)) {
            logger.debug("Using invalid token");
            response = bookingService.partialUpdateBookingWithInvalidToken(bookingId, bookingRequest, "invalid_token_12345");
        } else if ("empty".equalsIgnoreCase(tokenType)) {
            logger.debug("Using empty token");
            response = bookingService.partialUpdateBookingWithEmptyToken(bookingId, bookingRequest);
        } else {
            logger.error("Unknown token type received: {}", tokenType);
            throw new IllegalArgumentException("Unknown token type: " + tokenType);
        }

        logResponse();
        CommonStepDefinitions.setResponse(response);
    }

    @When("I partially update the created booking with firstname {string}")
    public void i_partially_update_the_created_booking_with_firstname(String firstname) {
        logger.info("Updating created booking with firstname '{}'", firstname);

        retrieveAuthToken();
        Integer createdBookingId = CommonStepDefinitions.getCreatedBookingId();
        logger.debug("Retrieved booking ID: {}", createdBookingId);

        Assertions.assertNotNull(createdBookingId, "Created booking ID should not be null");

        bookingRequest = TestDataBuilder.createPartialBookingUpdate(firstname, null, null);
        logger.debug("Request payload: {}", bookingRequest);

        response = bookingService.partialUpdateBooking(createdBookingId, bookingRequest, authToken);
        logResponse();
        CommonStepDefinitions.setResponse(response);
    }

    @When("I partially update the created booking with lastname {string}")
    public void i_partially_update_the_created_booking_with_lastname(String lastname) {
        logger.info("Updating created booking with lastname '{}'", lastname);

        retrieveAuthToken();
        Integer createdBookingId = CommonStepDefinitions.getCreatedBookingId();
        logger.debug("Retrieved booking ID: {}", createdBookingId);

        Assertions.assertNotNull(createdBookingId, "Created booking ID should not be null");

        bookingRequest = TestDataBuilder.createPartialBookingUpdate(null, lastname, null);
        logger.debug("Request payload: {}", bookingRequest);

        response = bookingService.partialUpdateBooking(createdBookingId, bookingRequest, authToken);
        logResponse();
        CommonStepDefinitions.setResponse(response);
    }

    @When("I partially update the created booking with depositpaid {string}")
    public void i_partially_update_the_created_booking_with_depositpaid(String depositpaid) {
        logger.info("Updating created booking with depositpaid '{}'", depositpaid);

        retrieveAuthToken();
        Integer createdBookingId = CommonStepDefinitions.getCreatedBookingId();
        logger.debug("Retrieved booking ID: {}", createdBookingId);

        Assertions.assertNotNull(createdBookingId, "Created booking ID should not be null");

        Boolean depositpaidValue = Boolean.parseBoolean(depositpaid);
        bookingRequest = TestDataBuilder.createPartialBookingUpdate(null, null, depositpaidValue);
        logger.debug("Request payload: {}", bookingRequest);

        response = bookingService.partialUpdateBooking(createdBookingId, bookingRequest, authToken);
        logResponse();
        CommonStepDefinitions.setResponse(response);
    }

    @When("I partially update the created booking with firstname {string} and lastname {string}")
    public void i_partially_update_the_created_booking_with_firstname_and_lastname(String firstname, String lastname) {
        logger.info("Updating created booking with firstname '{}' and lastname '{}'", firstname, lastname);

        retrieveAuthToken();
        Integer createdBookingId = CommonStepDefinitions.getCreatedBookingId();
        logger.debug("Retrieved booking ID: {}", createdBookingId);

        Assertions.assertNotNull(createdBookingId, "Created booking ID should not be null");

        bookingRequest = TestDataBuilder.createPartialBookingUpdate(firstname, lastname, null);
        logger.debug("Request payload: {}", bookingRequest);

        response = bookingService.partialUpdateBooking(createdBookingId, bookingRequest, authToken);
        logResponse();
        CommonStepDefinitions.setResponse(response);
    }

    private void logResponse() {
        logger.info("PATCH request completed with status code: {}", response.getStatusCode());
        logger.debug("PATCH response body: {}", response.asString());
    }
}
