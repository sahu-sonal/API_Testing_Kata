package com.booking.service;

import com.booking.config.ConfigManager;
import com.booking.models.AuthRequest;
import com.booking.models.AuthResponse;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AuthService {

    public Response loginRequest(String username, String password) {
        // Create request object with username and password
        AuthRequest request = new AuthRequest(username, password);

        // Send POST request to login endpoint and return Response
        return given()
                .baseUri(ConfigManager.BASE_URL)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(ConfigManager.AUTH_ENDPOINT)
                .then()
                .extract()
                .response();
    }

    public AuthResponse login(String username, String password) {
        Response response = loginRequest(username, password);
        return response.as(AuthResponse.class);
    }

    public AuthResponse login() {
        return login(ConfigManager.USERNAME, ConfigManager.PASSWORD);
    }

    public String getToken() {
        return login().getToken();
    }
}

