package com.booking.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingResponse {
    @JsonProperty("bookingid")
    private Integer bookingid;

    @JsonProperty("roomid")
    private Integer roomid;

    @JsonProperty("firstname")
    private String firstname;

    @JsonProperty("lastname")
    private String lastname;

    @JsonProperty("depositpaid")
    private Boolean depositpaid;

    @JsonProperty("bookingdates")
    private BookingDates bookingdates;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("errors")
    private List<String> errors;

    public Booking getBooking() {
        if (roomid == null && firstname == null && lastname == null) {
            return null;
        }
        return Booking.builder()
                .roomid(roomid)
                .firstname(firstname)
                .lastname(lastname)
                .depositpaid(depositpaid)
                .bookingdates(bookingdates)
                .email(email)
                .phone(phone)
                .build();
    }
}

