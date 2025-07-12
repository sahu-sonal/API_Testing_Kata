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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class CreateBooking extends TestSuiteSetup {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @DataProvider(name = "CreateBookingDetails")
    public Object[][] positiveCase() {
        return new Object[][]{
                //roomid,FirstName,LastName,DespositPaid,email,phone
                {2,"Siva","Kumari",true,"test1@gmail.com","24433234324",200,"Success TestCase - Validate Create Booking"},
                {4,"Siva","Kumari",false,"test1@gmail.com","24433234324",200,"Success TestCase - Validate Create Booking when DepositPaid is False"},
                {2,"Siva","Kumari",true,"test1@gmail.com","24433234324",500,"Negative TestCase - Validate Duplicate Create Booking"}

        };
    }

    //@Test(dataProvider = "CreateBookingDetails",groups = {"sanity","regression"})
    @Epic("Booking Service API Tests")
    @Severity(SeverityLevel.BLOCKER) @Feature("Create Booking") @Owner("Sivakumari")
    public void createBooking(int roomId,String firstName,String lastName,Boolean depositPaid,String email,String phone,int statusCode,String testDesc) {
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


    @DataProvider(name = "CreateBookingErrorMessages")
    public Object[][] createBookingNegCases() {
        return new Object[][]{
                //roomid,FirstName,LastName,DespositPaid,email,phone
                {null,"Siva","Kumari",true,"test1@gmail.com","24433234324",400,Arrays.asList("must be greater than or equal to 1"),"Validate When Room ID is null"},
                // First Name field Cases
                {2,"Si","Kumari",true,"test1@gmail.com","24433234324",400,Arrays.asList("size must be between 3 and 18"),"Validate When FirstName is less than 3"},
                {2,"SivakumariSadasivam","Kumari",true,"test1@gmail.com","24433234324",400,Arrays.asList("size must be between 3 and 18"),"Validate When FirstName is greater than 18"},
                {2,"","Kumari",true,"test1@gmail.com","24433234324",400,Arrays.asList("Firstname should not be blank", "size must be between 3 and 18"),"Validate When FirstName is empty"},
                {2," ","Kumari",true,"test1@gmail.com","24433234324",400,Arrays.asList("Firstname should not be blank", "size must be between 3 and 18"),"Validate When FirstName is empty spaces"},
                {2,null,"Kumari",true,"test1@gmail.com","24433234324",400,Arrays.asList("Firstname should not be blank"),"Validate When FirstName is null"},
                {201,"Siv","Kumari",true,"test1@gmail.com","24433234324",200,null,"Validate When FirstName is of size 3"},
                {401,"SivakumariTestTest","Kumari",true,"test1@gmail.com","24433234324",200,null,"Validate When FirstName is of size 18"},

                //Last Name Field Cases
                {2,"Siva","Ku",true,"test1@gmail.com","24433234324",400,Arrays.asList("size must be between 3 and 30"),"Validate When LastName is less than 3"},
                {2,"Siva","KumariKumariKumariKumariKumariKumari",true,"test1@gmail.com","24433234324",400,Arrays.asList("size must be between 3 and 30"),"Validate When LastName is greater than 30"},
                {101,"Siva","Kum",true,"test1@gmail.com","24433234324",200,null,"Validate When LastName is of size 3"},
                {301,"Siva","KumariKumariKumariKumariKumari",true,"test1@gmail.com","24433234324",200,null,"Validate When LastName is of size 30"},
                {2,"Siva","",true,"test1@gmail.com","24433234324",400, Arrays.asList("Lastname should not be blank", "size must be between 3 and 30"),"Validate When LastName is empty"},
                {2,"Siva"," ",true,"test1@gmail.com","24433234324",400, Arrays.asList("Lastname should not be blank", "size must be between 3 and 30"),"Validate When LastName is empty with spaces"},
                {2,"Siva",null,true,"test1@gmail.com","24433234324",400, Arrays.asList("Lastname should not be blank"),"Validate When LastName is null"},

                //Email Field Validation
                {2,"Siva","Kumari",true,"test@gmail","24433234324",400,Arrays.asList("must not be empty"),"Email Value is Without .com"},
                {2,"Siva","Kumari",true,"test@","24433234324",400,Arrays.asList("must be a well-formed email address"),"Email Value is Without Domain(No content after@)"},
                {2,"Siva","Kumari",true,"test","24433234324",400,Arrays.asList("must be a well-formed email address"),"Email Value is Without @"},
                {2,"Siva","Kumari",true,"","24433234324",400,Arrays.asList("must not be empty"),"Email Value is Empty"},
                {2,"Siva","Kumari",true," ","24433234324",400,Arrays.asList("must be a well-formed email address"),"Email Value is Empty with Space"},
                {2,"Siva","Kumari",true,null,"24433234324",400,Arrays.asList("Failed to create booking"),"Email Value is null"},

                //Phone Field Validation
                {2,"Siva","Kumari",true,"test1@gmail.com","4433234324",400,Arrays.asList("size must be between 11 and 21"),"Phone Value is less than 11 numbers"},
                {2,"Siva","Kumari",true,"test1@gmail.com","4433234324443323432422",400,Arrays.asList("size must be between 11 and 21"),"Phone Value is more than 21 numbers"},
                {4,"Siva","Kumari",true,"test1@gmail.com","44332343244",200,null,"Phone Value size is 11"},
                {1,"Siva","Kumari",true,"test1@gmail.com","443323432444343536354",200,null,"Phone Value size is 21"},
                {2,"Siva","Kumari",true,"test1@gmail.com","",400,Arrays.asList("size must be between 11 and 21","must not be empty"),"Phone Value is empty"},
                {2,"Siva","Kumari",true,"test1@gmail.com"," ",400,Arrays.asList("size must be between 11 and 21"),"Phone Value is empty spaces"},
                {3,"admin","admin",true,"test1@gmail.com",null,400,Arrays.asList("Failed to create booking"),"Phone Value is null"}
        };
    }


    @Test(dataProvider = "CreateBookingErrorMessages",groups = {"sanity","regression"})
    @Epic("Booking Service API Tests")
    @Severity(SeverityLevel.CRITICAL) @Feature("Create Booking") @Owner("Sivakumari")
   public void bookingErrorMessageValidation(Integer roomId, String firstName, String lastName, Boolean depositPaid, String email, String phone, int statusCode, List<String> errorMsg, String testDesc) {
        BookingServiceImpl bookingService = new BookingServiceImpl(null);
        try {
            Allure.description(testDesc);;
            CreateBookingRequest.BookingDates bookingDates = new CreateBookingRequest.BookingDates("2025-08-23","2025-08-24");
            CreateBookingRequest createBookingRequest = new CreateBookingRequest(roomId,firstName,lastName,depositPaid,email,phone,bookingDates);
            Response response = bookingService.postCreateBooking(createBookingRequest);
            Assert.assertEquals(response.statusCode(),statusCode);
            List<String> actualErrors = response.jsonPath().getList("errors");
            Assert.assertEquals(new HashSet<>(actualErrors), new HashSet<>(errorMsg));
        }
        catch (Exception e) {
            System.out.printf(String.valueOf(e));
        }
    }


}
