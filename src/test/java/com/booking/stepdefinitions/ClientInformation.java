package com.booking.stepdefinitions;

import com.payloads.Booking;
import com.payloads.Bookingdates;
import java.util.HashMap;
import java.util.Map;

public class ClientInformation {

    private Booking booking;
    Booking payload = new Booking();
    Bookingdates payloadDates = new Bookingdates();

    public ClientInformation(Booking booking){
        this.booking = booking;
    }

    public static Map<String,Object> getAllClientInfo(){

        Map<String,Object> info = new HashMap<String,Object>();
        info.put("roomid",5);
        info.put("firstname","Daniele");
        info.put("lastname","Marino");
        info.put("depositpaid",false);
        Map<String,Object> dates= new HashMap<String,Object>();
        dates.put("checkin","2025-06-25");
        dates.put("checkout","2025-07-02");
        info.put("bookingdates",dates);

        return info;

/*
        payload.setRoomid("6");
        payload.setFirstname("Daniele");
        payload.setLastname("Marino");
        payload.setDepositpaid(false);
        payload.setEmail("marino@gmail.com");
        payload.setPhone("716900001111");
        payload.setBookingdates();
        payloadDates.setCheckin("2026-07-02");
        payloadDates.setCheckout("2026-07-06");
*/

    }


    public static Map<String,Object> updateClientNameAndLastName() {

        Map<String, Object> updated_info = new HashMap<String, Object>();
        updated_info.put("roomid", 5);
        updated_info.put("firstname", "Jimmy");
        updated_info.put("lastname", "Love");
        updated_info.put("depositpaid", false);
        Map<String, Object> dates = new HashMap<String, Object>();
        dates.put("checkin", "2025-06-25");
        dates.put("checkout", "2025-07-02");
        updated_info.put("bookingdates", dates);

        return updated_info;

    }




}
