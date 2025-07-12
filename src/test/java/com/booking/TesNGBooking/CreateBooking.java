package com.booking.TesNGBooking;

import com.booking.impl.BookingServiceImpl;
import com.booking.models.CreateBookingRequest;
import com.booking.utils.TestSuiteSetup;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.platform.suite.api.Suite;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CreateBooking extends TestSuiteSetup {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @DataProvider(name = "CreateBookingDetails")
    public Object[][] addReasonsNegCases() {
        return new Object[][]{
                //roomid,FirstName,LastName,DespositPaid,email,phone
                {2,"Siva","Kumari",true,"test1@gmail.com","24433234324",200,"Success TestCase to Validate Create Booking"},
                {2,"Siva","Kumari",true,"test1@gmail.com","24433234324",500,"Negative TestCase - Validate Duplicate Create Booking"}

        };
    }

    @Test(dataProvider = "CreateBookingDetails",groups = {"sanity","regression"})
    @Epic("Booking Service API Tests")
    @Severity(SeverityLevel.CRITICAL) @Feature("Create Booking") @Owner("Sivakumari")
    public void CreateBooking(int roomId,String firstName,String lastName,Boolean depositPaid,String email,String phone,int statusCode,String testDesc) {
        BookingServiceImpl bookingService = new BookingServiceImpl(accessToken);
        try {
            Allure.description(testDesc);
            LocalDate checkinDate = LocalDate.now().plusDays(5);
            LocalDate checkoutDate = checkinDate.plusDays(1);
            CreateBookingRequest.BookingDates bookingDates = new CreateBookingRequest.BookingDates(checkinDate.format(formatter),checkoutDate.format(formatter));
            CreateBookingRequest createBookingRequest = new CreateBookingRequest(roomId,firstName,lastName,depositPaid,email,phone,bookingDates);
            Response response = bookingService.postCreateBooking(createBookingRequest);
            Assert.assertEquals(response.statusCode(),statusCode);
        }
        catch (Exception e) {
            System.out.printf(String.valueOf(e));
        }
    }
}
