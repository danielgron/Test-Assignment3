package servicelayer.booking;

import dto.Booking;
import dto.Customer;
import servicelayer.customer.CustomerServiceException;

import java.sql.SQLException;
import java.sql.Time;
import java.util.Collection;
import java.sql.Date;

public interface BookingService {
    int createBooking(int customerId, int employeeId, Date date, Time start, Time end) throws BookingServiceException;
    Collection<Booking> getBookingsForCustomer(int customerId) throws BookingServiceException;
    Collection<Booking> getBookingsForEmployee(int employeeId) throws BookingServiceException;
    Collection<Booking> getAllBookings() throws BookingServiceException;
}
