package com.booking.TesNGBooking;

import com.booking.impl.BookingServiceImpl;
import com.booking.models.GetBookingByRoomResponse;
import com.booking.utils.Commons;
import com.booking.utils.TestSuiteSetup;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeleteBookingTest extends TestSuiteSetup {
    @Test(groups = {"sanity","regression"})
    @Epic("Booking Service API Tests")
    @Severity(SeverityLevel.BLOCKER) @Feature("Delete Booking") @Owner("Sivakumari")
    public void deleteBooking() {
        BookingServiceImpl bookingService = new BookingServiceImpl(accessToken);
        Commons.setAllureLifecycle("Delete a Existing Booking");
        try {
            int id = getBookingIdToDelete(bookingService);
            Response response = bookingService.deleteBookingDetails(id);
            Assert.assertEquals(response.statusCode(),200);
            Assert.assertTrue(response.jsonPath().get("success").equals(true));
        }
        catch (Exception e) {
            e.printStackTrace();
            // ✅ Fail the test with proper message
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }

    @Test(groups = {"sanity","regression"})
    @Epic("Booking Service API Tests")
    @Severity(SeverityLevel.BLOCKER) @Feature("Delete Booking") @Owner("Sivakumari")
    @Description("Delete a Booking that Does not exist")
    public void deleteInvalidBooking() {
        BookingServiceImpl bookingService = new BookingServiceImpl(accessToken);
        Commons.setAllureLifecycle("Delete a Booking that Does not exist");
        try {
            Response response = bookingService.deleteBookingDetails(100);
            Assert.assertEquals(response.statusCode(),500);
            Assert.assertEquals(response.jsonPath().getString("error"),"Failed to delete booking");
        }
        catch (Exception e) {
            e.printStackTrace();
            // ✅ Fail the test with proper message
            Assert.fail("Test failed due to exception: " + e.getMessage());
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
