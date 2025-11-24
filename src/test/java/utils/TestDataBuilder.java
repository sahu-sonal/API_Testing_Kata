package utils;

import com.booking.models.Booking;
import com.booking.models.BookingDates;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

public class TestDataBuilder {

    public static Booking createValidBooking() {
        // Generate unique identifier to avoid duplicate booking errors
        long uniqueId = System.currentTimeMillis() % 100000; // Use last 5 digits of timestamp
        int randomSuffix = ThreadLocalRandom.current().nextInt(100, 999); // Random 3-digit number
        
        // Generate unique email, phone, and dates
        String uniqueEmail = "john.doe" + uniqueId + randomSuffix + "@example.com";
        String uniquePhone = "123456789" + String.format("%02d", randomSuffix % 100);
        
        // Generate random room ID from valid range (1-10) - common room IDs that likely exist
        int randomRoomId = ThreadLocalRandom.current().nextInt(1, 11);
        
        // Generate dates starting from today + 30 days to ensure future dates
        LocalDate baseDate = LocalDate.now().plusDays(30);
        String checkin = baseDate.format(DateTimeFormatter.ISO_DATE);
        String checkout = baseDate.plusDays(2).format(DateTimeFormatter.ISO_DATE);
        
        return Booking.builder()
                .roomid(randomRoomId)
                .firstname("John")
                .lastname("Doe")
                .depositpaid(true)
                .bookingdates(BookingDates.builder()
                        .checkin(checkin)
                        .checkout(checkout)
                        .build())
                .email(uniqueEmail)
                .phone(uniquePhone)
                .build();
    }

    public static Booking createBookingWithFirstname(String firstname) {
        Booking booking = createValidBooking();
        booking.setFirstname(firstname);
        return booking;
    }

    public static Booking createBookingWithLastname(String lastname) {
        Booking booking = createValidBooking();
        booking.setLastname(lastname);
        return booking;
    }

    public static Booking createBookingWithEmail(String email) {
        Booking booking = createValidBooking();
        booking.setEmail(email);
        return booking;
    }

    public static Booking createBookingWithPhone(String phone) {
        Booking booking = createValidBooking();
        booking.setPhone(phone);
        return booking;
    }

    public static Booking createBookingWithDates(String checkin, String checkout) {
        Booking booking = createValidBooking();
        booking.setBookingdates(BookingDates.builder()
                .checkin(checkin)
                .checkout(checkout)
                .build());
        return booking;
    }

    public static Booking createBookingWithRoomid(Integer roomid) {
        Booking booking = createValidBooking();
        booking.setRoomid(roomid);
        return booking;
    }

    public static Booking createBookingWithoutField(String fieldName) {
        Booking booking = createValidBooking();
        switch (fieldName.toLowerCase()) {
            case "roomid":
                booking.setRoomid(null);
                break;
            case "firstname":
                booking.setFirstname(null);
                break;
            case "lastname":
                booking.setLastname(null);
                break;
            case "depositpaid":
                booking.setDepositpaid(null);
                break;
            case "bookingdates":
                booking.setBookingdates(null);
                break;
            case "email":
                booking.setEmail(null);
                break;
            case "phone":
                booking.setPhone(null);
                break;
            default:
                throw new IllegalArgumentException("Unknown field: " + fieldName);
        }
        return booking;
    }

    public static Booking createBookingWithEmptyField(String fieldName) {
        Booking booking = createValidBooking();
        switch (fieldName.toLowerCase()) {
            case "firstname":
                booking.setFirstname("");
                break;
            case "lastname":
                booking.setLastname("");
                break;
            case "email":
                booking.setEmail("");
                break;
            case "phone":
                booking.setPhone("");
                break;
            default:
                throw new IllegalArgumentException("Field cannot be set as empty: " + fieldName);
        }
        return booking;
    }

    public static Booking createPartialBookingUpdate(String firstname, String lastname, Boolean depositpaid) {
        Booking booking = Booking.builder().build();
        if (firstname != null) {
            booking.setFirstname(firstname);
        }
        if (lastname != null) {
            booking.setLastname(lastname);
        }
        if (depositpaid != null) {
            booking.setDepositpaid(depositpaid);
        }
        return booking;
    }
}

