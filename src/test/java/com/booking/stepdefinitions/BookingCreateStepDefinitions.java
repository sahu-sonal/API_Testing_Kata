package com.booking.stepdefinitions;

import com.booking.models.Booking;
import com.booking.models.BookingResponse;
import com.booking.service.BookingService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import utils.TestDataBuilder;

public class BookingCreateStepDefinitions {

    private static final Logger logger = LogManager.getLogger(BookingCreateStepDefinitions.class);

    private Response response;
    private BookingResponse bookingResponse;
    private Booking bookingRequest;
    private BookingService bookingService;
    private Booking existingBooking;

    public BookingCreateStepDefinitions() {
        logger.info("Initializing BookingCreateStepDefinitions");
        bookingService = new BookingService();
    }

    @When("I create a booking with valid booking details")
    public void i_create_a_booking_with_valid_booking_details() {
        logger.info("Creating booking with valid booking details");
        bookingRequest = TestDataBuilder.createValidBooking();
        logger.debug("Request payload: {}", bookingRequest);

        response = bookingService.createBooking(bookingRequest);
        logger.debug("Response Status: {}", response.getStatusCode());

        bookingResponse = CommonStepDefinitions.safeDeserializeBookingResponse(response);
        CommonStepDefinitions.setResponse(response);
    }

    @When("I create a booking with firstname {string}")
    public void i_create_a_booking_with_firstname(String firstname) {
        logger.info("Creating booking with firstname={}", firstname);
        bookingRequest = TestDataBuilder.createBookingWithFirstname(firstname);

        response = bookingService.createBooking(bookingRequest);
        logger.debug("Response Status: {}", response.getStatusCode());

        bookingResponse = CommonStepDefinitions.safeDeserializeBookingResponse(response);
        CommonStepDefinitions.setResponse(response);
    }

    @When("I create a booking with lastname {string}")
    public void i_create_a_booking_with_lastname(String lastname) {
        logger.info("Creating booking with lastname={}", lastname);
        bookingRequest = TestDataBuilder.createBookingWithLastname(lastname);

        response = bookingService.createBooking(bookingRequest);
        logger.debug("Response Status: {}", response.getStatusCode());

        bookingResponse = CommonStepDefinitions.safeDeserializeBookingResponse(response);
        CommonStepDefinitions.setResponse(response);
    }

    @When("I create a booking with email {string}")
    public void i_create_a_booking_with_email(String email) {
        logger.info("Creating booking with email={}", email);
        bookingRequest = TestDataBuilder.createBookingWithEmail(email);

        response = bookingService.createBooking(bookingRequest);
        logger.debug("Response Status: {}", response.getStatusCode());

        bookingResponse = CommonStepDefinitions.safeDeserializeBookingResponse(response);
        CommonStepDefinitions.setResponse(response);
    }

    @When("I create a booking with phone {string}")
    public void i_create_a_booking_with_phone(String phone) {
        logger.info("Creating booking with phone={}", phone);
        bookingRequest = TestDataBuilder.createBookingWithPhone(phone);

        response = bookingService.createBooking(bookingRequest);
        logger.debug("Response Status: {}", response.getStatusCode());

        bookingResponse = CommonStepDefinitions.safeDeserializeBookingResponse(response);
        CommonStepDefinitions.setResponse(response);
    }

    @When("I create a booking with checkin {string} and checkout {string}")
    public void i_create_a_booking_with_checkin_and_checkout(String checkin, String checkout) {
        logger.info("Creating booking with checkin={} and checkout={}", checkin, checkout);
        bookingRequest = TestDataBuilder.createBookingWithDates(checkin, checkout);

        response = bookingService.createBooking(bookingRequest);
        logger.debug("Response Status: {}", response.getStatusCode());

        bookingResponse = CommonStepDefinitions.safeDeserializeBookingResponse(response);
        CommonStepDefinitions.setResponse(response);
    }

    @When("I create a booking with roomid {string}")
    public void i_create_a_booking_with_roomid(String roomid) {
        logger.info("Creating booking with roomid {}", roomid);
        Integer roomIdValue = Integer.parseInt(roomid);
        bookingRequest = TestDataBuilder.createBookingWithRoomid(roomIdValue);

        response = bookingService.createBooking(bookingRequest);
        logger.debug("Response Status: {}", response.getStatusCode());

        bookingResponse = CommonStepDefinitions.safeDeserializeBookingResponse(response);
        CommonStepDefinitions.setResponse(response);
    }

    @When("I create a booking with missing {string}")
    public void i_create_a_booking_with_missing_field(String field) {
        logger.info("Creating booking missing field {}", field);
        bookingRequest = TestDataBuilder.createBookingWithoutField(field);

        response = bookingService.createBooking(bookingRequest);
        logger.debug("Response Status: {}", response.getStatusCode());

        bookingResponse = CommonStepDefinitions.safeDeserializeBookingResponse(response);
        CommonStepDefinitions.setResponse(response);
    }

    @When("I create a booking with empty {string}")
    public void i_create_a_booking_with_empty_field(String field) {
        logger.info("Creating booking with empty field {}", field);
        bookingRequest = TestDataBuilder.createBookingWithEmptyField(field);

        response = bookingService.createBooking(bookingRequest);
        logger.debug("Response Status: {}", response.getStatusCode());

        bookingResponse = CommonStepDefinitions.safeDeserializeBookingResponse(response);
        CommonStepDefinitions.setResponse(response);
    }

    @When("I create a booking with firstname {string} and lastname {string}")
    public void i_create_a_booking_with_firstname_and_lastname(String firstname, String lastname) {
        logger.info("Creating booking with firstname={} and lastname={}", firstname, lastname);

        bookingRequest = TestDataBuilder.createValidBooking();
        bookingRequest.setFirstname(firstname);
        bookingRequest.setLastname(lastname);

        response = bookingService.createBooking(bookingRequest);
        logger.debug("Response Status: {}", response.getStatusCode());

        bookingResponse = CommonStepDefinitions.safeDeserializeBookingResponse(response);
        CommonStepDefinitions.setResponse(response);
    }

    @When("I create a booking with null depositpaid")
    public void i_create_a_booking_with_null_depositpaid() {
        logger.info("Creating booking with null depositpaid");

        bookingRequest = TestDataBuilder.createBookingWithoutField("depositpaid");

        response = bookingService.createBooking(bookingRequest);
        logger.debug("Response Status: {}", response.getStatusCode());

        bookingResponse = CommonStepDefinitions.safeDeserializeBookingResponse(response);
        CommonStepDefinitions.setResponse(response);
    }

    @Given("I have created a booking with valid details")
    public void i_have_created_a_booking_with_valid_details() {
        logger.info("Creating initial booking for duplicate test");

        existingBooking = TestDataBuilder.createValidBooking();
        Response createResponse = bookingService.createBooking(existingBooking);

        BookingResponse createBookingResponse = CommonStepDefinitions.safeDeserializeBookingResponse(createResponse);
        Assertions.assertNotNull(createBookingResponse.getBookingid(), "Initial booking should succeed");

        logger.debug("Created booking ID={}", createBookingResponse.getBookingid());
    }

    @When("I try to create the same booking again")
    public void i_try_to_create_the_same_booking_again() {
        logger.info("Attempting to create duplicate booking");

        bookingRequest = existingBooking;
        response = bookingService.createBooking(bookingRequest);
        logger.debug("Duplicate Response Status: {}", response.getStatusCode());

        bookingResponse = CommonStepDefinitions.safeDeserializeBookingResponse(response);
        CommonStepDefinitions.setResponse(response);
    }

    @Then("the response should contain a booking id")
    public void the_response_should_contain_a_booking_id() {
        logger.info("Validating booking ID exists");
        CommonStepDefinitions.validateBookingIdExists(bookingResponse);
    }

    @Then("the booking details should match the request")
    public void the_booking_details_should_match_the_request() {
        logger.info("Validating booking details match");
        CommonStepDefinitions.validateBookingDetailsMatch(bookingResponse, bookingRequest);
    }
}
