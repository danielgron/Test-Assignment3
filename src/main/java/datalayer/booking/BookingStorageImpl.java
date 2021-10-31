package datalayer.booking;

import dto.Booking;

import java.sql.SQLException;
import java.util.Collection;

public class BookingStorageImpl implements BookingStorage{
    public BookingStorageImpl(String conStr, String user, String pass){
        String connectionString = conStr;
        String username = user;
        String password = pass;
    }

    @Override
    public Collection<Booking> getBookingsForCustomer() throws SQLException {
        return null;
    }

    @Override
    public Collection<Booking> getAllBookings() throws SQLException {
        return null;
    }

    @Override
    public int createBooking(Booking booking) throws SQLException {
        return 0;
    }
}
