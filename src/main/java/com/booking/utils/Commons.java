package com.booking.utils;

import com.booking.models.CreateBookingRequest;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;

public class Commons {

    public static void setAllureLifecycle(String testDesc) {
        AllureLifecycle lifecycle = Allure.getLifecycle();
        lifecycle.updateTestCase(testResult -> {testResult.setName(testDesc);testResult.setDescription("Scenario:"+testDesc);});
    }

    public static CreateBookingRequest getCreateBooking(int roomId, String firstName, String lastName, Boolean depositPaid, String email, String phone, String checkinDate, String checkoutDate) {
        CreateBookingRequest.BookingDates bookingDates = new CreateBookingRequest.BookingDates(checkinDate,checkoutDate);
        CreateBookingRequest createBookingRequest = new CreateBookingRequest(roomId,firstName,lastName,depositPaid,email,phone,bookingDates);
        return createBookingRequest;
    }
}
