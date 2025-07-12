package com.booking.impl;

import com.booking.models.ConfigReader;
import com.booking.models.Constants;
import com.booking.models.CreateBookingRequest;
import com.booking.utils.ApiUtils;
import com.booking.utils.JsonUtil;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class BookingServiceImpl {
    private final Map<String, String> headerMap;
    private final Map<String, String> cookieMap;

    public BookingServiceImpl(String accessToken) {
        headerMap = new HashMap<>();
        headerMap.put("Content-Type", "application/json");

        cookieMap = new HashMap<>();
        cookieMap.put("token", accessToken);
    }


    public Response postCreateBooking(CreateBookingRequest createBookingRequest) {
        // In case if any additional Header we want to add specific to this API
        HashMap<String, String> headers = new HashMap<>(headerMap);
        String payload = JsonUtil.toJson(createBookingRequest);
        Response response = ApiUtils.post(ConfigReader.BASE_URL, Constants.CREATE_BOOKING , headerMap,cookieMap,payload);
        return response;
    }

}
