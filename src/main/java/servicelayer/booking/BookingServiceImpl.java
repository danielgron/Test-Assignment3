package servicelayer.booking;

import datalayer.booking.BookingStorage;
import datalayer.customer.CustomerStorage;
import dto.*;
import servicelayer.customer.CustomerService;
import servicelayer.customer.CustomerServiceException;
import servicelayer.notifications.SmsService;

import java.sql.SQLException;
import java.sql.Time;
import java.util.Collection;
import java.sql.Date;

public class BookingServiceImpl implements BookingService {

    private final SmsService smsService;
    private BookingStorage bookingStorage;
    private CustomerStorage customerStorage;

    public BookingServiceImpl(BookingStorage bookingStorage, CustomerStorage customerStorage, SmsService smsService) {
        this.bookingStorage = bookingStorage;
        this.customerStorage = customerStorage;
        this.smsService = smsService;
    }


    @Override
    public int createBooking(int customerId, int employeeId, Date date, Time start, Time end) throws BookingServiceException {
        try {
            var bookingId = bookingStorage.createBooking(new BookingCreation(customerId,employeeId,date,start,end));
            var customer = customerStorage.getCustomerWithId(customerId);

            if (customer == null)
                throw new BookingServiceException("No customer with id " + customerId + " exists");

            var phoneNumber = customerStorage.getCustomerWithId(customerId).getPhone();
            smsService.sendSms(new SmsMessage(phoneNumber, "Booking created"));
            return bookingId;
        } catch (SQLException throwables) {
            throw new BookingServiceException(throwables.getMessage());
        }
    }

    @Override
    public Collection<Booking> getBookingsForCustomer(int customerId) throws BookingServiceException {
        try {
            return bookingStorage.getBookingsForCustomer(customerId);
        } catch (SQLException throwables) {
            throw new BookingServiceException(throwables.getMessage());
        }
    }

    @Override
    public Collection<Booking> getBookingsForEmployee(int employeeId) throws BookingServiceException {
        try {
            return bookingStorage.getBookingsForEmployee(employeeId);

        } catch (SQLException throwables) {
            throw new BookingServiceException(throwables.getMessage());
        }
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
