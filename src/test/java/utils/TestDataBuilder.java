package utils;

import com.booking.models.Booking;
import com.booking.models.BookingDates;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

public class TestDataBuilder {

    public static Booking createValidBooking() {
        long uniqueId = System.currentTimeMillis() % 100000; // Use last 5 digits of timestamp
        int randomSuffix = ThreadLocalRandom.current().nextInt(100, 999); // Random 3-digit number

        // Generate unique email, phone, and dates
        String uniqueEmail = "john.doe" + uniqueId + randomSuffix + "@example.com";
        String uniquePhone = "123456789" + randomSuffix;

        // Generate random room ID from valid range (1-100) - room IDs that are accepted
        int randomRoomId = ThreadLocalRandom.current().nextInt(1, 101);

        // Generate unique dates - random offset from today (1-365 days) to ensure uniqueness
        int randomOffset = ThreadLocalRandom.current().nextInt(1, 365);
        LocalDate baseDate = LocalDate.now().plusDays(randomOffset);
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

    public static Booking createBookingWithoutField(String fieldName) {
        Booking booking = createValidBooking();
        removeField(booking, fieldName);
        return booking;
    }

    private static void removeField(Booking booking, String fieldName) {
        if ("roomid".equals(fieldName)) booking.setRoomid(null);
        else if ("firstname".equals(fieldName)) booking.setFirstname(null);
        else if ("lastname".equals(fieldName)) booking.setLastname(null);
        else if ("depositpaid".equals(fieldName)) booking.setDepositpaid(null);
        else if ("bookingdates".equals(fieldName)) booking.setBookingdates(null);
        else if ("email".equals(fieldName)) booking.setEmail(null);
        else if ("phone".equals(fieldName)) booking.setPhone(null);
    }

    public static Booking createBookingWithEmptyField(String fieldName) {
        Booking booking = createValidBooking();
        emptyField(booking, fieldName);
        return booking;
    }

    private static void emptyField(Booking booking, String fieldName) {
        if ("firstname".equals(fieldName)) booking.setFirstname("");
        else if ("lastname".equals(fieldName)) booking.setLastname("");
        else if ("email".equals(fieldName)) booking.setEmail("");
        else if ("phone".equals(fieldName)) booking.setPhone("");
    }

    public static Booking createPartialBookingUpdate(String firstname, String lastname, Boolean depositpaid) {
        Booking booking = Booking.builder().build();

        if (firstname != null) booking.setFirstname(firstname);
        if (lastname != null) booking.setLastname(lastname);
        if (depositpaid != null) booking.setDepositpaid(depositpaid);

        return booking;
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
        booking.setBookingdates(BookingDates.builder().checkin(checkin).checkout(checkout).build());
        return booking;
    }

    public static Booking createBookingWithRoomid(Integer roomid) {
        Booking booking = createValidBooking();
        booking.setRoomid(roomid);
        return booking;
    }
}
