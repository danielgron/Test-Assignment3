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

    public BookingServiceImpl(BookingStorage bookingStorage, SmsService smsService) {
        this.bookingStorage = bookingStorage;
        this.smsService = smsService;
    }


    @Override
    public int createBooking(int customerId, int employeeId, Date date, Time start, Time end) throws BookingServiceException {
        try {
            var bookingId = bookingStorage.createBooking(new BookingCreation(customerId,employeeId,date,start,end));
            smsService.sendSms(new SmsMessage("","Booking created"));
            return bookingId;
        } catch (SQLException throwables) {
            throw new BookingServiceException(throwables.getMessage());
        }
    }

    @Override
    public Collection<Booking> getBookingsForCustomer(int customerId) {
        return null;
    }

    @Override
    public Collection<Booking> getBookingsForEmployee(int employeeId) {
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
