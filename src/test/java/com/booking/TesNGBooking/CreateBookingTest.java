package com.booking.TesNGBooking;

import com.booking.data.TestDataProvider;
import com.booking.impl.BookingServiceImpl;
import com.booking.models.CreateBookingRequest;
import com.booking.utils.Commons;
import com.booking.utils.TestSuiteSetup;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;

public class CreateBookingTest extends TestSuiteSetup {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Test(dataProvider = "createBookingPositiveCaseData", dataProviderClass = TestDataProvider.class, groups = {"sanity", "regression", "create"})
    @Epic("Booking Service API Tests")
    @Severity(SeverityLevel.BLOCKER)
    @Feature("Create Booking")
    @Owner("Sivakumari")
    public void createBooking(int roomId, String firstName, String lastName, Boolean depositPaid, String email, String phone, int statusCode, String testDesc) {
        BookingServiceImpl bookingService = new BookingServiceImpl(accessToken);
        Commons.setAllureLifecycle(testDesc);
        try {
            LocalDate checkinDate = LocalDate.now().plusDays(13);
            LocalDate checkoutDate = checkinDate.plusDays(1);
            CreateBookingRequest createBookingRequest = Commons.getCreateBooking(roomId, firstName, lastName, depositPaid, email, phone, checkinDate.format(formatter), checkoutDate.format(formatter));
            Response response = bookingService.postCreateBooking(createBookingRequest);
            Assert.assertEquals(response.statusCode(), statusCode);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }

    @Test(dataProvider = "CreateBookingNegativeCaseData", dataProviderClass = TestDataProvider.class, groups = {"sanity", "regression"})
    @Epic("Booking Service API Tests")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Create Booking")
    @Owner("Sivakumari")
    public void bookingFieldsErrorMsg(Integer roomId, String firstName, String lastName, Boolean depositPaid, String email, String phone, int statusCode, List<String> errorMsg, String testDesc) {
        BookingServiceImpl bookingService = new BookingServiceImpl(accessToken);
        Commons.setAllureLifecycle(testDesc);
        try {
            CreateBookingRequest createBookingRequest = Commons.getCreateBooking(roomId, firstName, lastName, depositPaid, email, phone, "2025-08-13", "2025-08-14");
            Response response = bookingService.postCreateBooking(createBookingRequest);
            Assert.assertEquals(response.statusCode(), statusCode,"Status Code is Wrong");
            Assert.assertNotNull(response.jsonPath().getList("errors"), "Error message is null");
            List<String> actualErrors = response.jsonPath().getList("errors");
            Assert.assertEquals(new HashSet<>(actualErrors), new HashSet<>(errorMsg));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }

    @Test(dataProvider = "CreateBookingDatesNegativeCaseData", dataProviderClass = TestDataProvider.class, groups = {"sanity", "regression"})
    @Epic("Booking Service API Tests")
    @Severity(SeverityLevel.CRITICAL)
    @Feature("Create Booking")
    @Owner("Sivakumari")
    public void bookingDatesErrorMsg(String checkin, String checkout, Integer statusCode, List<String> errorMsg, String testDesc) {
        Commons.setAllureLifecycle(testDesc);
        BookingServiceImpl bookingService = new BookingServiceImpl(accessToken);
        try {
            CreateBookingRequest createBookingRequest = Commons.getCreateBooking(1, "Siva", "Kumari", true, "test1@gmail.com", "24433234324", "2025-08-13", "2025-08-14");
            Response response = bookingService.postCreateBooking(createBookingRequest);
            Assert.assertEquals(response.statusCode(), statusCode,"Status Code is Wrong");
            Assert.assertNotNull(response.jsonPath().getList("errors"), "Error message is null");
            List<String> actualErrors = response.jsonPath().getList("errors");
            Assert.assertEquals(new HashSet<>(actualErrors), new HashSet<>(errorMsg));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }

    @Test(groups = {"sanity", "regression"})
    @Epic("Booking Service API Tests")
    @Severity(SeverityLevel.MINOR)
    @Feature("Create Booking")
    @Owner("Sivakumari")
    public void createBookingInvalidRequestType() {
        Commons.setAllureLifecycle("Check the Error if HTTP Method is not POST");
        BookingServiceImpl bookingService = new BookingServiceImpl(accessToken);
        try {
            CreateBookingRequest createBookingRequest = Commons.getCreateBooking(1, "Siva", "Kumari", true, "test1@gmail.com", "24433234324", "2025-08-23", "2025-08-24");
            Response response = bookingService.putCreateBooking(createBookingRequest);
            Assert.assertEquals(response.statusCode(), 405,"Status Code is Wrong");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }

    @Test(groups = {"sanity", "regression"})
    @Epic("Booking Service API Tests")
    @Severity(SeverityLevel.MINOR)
    @Feature("Create Booking")
    @Owner("Sivakumari")
    public void createBookingInvalidEndpoint() {
        Commons.setAllureLifecycle("Check the Error if EndPoint is Wrong");
        BookingServiceImpl bookingService = new BookingServiceImpl(accessToken);
        try {
            CreateBookingRequest createBookingRequest = Commons.getCreateBooking(1, "Siva", "Kumari", true, "test1@gmail.com", "24433234324", "2025-08-23", "2025-08-24");
            Response response = bookingService.postCreateBooking(createBookingRequest, "/bookingRoom");
            Assert.assertEquals(response.statusCode(), 404,"Status Code is Wrong");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }
}
