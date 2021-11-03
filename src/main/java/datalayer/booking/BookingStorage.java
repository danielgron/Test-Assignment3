package datalayer.booking;

import dto.Booking;
import dto.BookingCreation;
import dto.Customer;
import dto.CustomerCreation;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public interface BookingStorage {
    //public Booking getBookingWithId(int bookingId) throws SQLException;
    public Collection<Booking> getBookingsForCustomer() throws SQLException;
    public Collection<Booking> getAllBookings() throws SQLException;
    public int createBooking(BookingCreation booking) throws SQLException;
}
