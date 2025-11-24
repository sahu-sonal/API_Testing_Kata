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

    public Response deleteBooking(Integer bookingId, String token) {
        return given()
                .baseUri(ConfigManager.BASE_URL)
                .cookie("token", token)
                .when()
                .delete(ConfigManager.BOOKING_ENDPOINT + "/" + bookingId)
                .then()
                .extract()
                .response();
    }

    public Response deleteBookingWithoutAuth(Integer bookingId) {
        return given()
                .baseUri(ConfigManager.BASE_URL)
                .when()
                .delete(ConfigManager.BOOKING_ENDPOINT + "/" + bookingId)
                .then()
                .extract()
                .response();
    }

    public Response deleteBookingWithInvalidToken(Integer bookingId, String invalidToken) {
        return given()
                .baseUri(ConfigManager.BASE_URL)
                .cookie("token", invalidToken)
                .when()
                .delete(ConfigManager.BOOKING_ENDPOINT + "/" + bookingId)
                .then()
                .extract()
                .response();
    }

    public Response deleteBookingWithEmptyToken(Integer bookingId) {
        return given()
                .baseUri(ConfigManager.BASE_URL)
                .cookie("token", "")
                .when()
                .delete(ConfigManager.BOOKING_ENDPOINT + "/" + bookingId)
                .then()
                .extract()
                .response();
    }
}

