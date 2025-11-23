package com.booking.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Model class carrying Auth request
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {
    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;
}

