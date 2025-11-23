package com.booking.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Model class representing authentication response
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    @JsonProperty("token")
    private String token;
    @JsonProperty("error")
    private String error;
}

