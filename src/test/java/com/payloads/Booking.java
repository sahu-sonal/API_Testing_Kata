package com.payloads;

import java.util.ArrayList;

public class Booking {

    public class BookingInformation{
         int bookingid;
         int roomid;
        String firstname;
        String lastname;
        boolean depositpaid;
        Bookingdates bookingdates;

        public int getBookingid() {
            return bookingid;
        }

        public void setBookingid(int bookingid) {
            this.bookingid = bookingid;
        }

        public int getRoomid() {
            return roomid;
        }

        public void setRoomid(int roomid) {
            this.roomid = roomid;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public boolean isDepositpaid() {
            return depositpaid;
        }

        public void setDepositpaid(boolean depositpaid) {
            this.depositpaid = depositpaid;
        }

        public Bookingdates getBookingdates() {
            return bookingdates;
        }

        public void setBookingdates(Bookingdates bookingdates) {
            this.bookingdates = bookingdates;
        }


    }

    public class Bookingdates{
         String checkin;
         String checkout;
    }

    public class Root{
         ArrayList<Booking> bookings;
    }


}
