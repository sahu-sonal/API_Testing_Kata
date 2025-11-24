package com.booking.stepdefinitions;

import com.booking.models.Booking;
import com.booking.models.BookingResponse;
import com.booking.service.BookingService;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import utils.TestDataBuilder;

public class BookingUpdateStepDefinitions {
    
    private Response response;
    private BookingResponse bookingResponse;
    private Booking bookingRequest;
    private BookingService bookingService;
    private String authToken;
    private Integer createdBookingId;

    public BookingUpdateStepDefinitions() {
        bookingService = new BookingService();
    }

    // Note: @Given("I have a valid authentication token") is defined in CommonStepDefinitions
    // This method retrieves the token stored by the common step definition
    private void retrieveAuthToken() {
        authToken = CommonStepDefinitions.getToken();
        Assertions.assertNotNull(authToken, "Authentication token should not be null. Make sure 'I have a valid authentication token' step is executed first.");
    }

    // Note: @Given("I have created a booking") is defined in CommonStepDefinitions
    
    @When("I update the created booking")
    public void i_update_the_created_booking() {
        // Retrieve token and booking ID from common step definitions
        retrieveAuthToken();
        createdBookingId = CommonStepDefinitions.getCreatedBookingId();
        Assertions.assertNotNull(createdBookingId, "Created booking ID should not be null. Make sure 'I have created a booking' step is executed first.");
        
        // Create updated booking data
        bookingRequest = TestDataBuilder.createValidBooking();
        bookingRequest.setFirstname("UpdatedName");
        bookingRequest.setLastname("UpdatedLastName");
        
        // Update booking with authentication
        response = bookingService.updateBooking(createdBookingId, bookingRequest, authToken);
        bookingResponse = CommonStepDefinitions.safeDeserializeBookingResponse(response);
        CommonStepDefinitions.setResponse(response);
    }

    @When("I update booking ID {int} with new booking details")
    public void i_update_booking_id_with_new_booking_details(Integer bookingId) {
        // Retrieve token from common step definition
        retrieveAuthToken();
        // Create updated booking data
        bookingRequest = TestDataBuilder.createValidBooking();
        bookingRequest.setFirstname("UpdatedName");
        bookingRequest.setLastname("UpdatedLastName");
        
        // Update booking with authentication
        response = bookingService.updateBooking(bookingId, bookingRequest, authToken);
        bookingResponse = CommonStepDefinitions.safeDeserializeBookingResponse(response);
        CommonStepDefinitions.setResponse(response);
    }

    @When("I update booking ID {int} without authentication")
    public void i_update_booking_id_without_authentication(Integer bookingId) {
        // No need to retrieve token for this negative test case
        bookingRequest = TestDataBuilder.createValidBooking();
        
        response = bookingService.updateBookingWithoutAuth(bookingId, bookingRequest);
        CommonStepDefinitions.setResponse(response);
    }

    @When("I update booking ID {int} with {string} token")
    public void i_update_booking_id_with_token_type(Integer bookingId, String tokenType) {
        bookingRequest = TestDataBuilder.createValidBooking();
        
        if ("invalid".equals(tokenType)) {
            // Use an invalid token (random string that doesn't match any valid token)
            response = bookingService.updateBookingWithInvalidToken(bookingId, bookingRequest, "invalid_token_12345");
        } else if ("empty".equals(tokenType)) {
            // Use an empty token
            response = bookingService.updateBookingWithEmptyToken(bookingId, bookingRequest);
        } else {
            throw new IllegalArgumentException("Unknown token type: " + tokenType);
        }
        CommonStepDefinitions.setResponse(response);
    }

    @When("I update the created booking with firstname {string}")
    public void i_update_the_created_booking_with_firstname(String firstname) {
        retrieveAuthToken();
        createdBookingId = CommonStepDefinitions.getCreatedBookingId();
        Assertions.assertNotNull(createdBookingId, "Created booking ID should not be null");
        
        bookingRequest = TestDataBuilder.createBookingWithFirstname(firstname);
        response = bookingService.updateBooking(createdBookingId, bookingRequest, authToken);
        bookingResponse = CommonStepDefinitions.safeDeserializeBookingResponse(response);
        CommonStepDefinitions.setResponse(response);
    }

    @When("I update the created booking with lastname {string}")
    public void i_update_the_created_booking_with_lastname(String lastname) {
        retrieveAuthToken();
        createdBookingId = CommonStepDefinitions.getCreatedBookingId();
        Assertions.assertNotNull(createdBookingId, "Created booking ID should not be null");
        
        bookingRequest = TestDataBuilder.createBookingWithLastname(lastname);
        response = bookingService.updateBooking(createdBookingId, bookingRequest, authToken);
        bookingResponse = CommonStepDefinitions.safeDeserializeBookingResponse(response);
        CommonStepDefinitions.setResponse(response);
    }

    @When("I update the created booking with email {string}")
    public void i_update_the_created_booking_with_email(String email) {
        retrieveAuthToken();
        createdBookingId = CommonStepDefinitions.getCreatedBookingId();
        Assertions.assertNotNull(createdBookingId, "Created booking ID should not be null");
        
        bookingRequest = TestDataBuilder.createBookingWithEmail(email);
        response = bookingService.updateBooking(createdBookingId, bookingRequest, authToken);
        bookingResponse = CommonStepDefinitions.safeDeserializeBookingResponse(response);
        CommonStepDefinitions.setResponse(response);
    }

    @When("I update the created booking with phone {string}")
    public void i_update_the_created_booking_with_phone(String phone) {
        retrieveAuthToken();
        createdBookingId = CommonStepDefinitions.getCreatedBookingId();
        Assertions.assertNotNull(createdBookingId, "Created booking ID should not be null");
        
        bookingRequest = TestDataBuilder.createBookingWithPhone(phone);
        response = bookingService.updateBooking(createdBookingId, bookingRequest, authToken);
        bookingResponse = CommonStepDefinitions.safeDeserializeBookingResponse(response);
        CommonStepDefinitions.setResponse(response);
    }

    @When("I update the created booking with checkin {string} and checkout {string}")
    public void i_update_the_created_booking_with_checkin_and_checkout(String checkin, String checkout) {
        retrieveAuthToken();
        createdBookingId = CommonStepDefinitions.getCreatedBookingId();
        Assertions.assertNotNull(createdBookingId, "Created booking ID should not be null");
        
        bookingRequest = TestDataBuilder.createBookingWithDates(checkin, checkout);
        response = bookingService.updateBooking(createdBookingId, bookingRequest, authToken);
        bookingResponse = CommonStepDefinitions.safeDeserializeBookingResponse(response);
        CommonStepDefinitions.setResponse(response);
    }

    @When("I update the created booking with roomid {string}")
    public void i_update_the_created_booking_with_roomid(String roomid) {
        retrieveAuthToken();
        createdBookingId = CommonStepDefinitions.getCreatedBookingId();
        Assertions.assertNotNull(createdBookingId, "Created booking ID should not be null");
        
        Integer roomIdValue = Integer.parseInt(roomid);
        bookingRequest = TestDataBuilder.createBookingWithRoomid(roomIdValue);
        response = bookingService.updateBooking(createdBookingId, bookingRequest, authToken);
        bookingResponse = CommonStepDefinitions.safeDeserializeBookingResponse(response);
        CommonStepDefinitions.setResponse(response);
    }

    @When("I update the created booking with missing {string}")
    public void i_update_the_created_booking_with_missing_field(String field) {
        retrieveAuthToken();
        createdBookingId = CommonStepDefinitions.getCreatedBookingId();
        Assertions.assertNotNull(createdBookingId, "Created booking ID should not be null");
        
        bookingRequest = TestDataBuilder.createBookingWithoutField(field);
        response = bookingService.updateBooking(createdBookingId, bookingRequest, authToken);
        bookingResponse = CommonStepDefinitions.safeDeserializeBookingResponse(response);
        CommonStepDefinitions.setResponse(response);
    }

    @When("I update the created booking with empty {string}")
    public void i_update_the_created_booking_with_empty_field(String field) {
        retrieveAuthToken();
        createdBookingId = CommonStepDefinitions.getCreatedBookingId();
        Assertions.assertNotNull(createdBookingId, "Created booking ID should not be null");
        
        bookingRequest = TestDataBuilder.createBookingWithEmptyField(field);
        response = bookingService.updateBooking(createdBookingId, bookingRequest, authToken);
        bookingResponse = CommonStepDefinitions.safeDeserializeBookingResponse(response);
        CommonStepDefinitions.setResponse(response);
    }

    @When("I update the created booking with firstname {string} and lastname {string}")
    public void i_update_the_created_booking_with_firstname_and_lastname(String firstname, String lastname) {
        retrieveAuthToken();
        createdBookingId = CommonStepDefinitions.getCreatedBookingId();
        Assertions.assertNotNull(createdBookingId, "Created booking ID should not be null");
        
        bookingRequest = TestDataBuilder.createValidBooking();
        bookingRequest.setFirstname(firstname);
        bookingRequest.setLastname(lastname);
        response = bookingService.updateBooking(createdBookingId, bookingRequest, authToken);
        bookingResponse = CommonStepDefinitions.safeDeserializeBookingResponse(response);
        CommonStepDefinitions.setResponse(response);
    }

    @When("I update the created booking with null depositpaid")
    public void i_update_the_created_booking_with_null_depositpaid() {
        retrieveAuthToken();
        createdBookingId = CommonStepDefinitions.getCreatedBookingId();
        Assertions.assertNotNull(createdBookingId, "Created booking ID should not be null");
        
        bookingRequest = TestDataBuilder.createBookingWithoutField("depositpaid");
        response = bookingService.updateBooking(createdBookingId, bookingRequest, authToken);
        bookingResponse = CommonStepDefinitions.safeDeserializeBookingResponse(response);
        CommonStepDefinitions.setResponse(response);
    }

    @Then("the booking should be updated successfully")
    public void the_booking_should_be_updated_successfully() {
        // Use common validation method
        CommonStepDefinitions.validateBookingDetailsMatch(bookingResponse, bookingRequest);
    }
}

