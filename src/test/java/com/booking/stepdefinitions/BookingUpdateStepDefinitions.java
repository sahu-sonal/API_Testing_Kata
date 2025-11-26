package com.booking.stepdefinitions;

import com.booking.models.Booking;
import com.booking.models.BookingResponse;
import com.booking.service.BookingService;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import utils.TestDataBuilder;

public class BookingUpdateStepDefinitions {

    private static final Logger logger = LogManager.getLogger(BookingUpdateStepDefinitions.class);

    private Response response;
    private BookingResponse bookingResponse;
    private Booking bookingRequest;
    private BookingService bookingService;
    private String authToken;
    private Integer createdBookingId;

    public BookingUpdateStepDefinitions() {
        bookingService = new BookingService();
        logger.info("BookingUpdateStepDefinitions initialized");
    }

    private void retrieveAuthToken() {
        logger.info("Retrieving authentication token for PUT update operation");
        authToken = CommonStepDefinitions.getToken();

        logger.debug("Retrieved token: {}", authToken);
        Assertions.assertNotNull(authToken,
                "Authentication token should not be null. Make sure 'I have a valid authentication token' step is executed first.");
    }

    @When("I update the created booking")
    public void i_update_the_created_booking() {
        logger.info("Updating the previously created booking");

        retrieveAuthToken();
        createdBookingId = CommonStepDefinitions.getCreatedBookingId();
        logger.debug("Retrieved booking ID: {}", createdBookingId);
        Assertions.assertNotNull(createdBookingId, "Created booking ID should not be null");

        bookingRequest = TestDataBuilder.createValidBooking();
        bookingRequest.setFirstname("UpdatedName");
        bookingRequest.setLastname("UpdatedLastName");
        logger.debug("Request payload: {}", bookingRequest);

        response = bookingService.updateBooking(createdBookingId, bookingRequest, authToken);
        bookingResponse = CommonStepDefinitions.safeDeserializeBookingResponse(response);

        logResponse();
        CommonStepDefinitions.setResponse(response);
    }

    @When("I update booking ID {int} with new booking details")
    public void i_update_booking_id_with_new_booking_details(Integer bookingId) {
        logger.info("Updating booking ID {} with new details", bookingId);

        retrieveAuthToken();
        bookingRequest = TestDataBuilder.createValidBooking();
        bookingRequest.setFirstname("UpdatedName");
        bookingRequest.setLastname("UpdatedLastName");
        logger.debug("Request payload: {}", bookingRequest);

        response = bookingService.updateBooking(bookingId, bookingRequest, authToken);
        bookingResponse = CommonStepDefinitions.safeDeserializeBookingResponse(response);

        logResponse();
        CommonStepDefinitions.setResponse(response);
    }

    @When("I update booking ID {int} without authentication")
    public void i_update_booking_id_without_authentication(Integer bookingId) {
        logger.info("Attempting update for booking ID {} WITHOUT authentication", bookingId);

        bookingRequest = TestDataBuilder.createValidBooking();
        logger.debug("Request payload: {}", bookingRequest);

        response = bookingService.updateBookingWithoutAuth(bookingId, bookingRequest);

        logResponse();
        CommonStepDefinitions.setResponse(response);
    }

    @When("I update booking ID {int} with {string} token")
    public void i_update_booking_id_with_token_type(Integer bookingId, String tokenType) {
        logger.info("Attempting update for booking ID {} using '{}' token type", bookingId, tokenType);

        bookingRequest = TestDataBuilder.createValidBooking();
        logger.debug("Request payload: {}", bookingRequest);

        if ("invalid".equalsIgnoreCase(tokenType)) {
            logger.debug("Using invalid token");
            response = bookingService.updateBookingWithInvalidToken(bookingId, bookingRequest, "invalid_token_12345");
        } else if ("empty".equalsIgnoreCase(tokenType)) {
            logger.debug("Using empty token");
            response = bookingService.updateBookingWithEmptyToken(bookingId, bookingRequest);
        } else {
            logger.error("Unknown token type received: {}", tokenType);
            throw new IllegalArgumentException("Unknown token type: " + tokenType);
        }

        logResponse();
        CommonStepDefinitions.setResponse(response);
    }

    @When("I update the created booking with firstname {string}")
    public void i_update_the_created_booking_with_firstname(String firstname) {
        logger.info("Updating created booking with firstname '{}'", firstname);

        retrieveAuthToken();
        retrieveCreatedBookingId();

        bookingRequest = TestDataBuilder.createBookingWithFirstname(firstname);
        logger.debug("Request payload: {}", bookingRequest);

        response = bookingService.updateBooking(createdBookingId, bookingRequest, authToken);
        bookingResponse = CommonStepDefinitions.safeDeserializeBookingResponse(response);

        logResponse();
        CommonStepDefinitions.setResponse(response);
    }

    @When("I update the created booking with lastname {string}")
    public void i_update_the_created_booking_with_lastname(String lastname) {
        logger.info("Updating created booking with lastname '{}'", lastname);

        retrieveAuthToken();
        retrieveCreatedBookingId();

        bookingRequest = TestDataBuilder.createBookingWithLastname(lastname);
        logger.debug("Request payload: {}", bookingRequest);

        response = bookingService.updateBooking(createdBookingId, bookingRequest, authToken);
        bookingResponse = CommonStepDefinitions.safeDeserializeBookingResponse(response);

        logResponse();
        CommonStepDefinitions.setResponse(response);
    }

    @When("I update the created booking with email {string}")
    public void i_update_the_created_booking_with_email(String email) {
        logger.info("Updating created booking with email '{}'", email);

        retrieveAuthToken();
        retrieveCreatedBookingId();

        bookingRequest = TestDataBuilder.createBookingWithEmail(email);
        logger.debug("Request payload: {}", bookingRequest);

        response = bookingService.updateBooking(createdBookingId, bookingRequest, authToken);
        bookingResponse = CommonStepDefinitions.safeDeserializeBookingResponse(response);

        logResponse();
        CommonStepDefinitions.setResponse(response);
    }

    @When("I update the created booking with phone {string}")
    public void i_update_the_created_booking_with_phone(String phone) {
        logger.info("Updating created booking with phone '{}'", phone);

        retrieveAuthToken();
        retrieveCreatedBookingId();

        bookingRequest = TestDataBuilder.createBookingWithPhone(phone);
        logger.debug("Request payload: {}", bookingRequest);

        response = bookingService.updateBooking(createdBookingId, bookingRequest, authToken);
        bookingResponse = CommonStepDefinitions.safeDeserializeBookingResponse(response);

        logResponse();
        CommonStepDefinitions.setResponse(response);
    }

    @When("I update the created booking with checkin {string} and checkout {string}")
    public void i_update_the_created_booking_with_checkin_and_checkout(String checkin, String checkout) {
        logger.info("Updating created booking with checkin={} and checkout={}", checkin, checkout);

        retrieveAuthToken();
        retrieveCreatedBookingId();

        bookingRequest = TestDataBuilder.createBookingWithDates(checkin, checkout);
        logger.debug("Request payload: {}", bookingRequest);

        response = bookingService.updateBooking(createdBookingId, bookingRequest, authToken);
        bookingResponse = CommonStepDefinitions.safeDeserializeBookingResponse(response);

        logResponse();
        CommonStepDefinitions.setResponse(response);
    }

    @When("I update the created booking with roomid {string}")
    public void i_update_the_created_booking_with_roomid(String roomid) {
        logger.info("Updating created booking with roomid '{}'", roomid);

        retrieveAuthToken();
        retrieveCreatedBookingId();

        bookingRequest = TestDataBuilder.createBookingWithRoomid(Integer.valueOf(roomid));
        logger.debug("Request payload: {}", bookingRequest);

        response = bookingService.updateBooking(createdBookingId, bookingRequest, authToken);
        bookingResponse = CommonStepDefinitions.safeDeserializeBookingResponse(response);

        logResponse();
        CommonStepDefinitions.setResponse(response);
    }

    @When("I update the created booking with missing {string}")
    public void i_update_the_created_booking_with_missing_field(String field) {
        logger.info("Updating created booking with missing field '{}'", field);

        retrieveAuthToken();
        retrieveCreatedBookingId();

        bookingRequest = TestDataBuilder.createBookingWithoutField(field);
        logger.debug("Request payload: {}", bookingRequest);

        response = bookingService.updateBooking(createdBookingId, bookingRequest, authToken);
        bookingResponse = CommonStepDefinitions.safeDeserializeBookingResponse(response);

        logResponse();
        CommonStepDefinitions.setResponse(response);
    }

    @When("I update the created booking with empty {string}")
    public void i_update_the_created_booking_with_empty_field(String field) {
        logger.info("Updating created booking with empty field '{}'", field);

        retrieveAuthToken();
        retrieveCreatedBookingId();

        bookingRequest = TestDataBuilder.createBookingWithEmptyField(field);
        logger.debug("Request payload: {}", bookingRequest);

        response = bookingService.updateBooking(createdBookingId, bookingRequest, authToken);
        bookingResponse = CommonStepDefinitions.safeDeserializeBookingResponse(response);

        logResponse();
        CommonStepDefinitions.setResponse(response);
    }

    @When("I update the created booking with firstname {string} and lastname {string}")
    public void i_update_the_created_booking_with_firstname_and_lastname(String firstname, String lastname) {
        logger.info("Updating created booking with firstname='{}' and lastname='{}'", firstname, lastname);

        retrieveAuthToken();
        retrieveCreatedBookingId();

        bookingRequest = TestDataBuilder.createValidBooking();
        bookingRequest.setFirstname(firstname);
        bookingRequest.setLastname(lastname);

        logger.debug("Request payload: {}", bookingRequest);

        response = bookingService.updateBooking(createdBookingId, bookingRequest, authToken);
        bookingResponse = CommonStepDefinitions.safeDeserializeBookingResponse(response);

        logResponse();
        CommonStepDefinitions.setResponse(response);
    }

    @When("I update the created booking with null depositpaid")
    public void i_update_the_created_booking_with_null_depositpaid() {
        logger.info("Updating created booking with null depositpaid");

        retrieveAuthToken();
        retrieveCreatedBookingId();

        bookingRequest = TestDataBuilder.createBookingWithoutField("depositpaid");
        logger.debug("Request payload: {}", bookingRequest);

        response = bookingService.updateBooking(createdBookingId, bookingRequest, authToken);
        bookingResponse = CommonStepDefinitions.safeDeserializeBookingResponse(response);

        logResponse();
        CommonStepDefinitions.setResponse(response);
    }

    @Then("the booking should be updated successfully")
    public void the_booking_should_be_updated_successfully() {
        logger.info("Validating update success via booking details comparison");
        CommonStepDefinitions.validateBookingDetailsMatch(bookingResponse, bookingRequest);
    }

    private void retrieveCreatedBookingId() {
        createdBookingId = CommonStepDefinitions.getCreatedBookingId();
        logger.debug("Retrieved booking ID: {}", createdBookingId);
        Assertions.assertNotNull(createdBookingId, "Created booking ID should not be null");
    }

    private void logResponse() {
        logger.info("Response status code: {}", response.getStatusCode());
        logger.debug("Response body: {}", response.asString());
    }
}
