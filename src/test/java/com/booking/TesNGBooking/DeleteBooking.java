package com.booking.TesNGBooking;

import com.booking.impl.BookingServiceImpl;
import com.booking.models.GetBookingByRoomResponse;
import com.booking.utils.TestSuiteSetup;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeleteBooking extends TestSuiteSetup {

    @Test(groups = {"sanity","regression"})
    @Epic("Booking Service API Tests")
    @Severity(SeverityLevel.BLOCKER) @Feature("Delete Booking") @Owner("Sivakumari")
    public void deleteBooking() {
        BookingServiceImpl bookingService = new BookingServiceImpl(accessToken);
        try {
            String testDesc = "Delete a Valid Booking";
            Allure.description(testDesc);
            int id = getBookingIdToDelete(bookingService);
            Response response = bookingService.deleteBookingDetails(id);
            Assert.assertEquals(response.statusCode(),200);
            Assert.assertTrue(response.jsonPath().get("success").equals(true));
        }
        catch (Exception e) {
            System.out.printf(String.valueOf(e));
        }

    }

    @Test(groups = {"sanity","regression"})
    @Epic("Booking Service API Tests")
    @Severity(SeverityLevel.BLOCKER) @Feature("Delete Booking") @Owner("Sivakumari")
    public void deleteInvalidBooking() {
        BookingServiceImpl bookingService = new BookingServiceImpl(accessToken);
        try {
            String testDesc = "Delete a Booking that Does not exist";
            Allure.description(testDesc);
            Response response = bookingService.deleteBookingDetails(100);
            Assert.assertEquals(response.statusCode(),500);
            Assert.assertEquals(response.jsonPath().getString("error"),"Failed to delete booking");
        }
        catch (Exception e) {
            System.out.printf(String.valueOf(e));
        }

    }

    private int getBookingIdToDelete(BookingServiceImpl bookingService) {
        Map<String,Integer> params = new HashMap<>();
        int bookingID = 0;
        params.put("roomid",1);
        Response response = bookingService.getBookingDetails(params);
        GetBookingByRoomResponse getBookingByRoomResponse = response.as(GetBookingByRoomResponse.class);

        List<GetBookingByRoomResponse.Booking> bookings = getBookingByRoomResponse.getBookings();

        for (GetBookingByRoomResponse.Booking b : bookings) {
            if(b.getFirstname().equals("Siva")) {
                bookingID = b.getBookingid();
                break;
            }
        }
        return bookingID;
    }
}
