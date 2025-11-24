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
    
    @JsonProperty("booking")
    private Booking booking;
    
    @JsonProperty("errors")
    private List<String> errors;
}

