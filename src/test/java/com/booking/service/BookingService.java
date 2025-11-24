package com.booking.service;

import com.booking.config.ConfigManager;
import com.booking.models.Booking;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class BookingService {

    // Create a new booking
    public Response createBooking(Booking booking) {
        return given()
                .baseUri(ConfigManager.BASE_URL)
                .contentType(ContentType.JSON)
                .body(booking)
                .when()
                .post(ConfigManager.BOOKING_ENDPOINT)
                .then()
                .extract()
                .response();
    }
}

