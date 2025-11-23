package com.booking.stepdefinitions;

import com.booking.config.ConfigManager;
import com.booking.models.AuthResponse;
import com.booking.service.AuthService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

public class AuthStepDefinitions {
    // Store the response and auth response for validation
    private Response response;
    private AuthResponse authResponse;
    private AuthService authService;

    public AuthStepDefinitions() {
        authService = new AuthService();
    }

    // ==================== GIVEN STEPS ====================

    @Given("the authentication API is available")
    public void the_authentication_api_is_available() {
        // Check that configuration is loaded
        Assertions.assertNotNull(ConfigManager.BASE_URL, "Base URL should be configured");
        Assertions.assertNotNull(ConfigManager.AUTH_ENDPOINT, "Auth endpoint should be configured");
    }

    // ==================== WHEN STEPS ====================

    @When("I send a POST request to {string} with valid credentials")
    public void i_send_a_post_request_to_with_valid_credentials(String endpoint) {
        // Use AuthService to send login request with default credentials
        response = authService.loginRequest(ConfigManager.USERNAME, ConfigManager.PASSWORD);
        authResponse = response.as(AuthResponse.class);
    }

    @When("I send a POST request to {string} with username {string} and password {string}")
    public void i_send_a_post_request_to_with_username_and_password(String endpoint, String username, String password) {
        // Handle empty strings - convert to null
        String usernameValue = (username == null || username.trim().isEmpty()) ? null : username;
        String passwordValue = (password == null || password.trim().isEmpty()) ? null : password;
        
        // Use AuthService to send login request
        response = authService.loginRequest(usernameValue, passwordValue);
        authResponse = response.as(AuthResponse.class);
    }

    // ==================== THEN STEPS ====================

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(int expectedStatusCode) {
        // Get actual status code from response
        int actualStatusCode = response.getStatusCode();
        
        // Verify it matches expected status code
        Assertions.assertEquals(expectedStatusCode, actualStatusCode,
                String.format("Expected status code %d but got %d", expectedStatusCode, actualStatusCode));
    }

    @Then("the response should contain a token")
    public void the_response_should_contain_a_token() {
        // Get token from response
        String token = authResponse.getToken();
        
        // Check token exists and is not empty
        Assertions.assertNotNull(token, "Response should contain a token");
        Assertions.assertFalse(token.isEmpty(), "Token should not be empty");
    }

    @Then("^the response should contain an error message \"(.+)\"$")
    public void the_response_should_contain_an_error_message_with_quoted_text(String expectedErrorMessage) {
        // Get error message from response
        String actualErrorMessage = authResponse.getError();
        
        // Check error message exists and matches expected
        Assertions.assertNotNull(actualErrorMessage, "Response should contain an error message");
        Assertions.assertEquals(expectedErrorMessage, actualErrorMessage,
                String.format("Expected error message '%s' but got '%s'", expectedErrorMessage, actualErrorMessage));
    }

}

