package com.booking.stepdefinitions;

import com.booking.config.ConfigManager;
import com.booking.models.AuthResponse;
import com.booking.service.AuthService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import utils.StringUtils;

public class AuthStepDefinitions {

    private static final Logger logger = LogManager.getLogger(AuthStepDefinitions.class);

    private Response response;
    private AuthResponse authResponse;
    private AuthService authService;

    public AuthStepDefinitions() {
        authService = new AuthService();
        logger.info("AuthStepDefinitions initialized");
    }

    // ==================== GIVEN STEPS ====================

    @Given("the authentication API is available")
    public void the_authentication_api_is_available() {
        logger.info("Validating authentication API configuration is available");

        Assertions.assertNotNull(ConfigManager.BASE_URL, "Base URL should be configured");
        Assertions.assertNotNull(ConfigManager.AUTH_ENDPOINT, "Auth endpoint should be configured");

        logger.debug("Configuration values - BASE_URL: {}, AUTH_ENDPOINT: {}",
                ConfigManager.BASE_URL, ConfigManager.AUTH_ENDPOINT);
    }

    // ==================== WHEN STEPS ====================

    @When("I send a POST request to {string} with valid credentials")
    public void i_send_a_post_request_to_with_valid_credentials(String endpoint) {
        logger.info("Sending POST request to endpoint '{}' with valid credentials", endpoint);

        response = authService.loginRequest(ConfigManager.USERNAME, ConfigManager.PASSWORD);
        logger.debug("Raw response body: {}", response.asString());

        authResponse = response.as(AuthResponse.class);
        logger.info("Login request completed with status code: {}", response.getStatusCode());

        CommonStepDefinitions.setResponse(response);
    }

    @When("I send a POST request to {string} with username {string} and password {string}")
    public void i_send_a_post_request_to_with_username_and_password(String endpoint, String username, String password) {
        logger.info("Sending POST request to '{}' with custom credentials", endpoint);

        String usernameValue = StringUtils.normalizeString(username);
        String passwordValue = StringUtils.normalizeString(password);

        logger.debug("Normalized credentials: username='{}', password='{}'", usernameValue, passwordValue);

        response = authService.loginRequest(usernameValue, passwordValue);
        logger.debug("Raw response body: {}", response.asString());

        authResponse = response.as(AuthResponse.class);
        logger.info("Login request completed with status code: {}", response.getStatusCode());

        CommonStepDefinitions.setResponse(response);
    }

    // ==================== THEN STEPS ====================

    @Then("the response should contain a token")
    public void the_response_should_contain_a_token() {
        logger.info("Validating token existence in response");

        String token = authResponse.getToken();
        logger.debug("Extracted token: {}", token);

        Assertions.assertNotNull(token, "Response should contain a token");
        Assertions.assertFalse(token.isEmpty(), "Token should not be empty");
    }

    @Then("^the response should contain an error message \"(.+)\"$")
    public void the_response_should_contain_an_error_message_with_quoted_text(String expectedErrorMessage) {
        logger.info("Validating error message in response");

        String actualErrorMessage = authResponse.getError();
        logger.debug("Actual error message from response: {}", actualErrorMessage);

        Assertions.assertNotNull(actualErrorMessage, "Response should contain an error message");
        Assertions.assertEquals(expectedErrorMessage, actualErrorMessage,
                String.format("Expected error message '%s' but got '%s'", expectedErrorMessage, actualErrorMessage));
    }
}
