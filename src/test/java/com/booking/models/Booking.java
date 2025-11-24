package com.booking.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
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
}
