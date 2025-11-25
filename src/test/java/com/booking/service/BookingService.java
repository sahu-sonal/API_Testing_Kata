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

    public Response updateBooking(Integer bookingId, Booking booking, String token) {
        return given()
                .baseUri(ConfigManager.BASE_URL)
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(booking)
                .when()
                .put(ConfigManager.BOOKING_ENDPOINT + "/" + bookingId)
                .then()
                .extract()
                .response();
    }

    public Response updateBookingWithoutAuth(Integer bookingId, Booking booking) {
        return given()
                .baseUri(ConfigManager.BASE_URL)
                .contentType(ContentType.JSON)
                .body(booking)
                .when()
                .put(ConfigManager.BOOKING_ENDPOINT + "/" + bookingId)
                .then()
                .extract()
                .response();
    }

    public Response updateBookingWithInvalidToken(Integer bookingId, Booking booking, String invalidToken) {
        return given()
                .baseUri(ConfigManager.BASE_URL)
                .contentType(ContentType.JSON)
                .cookie("token", invalidToken)
                .body(booking)
                .when()
                .put(ConfigManager.BOOKING_ENDPOINT + "/" + bookingId)
                .then()
                .extract()
                .response();
    }

    public Response updateBookingWithEmptyToken(Integer bookingId, Booking booking) {
        return given()
                .baseUri(ConfigManager.BASE_URL)
                .contentType(ContentType.JSON)
                .cookie("token", "")
                .body(booking)
                .when()
                .put(ConfigManager.BOOKING_ENDPOINT + "/" + bookingId)
                .then()
                .extract()
                .response();
    }

    public Response partialUpdateBooking(Integer bookingId, Booking booking, String token) {
        return given()
                .baseUri(ConfigManager.BASE_URL)
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(booking)
                .when()
                .patch(ConfigManager.BOOKING_ENDPOINT + "/" + bookingId)
                .then()
                .extract()
                .response();
    }

    public Response partialUpdateBookingWithoutAuth(Integer bookingId, Booking booking) {
        return given()
                .baseUri(ConfigManager.BASE_URL)
                .contentType(ContentType.JSON)
                .body(booking)
                .when()
                .patch(ConfigManager.BOOKING_ENDPOINT + "/" + bookingId)
                .then()
                .extract()
                .response();
    }

    public Response partialUpdateBookingWithInvalidToken(Integer bookingId, Booking booking, String invalidToken) {
        return given()
                .baseUri(ConfigManager.BASE_URL)
                .contentType(ContentType.JSON)
                .cookie("token", invalidToken)
                .body(booking)
                .when()
                .patch(ConfigManager.BOOKING_ENDPOINT + "/" + bookingId)
                .then()
                .extract()
                .response();
    }

    public Response partialUpdateBookingWithEmptyToken(Integer bookingId, Booking booking) {
        return given()
                .baseUri(ConfigManager.BASE_URL)
                .contentType(ContentType.JSON)
                .cookie("token", "")
                .body(booking)
                .when()
                .patch(ConfigManager.BOOKING_ENDPOINT + "/" + bookingId)
                .then()
                .extract()
                .response();
    }

    public Response getBookingById(Integer bookingId, String token) {
        return given()
                .baseUri(ConfigManager.BASE_URL)
                .cookie("token", token)
                .when()
                .get(ConfigManager.BOOKING_ENDPOINT + "/" + bookingId)
                .then()
                .extract()
                .response();
    }

    public Response getBookingByIdWithoutAuth(Integer bookingId) {
        return given()
                .baseUri(ConfigManager.BASE_URL)
                .when()
                .get(ConfigManager.BOOKING_ENDPOINT + "/" + bookingId)
                .then()
                .extract()
                .response();
    }

    public Response getBookingByIdWithInvalidToken(Integer bookingId, String invalidToken) {
        return given()
                .baseUri(ConfigManager.BASE_URL)
                .cookie("token", invalidToken)
                .when()
                .get(ConfigManager.BOOKING_ENDPOINT + "/" + bookingId)
                .then()
                .extract()
                .response();
    }

    public Response getBookingByIdWithEmptyToken(Integer bookingId) {
        return given()
                .baseUri(ConfigManager.BASE_URL)
                .cookie("token", "")
                .when()
                .get(ConfigManager.BOOKING_ENDPOINT + "/" + bookingId)
                .then()
                .extract()
                .response();
    }
}

