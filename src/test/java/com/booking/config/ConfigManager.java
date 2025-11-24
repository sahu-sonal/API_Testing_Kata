package com.booking.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
    private static final Properties properties = new Properties();
    public static final String BASE_URL;
    public static final String AUTH_ENDPOINT;
    public static final String BOOKING_ENDPOINT;
    public static final String USERNAME;
    public static final String PASSWORD;

    static {
        try {
            FileInputStream fileInputStream = new FileInputStream("src/test/resources/application.properties");
            properties.load(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load application.properties file", e);
        }
    }

    static {
        // Initialize constants after properties are loaded
        BASE_URL = getBaseUrl();
        AUTH_ENDPOINT = getAuthEndpoint();
        BOOKING_ENDPOINT = getBookingEndpoint();
        USERNAME = getUsername();
        PASSWORD = getPassword();
    }

    public static String getBaseUrl() {
        return get("api.base.url");
    }

    public static String getAuthEndpoint() {
        return BASE_URL + get("api.auth.endpoint");
    }

    public static String getBookingEndpoint() {
        return BASE_URL + get("api.booking.endpoint");
    }

    public static String getUsername() {
        return get("api.username");
    }

    public static String getPassword() {
        return get("api.password");
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
