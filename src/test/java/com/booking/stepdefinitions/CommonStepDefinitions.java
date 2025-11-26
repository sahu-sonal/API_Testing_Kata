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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import utils.TestDataBuilder;

import java.util.List;

public class CommonStepDefinitions {

    private static final Logger logger = LogManager.getLogger(CommonStepDefinitions.class);

    private static final ThreadLocal<Response> responseHolder = new ThreadLocal<>();
    private static final ThreadLocal<String> tokenHolder = new ThreadLocal<>();
    private static final ThreadLocal<Integer> createdBookingIdHolder = new ThreadLocal<>();

    public static void setResponse(Response response) {
        logger.info("Storing response in ThreadLocal");
        if (response != null) {
            logger.debug("Response status code being stored: {}", response.getStatusCode());
        } else {
            logger.warn("Attempted to store a null response");
        }
        responseHolder.set(response);
    }

    public static Response getResponse() {
        Response response = responseHolder.get();
        if (response == null) {
            logger.warn("No response found in ThreadLocal");
        } else {
            logger.debug("Retrieved response with status code: {}", response.getStatusCode());
        }
        return response;
    }

    public static void setToken(String token) {
        logger.info("Storing authentication token");
        logger.debug("Token value: {}", token);
        tokenHolder.set(token);
    }

    public static String getToken() {
        String token = tokenHolder.get();
        logger.debug("Retrieved token from ThreadLocal: {}", token);
        return token;
    }

    public static void setCreatedBookingId(Integer bookingId) {
        logger.info("Storing created booking ID: {}", bookingId);
        createdBookingIdHolder.set(bookingId);
    }

    public static Integer getCreatedBookingId() {
        Integer id = createdBookingIdHolder.get();
        logger.debug("Retrieved created booking ID from ThreadLocal: {}", id);
        return id;
    }

    @Given("the booking API is available")
    public void the_booking_api_is_available() {
        logger.info("Checking if booking API is available");
        Assertions.assertNotNull(ConfigManager.BASE_URL, "Base URL should be configured");
        Assertions.assertNotNull(ConfigManager.BOOKING_ENDPOINT, "Booking endpoint should be configured");
        logger.debug("ConfigManager.BASE_URL={}, ConfigManager.BOOKING_ENDPOINT={}",
                ConfigManager.BASE_URL, ConfigManager.BOOKING_ENDPOINT);
    }

    @Given("I have a valid authentication token")
    public void i_have_a_valid_authentication_token() {
        logger.info("Ensuring a valid authentication token exists");
        String existingToken = getToken();

        if (existingToken == null || existingToken.isEmpty()) {
            logger.info("No existing token found, requesting new token from AuthService");
            AuthService authService = new AuthService();
            String token = authService.getToken();
            logger.debug("Token received from AuthService: {}", token);

            Assertions.assertNotNull(token, "Authentication token should not be null");
            Assertions.assertFalse(token.isEmpty(), "Authentication token should not be empty");

            setToken(token);
        } else {
            logger.info("Using existing authentication token");
        }
    }

    @Given("I have created a booking")
    public void i_have_created_a_booking() {
        logger.info("Creating a booking for test setup");
        BookingService bookingService = new BookingService();
        int maxRetries = 3;
        Integer bookingId = null;

        for (int attempt = 0; attempt < maxRetries; attempt++) {
            logger.debug("Booking creation attempt {}", attempt + 1);
            Booking bookingRequest = TestDataBuilder.createValidBooking();
            Response createResponse = bookingService.createBooking(bookingRequest);
            int statusCode = createResponse.getStatusCode();
            logger.debug("Create booking response status: {}", statusCode);

            if (statusCode == 201) {
                BookingResponse bookingResponse = safeDeserializeBookingResponse(createResponse);
                bookingId = bookingResponse.getBookingid();
                logger.info("Booking created successfully with ID {}", bookingId);
                break;
            } else {
                logger.warn("Booking creation failed with status {} on attempt {}", statusCode, attempt + 1);
            }
        }

        Assertions.assertNotNull(bookingId, "Failed to create booking after " + maxRetries + " attempts due to 409 conflicts");
        setCreatedBookingId(bookingId);
    }

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(int expectedStatusCode) {
        logger.info("Validating response status code. Expected: {}", expectedStatusCode);
        Response response = getResponse();
        Assertions.assertNotNull(response, "Response should not be null. Make sure to set response using CommonStepDefinitions.setResponse()");

        int actualStatusCode = response.getStatusCode();
        logger.debug("Actual response status code: {}", actualStatusCode);

        Assertions.assertEquals(expectedStatusCode, actualStatusCode,
                String.format("Expected status code %d but got %d. Response body: %s",
                        expectedStatusCode, actualStatusCode, response.getBody().asString()));
    }

    public static void validateBookingIdExists(BookingResponse bookingResponse) {
        logger.info("Validating that booking ID exists in response");
        Assertions.assertNotNull(bookingResponse, "BookingResponse should not be null");

        Response response = getResponse();
        Assertions.assertNotNull(response, "Response should not be null when validating booking ID");

        int statusCode = response.getStatusCode();
        logger.debug("Response status code while validating booking ID: {}", statusCode);

        if (statusCode != 201) {
            String responseBody = response.getBody().asString();
            logger.error("Expected status 201 but got {}. Response body: {}", statusCode, responseBody);
            Assertions.fail(String.format("Expected status code 201 but got %d. Response body: %s", statusCode, responseBody));
        }

        Integer bookingId = bookingResponse.getBookingid();
        logger.debug("Extracted booking ID from response: {}", bookingId);

        Assertions.assertNotNull(bookingId,
                String.format("Response should contain a booking id. Status: %d, Response body: %s",
                        statusCode, response.getBody().asString()));
    }

    public static void validateBookingDetailsMatch(BookingResponse bookingResponse, Booking expectedBooking) {
        logger.info("Validating that booking details match expected values");
        Assertions.assertNotNull(bookingResponse, "BookingResponse should not be null");

        Booking returnedBooking = bookingResponse.getBooking();
        Assertions.assertNotNull(returnedBooking, "Booking should be present in response");

        logger.debug("Comparing firstname: expected={}, actual={}", expectedBooking.getFirstname(), returnedBooking.getFirstname());
        Assertions.assertEquals(expectedBooking.getFirstname(), returnedBooking.getFirstname(), "Firstname should match");

        logger.debug("Comparing lastname: expected={}, actual={}", expectedBooking.getLastname(), returnedBooking.getLastname());
        Assertions.assertEquals(expectedBooking.getLastname(), returnedBooking.getLastname(), "Lastname should match");

        logger.debug("Comparing roomid: expected={}, actual={}", expectedBooking.getRoomid(), returnedBooking.getRoomid());
        Assertions.assertEquals(expectedBooking.getRoomid(), returnedBooking.getRoomid(), "Roomid should match");

        logger.debug("Comparing depositpaid: expected={}, actual={}", expectedBooking.getDepositpaid(), returnedBooking.getDepositpaid());
        Assertions.assertEquals(expectedBooking.getDepositpaid(), returnedBooking.getDepositpaid(), "Depositpaid should match");

        Assertions.assertNotNull(returnedBooking.getBookingdates(), "Booking dates should be present");

        logger.debug("Comparing checkin: expected={}, actual={}",
                expectedBooking.getBookingdates().getCheckin(), returnedBooking.getBookingdates().getCheckin());
        Assertions.assertEquals(expectedBooking.getBookingdates().getCheckin(),
                returnedBooking.getBookingdates().getCheckin(),
                "Checkin date should match");

        logger.debug("Comparing checkout: expected={}, actual={}",
                expectedBooking.getBookingdates().getCheckout(), returnedBooking.getBookingdates().getCheckout());
        Assertions.assertEquals(expectedBooking.getBookingdates().getCheckout(),
                returnedBooking.getBookingdates().getCheckout(),
                "Checkout date should match");
    }

    public static void validateValidationError(BookingResponse bookingResponse, String expectedError) {
        logger.info("Validating validation error contains: {}", expectedError);
        Assertions.assertNotNull(bookingResponse, "BookingResponse should not be null");

        Response response = getResponse();
        Assertions.assertNotNull(response, "Response should not be null when validating validation error");

        List<String> errors = bookingResponse.getErrors();
        logger.debug("Validation errors from response: {}", errors);

        if (errors != null && !errors.isEmpty()) {
            Assertions.assertTrue(errors.contains(expectedError),
                    String.format("Expected error '%s' not found in errors: %s", expectedError, errors));
        } else {
            String responseBody = response.getBody().asString();
            logger.warn("No structured errors list present, checking raw body");
            logger.debug("Raw response body: {}", responseBody);

            Assertions.assertTrue(responseBody.contains(expectedError),
                    String.format("Expected error '%s' not found in response body: %s. Errors list: %s",
                            expectedError, responseBody, errors));
        }
    }

    public static void validateErrorResponse(String expectedError) {
        logger.info("Validating error response contains: {}", expectedError);

        Response response = getResponse();
        Assertions.assertNotNull(response, "Response should not be null when validating error response");

        try {
            ErrorResponse errorResponse = response.as(ErrorResponse.class);
            String actualError = errorResponse.getError();
            logger.debug("Deserialized error message: {}", actualError);

            Assertions.assertNotNull(actualError, "Response should contain an error message");
            Assertions.assertEquals(expectedError, actualError,
                    String.format("Expected error message '%s' but got '%s'", expectedError, actualError));
        } catch (Exception e) {
            logger.warn("Failed to deserialize ErrorResponse, falling back to raw body. Exception: {}", e.getMessage());
            String responseBody = response.getBody().asString();
            logger.debug("Raw response body: {}", responseBody);

            Assertions.assertTrue(responseBody.contains(expectedError),
                    String.format("Expected error '%s' not found in response body: %s", expectedError, responseBody));
        }
    }

    public static BookingResponse safeDeserializeBookingResponse(Response response) {
        logger.debug("Attempting to deserialize response to BookingResponse");
        try {
            BookingResponse bookingResponse = response.as(BookingResponse.class);
            logger.debug("BookingResponse deserialization successful");
            return bookingResponse;
        } catch (Exception e) {
            logger.warn("Failed to deserialize BookingResponse: {}", e.getMessage());
            return BookingResponse.builder().build();
        }
    }

    public static Booking safeDeserializeBooking(Response response) {
        logger.debug("Attempting to deserialize response to Booking");
        try {
            Booking booking = response.as(Booking.class);
            logger.debug("Booking deserialization successful");
            return booking;
        } catch (Exception e) {
            logger.warn("Failed to deserialize Booking: {}", e.getMessage());
            return null;
        }
    }

    @Then("the booking details should be returned")
    public void the_booking_details_should_be_returned() {
        logger.info("Validating that full booking details are returned");
        Response response = getResponse();

        BookingResponse bookingResponse = safeDeserializeBookingResponse(response);
        Assertions.assertNotNull(bookingResponse, "BookingResponse should be present in response");

        Booking returnedBooking = bookingResponse.getBooking();
        Assertions.assertNotNull(returnedBooking, "Booking should be present in response");

        Assertions.assertNotNull(returnedBooking.getRoomid(), "Roomid should not be null");
        Assertions.assertNotNull(returnedBooking.getFirstname(), "Firstname should not be null");
        Assertions.assertNotNull(returnedBooking.getLastname(), "Lastname should not be null");
        Assertions.assertNotNull(returnedBooking.getDepositpaid(), "Depositpaid should not be null");
        Assertions.assertNotNull(returnedBooking.getBookingdates(), "Booking dates should not be null");
        Assertions.assertNotNull(returnedBooking.getBookingdates().getCheckin(), "Checkin date should not be null");
        Assertions.assertNotNull(returnedBooking.getBookingdates().getCheckout(), "Checkout date should not be null");
    }

    @Then("the partial booking details should be returned")
    public void the_partial_booking_details_should_be_returned() {
        logger.info("Validating that partial booking details are returned");
        Response response = getResponse();

        Booking returnedBooking = safeDeserializeBooking(response);
        Assertions.assertNotNull(returnedBooking, "Booking should be present in response");

        if (returnedBooking.getFirstname() != null) {
            Assertions.assertFalse(returnedBooking.getFirstname().isEmpty(), "Firstname should not be empty if present");
        }
        if (returnedBooking.getLastname() != null) {
            Assertions.assertFalse(returnedBooking.getLastname().isEmpty(), "Lastname should not be empty if present");
        }
    }

    @Then("the response should contain error message {string}")
    public void the_response_should_contain_error_message(String expectedError) {
        logger.info("Step: validating response contains error message '{}'", expectedError);
        validateErrorResponse(expectedError);
    }

    @Then("the response should contain validation error {string}")
    public void the_response_should_contain_validation_error(String expectedError) {
        logger.info("Step: validating response contains validation error '{}'", expectedError);
        Response response = getResponse();
        BookingResponse bookingResponse = safeDeserializeBookingResponse(response);
        validateValidationError(bookingResponse, expectedError);
    }
}
