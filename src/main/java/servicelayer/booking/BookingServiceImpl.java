package servicelayer.booking;

import datalayer.booking.BookingStorage;
import datalayer.customer.CustomerStorage;
import dto.Booking;
import dto.BookingCreation;
import dto.Customer;
import dto.CustomerCreation;
import servicelayer.customer.CustomerService;
import servicelayer.customer.CustomerServiceException;

import java.sql.SQLException;
import java.sql.Time;
import java.util.Collection;
import java.sql.Date;

public class BookingServiceImpl implements BookingService {

    private BookingStorage bookingStorage;

    public BookingServiceImpl(CustomerStorage customerStorage) {
        this.bookingStorage = bookingStorage;
    }


    @Override
    public int createBooking(int customerId, int employeeId, Date date, Time start, Time end) throws BookingServiceException {
        try {
            return bookingStorage.createBooking(new BookingCreation(customerId,employeeId,date,start,end));
        } catch (SQLException throwables) {
            throw new BookingServiceException(throwables.getMessage());
        }
    }

    @Override
    public Collection<Booking> getBookingsForCustomer(int customerId) {
        return null;
    }

    @Override
    public Collection<Booking> getBookingsForEmployeee(int employeeId) {
        return null;
    }

    @Override
    public Collection<Booking> getAllBookings() throws BookingServiceException {
        try {
            return bookingStorage.getAllBookings();

        } catch (SQLException throwables) {
            throw new BookingServiceException(throwables.getMessage());
        }
    }
}
