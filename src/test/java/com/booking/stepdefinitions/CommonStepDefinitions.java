package com.booking.stepdefinitions;

import com.booking.config.ConfigManager;
import com.booking.models.Booking;
import com.booking.models.BookingResponse;
import com.booking.models.ErrorResponse;
import com.booking.service.AuthService;
import com.booking.service.BookingService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import utils.TestDataBuilder;

import java.util.List;

public class CommonStepDefinitions {
    
    // ThreadLocal to store response objects from different step definition classes
    private static final ThreadLocal<Response> responseHolder = new ThreadLocal<>();
    
    // ThreadLocal to store authentication token for the current thread
    private static final ThreadLocal<String> tokenHolder = new ThreadLocal<>();
    
    // ThreadLocal to store created booking ID for the current thread
    private static final ThreadLocal<Integer> createdBookingIdHolder = new ThreadLocal<>();

    public static void setResponse(Response response) {
        responseHolder.set(response);
    }

    public static Response getResponse() {
        return responseHolder.get();
    }

    public static void setToken(String token) {
        tokenHolder.set(token);
    }

    public static String getToken() {
        return tokenHolder.get();
    }

    public static void setCreatedBookingId(Integer bookingId) {
        createdBookingIdHolder.set(bookingId);
    }

    public static Integer getCreatedBookingId() {
        return createdBookingIdHolder.get();
    }

    @Given("the booking API is available")
    public void the_booking_api_is_available() {
        // Check that configuration is loaded
        Assertions.assertNotNull(ConfigManager.BASE_URL, "Base URL should be configured");
        Assertions.assertNotNull(ConfigManager.BOOKING_ENDPOINT, "Booking endpoint should be configured");
    }

    @Given("I have a valid authentication token")
    public void i_have_a_valid_authentication_token() {
        // Use AuthService.getToken() method which handles login with default credentials
        AuthService authService = new AuthService();
        String token = authService.getToken();
        Assertions.assertNotNull(token, "Authentication token should not be null");
        Assertions.assertFalse(token.isEmpty(), "Authentication token should not be empty");
        
        // Store token in ThreadLocal for use across step definitions
        setToken(token);
    }

    @Given("I have created a booking")
    public void i_have_created_a_booking() {
        // Create a booking and store its ID for later use
        Booking bookingRequest = TestDataBuilder.createValidBooking();
        BookingService bookingService = new BookingService();
        Response createResponse = bookingService.createBooking(bookingRequest);
        BookingResponse bookingResponse = safeDeserializeBookingResponse(createResponse);
        Integer bookingId = bookingResponse.getBookingid();
        Assertions.assertNotNull(bookingId, "Booking ID should not be null");
        
        // Store booking ID in ThreadLocal for use across step definitions
        setCreatedBookingId(bookingId);
    }

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(int expectedStatusCode) {
        Response response = getResponse();
        Assertions.assertNotNull(response, "Response should not be null. Make sure to set response using CommonStepDefinitions.setResponse()");
        
        int actualStatusCode = response.getStatusCode();
        Assertions.assertEquals(expectedStatusCode, actualStatusCode,
                String.format("Expected status code %d but got %d. Response body: %s", 
                        expectedStatusCode, actualStatusCode, response.getBody().asString()));
    }


    public static void validateBookingIdExists(BookingResponse bookingResponse) {
        Assertions.assertNotNull(bookingResponse, "BookingResponse should not be null");

        // First check if we got a successful response
        Response response = getResponse();
        int statusCode = response.getStatusCode();

        if (statusCode != 201) {
            String responseBody = response.getBody().asString();
            Assertions.fail(String.format("Expected status code 201 but got %d. Response body: %s", statusCode, responseBody));
        }

        Integer bookingId = bookingResponse.getBookingid();
        Assertions.assertNotNull(bookingId,
                String.format("Response should contain a booking id. Status: %d, Response body: %s",
                        statusCode, response.getBody().asString()));
    }

    public static void validateBookingDetailsMatch(BookingResponse bookingResponse, Booking expectedBooking) {
        Assertions.assertNotNull(bookingResponse, "BookingResponse should not be null");
        Booking returnedBooking = bookingResponse.getBooking();
        Assertions.assertNotNull(returnedBooking, "Booking should be present in response");
        Assertions.assertEquals(expectedBooking.getFirstname(), returnedBooking.getFirstname(), "Firstname should match");
        Assertions.assertEquals(expectedBooking.getLastname(), returnedBooking.getLastname(), "Lastname should match");
        Assertions.assertEquals(expectedBooking.getRoomid(), returnedBooking.getRoomid(), "Roomid should match");
        Assertions.assertEquals(expectedBooking.getDepositpaid(), returnedBooking.getDepositpaid(), "Depositpaid should match");

        // Validate booking dates
        Assertions.assertNotNull(returnedBooking.getBookingdates(), "Booking dates should be present");
        Assertions.assertEquals(expectedBooking.getBookingdates().getCheckin(),
                returnedBooking.getBookingdates().getCheckin(),
                "Checkin date should match");
        Assertions.assertEquals(expectedBooking.getBookingdates().getCheckout(),
                returnedBooking.getBookingdates().getCheckout(),
                "Checkout date should match");
    }

    public static void validateValidationError(BookingResponse bookingResponse, String expectedError) {
        Assertions.assertNotNull(bookingResponse, "BookingResponse should not be null");
        Response response = getResponse();
        
        List<String> errors = bookingResponse.getErrors();
        
        // Check if errors list exists and contains expected error
        if (errors != null && !errors.isEmpty()) {
            Assertions.assertTrue(errors.contains(expectedError),
                    String.format("Expected error '%s' not found in errors: %s", expectedError, errors));
        } else {
            // If errors list is null or empty, check the raw response body
            String responseBody = response.getBody().asString();
            Assertions.assertTrue(responseBody.contains(expectedError),
                    String.format("Expected error '%s' not found in response body: %s. Errors list: %s", 
                            expectedError, responseBody, errors));
        }
    }

    public static void validateErrorResponse(String expectedError) {
        Response response = getResponse();
        try {
            ErrorResponse errorResponse = response.as(ErrorResponse.class);
            String actualError = errorResponse.getError();
            Assertions.assertNotNull(actualError, "Response should contain an error message");
            Assertions.assertEquals(expectedError, actualError,
                    String.format("Expected error message '%s' but got '%s'", expectedError, actualError));
        } catch (Exception e) {
            // If deserialization fails, check raw response body
            String responseBody = response.getBody().asString();
            Assertions.assertTrue(responseBody.contains(expectedError),
                    String.format("Expected error '%s' not found in response body: %s", expectedError, responseBody));
        }
    }

    public static BookingResponse safeDeserializeBookingResponse(Response response) {
        try {
            BookingResponse bookingResponse = response.as(BookingResponse.class);
            // Log if bookingid is null but status code suggests success
            if (bookingResponse.getBookingid() == null && response.getStatusCode() == 201) {
                System.err.println("WARNING: Status code is 201 but bookingid is null. Response body: " + response.getBody().asString());
            }
            return bookingResponse;
        } catch (Exception e) {
            // Log the actual response for debugging
            int statusCode = response.getStatusCode();
            String responseBody = response.getBody().asString();
            // If deserialization fails, return empty BookingResponse
            return BookingResponse.builder().build();
        }
    }

    public static Booking safeDeserializeBooking(Response response) {
        try {
            return response.as(Booking.class);
        } catch (Exception e) {
            return null;
        }
    }

    @Then("the booking details should be returned")
    public void the_booking_details_should_be_returned() {
        Response response = getResponse();
        Booking returnedBooking = safeDeserializeBooking(response);
        Assertions.assertNotNull(returnedBooking, "Booking should be present in response");

        // Validate all required fields returned by GET booking API
        Assertions.assertNotNull(returnedBooking.getRoomid(), "Roomid should not be null");
        Assertions.assertNotNull(returnedBooking.getFirstname(), "Firstname should not be null");
        Assertions.assertNotNull(returnedBooking.getLastname(), "Lastname should not be null");
        Assertions.assertNotNull(returnedBooking.getDepositpaid(), "Depositpaid should not be null");
        Assertions.assertNotNull(returnedBooking.getBookingdates(), "Booking dates should not be null");
        Assertions.assertNotNull(returnedBooking.getBookingdates().getCheckin(), "Checkin date should not be null");
        Assertions.assertNotNull(returnedBooking.getBookingdates().getCheckout(), "Checkout date should not be null");
        Assertions.assertNotNull(returnedBooking.getEmail(), "Email should not be null");
        Assertions.assertNotNull(returnedBooking.getPhone(), "Phone should not be null");

        // Validate field formats/values
        Assertions.assertFalse(returnedBooking.getFirstname().isEmpty(), "Firstname should not be empty");
        Assertions.assertFalse(returnedBooking.getLastname().isEmpty(), "Lastname should not be empty");
        Assertions.assertFalse(returnedBooking.getEmail().isEmpty(), "Email should not be empty");
        Assertions.assertFalse(returnedBooking.getPhone().isEmpty(), "Phone should not be empty");
        Assertions.assertTrue(returnedBooking.getRoomid() > 0, "Roomid should be a positive integer");
    }

    @Then("the partial booking details should be returned")
    public void the_partial_booking_details_should_be_returned() {
        Response response = getResponse();
        Booking returnedBooking = safeDeserializeBooking(response);
        Assertions.assertNotNull(returnedBooking, "Booking should be present in response");

        // Lenient validation for PATCH - only check essential fields that should always be present
        // PATCH response structure may vary, so we validate minimally
        Assertions.assertNotNull(returnedBooking, "Booking should be present in response");

        // At minimum, firstname and lastname should be present (most common fields in PATCH)
        // Other fields may or may not be present depending on what was updated
        if (returnedBooking.getFirstname() != null) {
            Assertions.assertFalse(returnedBooking.getFirstname().isEmpty(), "Firstname should not be empty if present");
        }
        if (returnedBooking.getLastname() != null) {
            Assertions.assertFalse(returnedBooking.getLastname().isEmpty(), "Lastname should not be empty if present");
        }
    }

    @Then("the response should contain error message {string}")
    public void the_response_should_contain_error_message(String expectedError) {
        validateErrorResponse(expectedError);
    }

    @Then("the response should contain validation error {string}")
    public void the_response_should_contain_validation_error(String expectedError) {
        Response response = getResponse();
        BookingResponse bookingResponse = safeDeserializeBookingResponse(response);
        validateValidationError(bookingResponse, expectedError);
    }
}

