package unit.servicelayer.booking;

import datalayer.booking.BookingStorage;
import datalayer.customer.CustomerStorage;
import dto.Customer;
import dto.SmsMessage;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import servicelayer.booking.BookingService;
import servicelayer.booking.BookingServiceException;
import servicelayer.booking.BookingServiceImpl;
import servicelayer.notifications.SmsService;

import java.sql.SQLException;
import java.sql.Date;
import java.sql.Time;

import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("unit")
public class CreateBookingTest {

    // SUT (System Under Test)
    private BookingService bookingService;

    // DOC (Depended-on Component)
    private BookingStorage bookingStorageMock;

    // DOC (Depended-on Component)
    private CustomerStorage customerStorageMock;

    // DOC (Depended-on Component)
    private SmsService smsServiceMock;


    @BeforeEach
    public void beforeEach(){
        bookingStorageMock = mock(BookingStorage.class);
        customerStorageMock = mock(CustomerStorage.class);

        var testCustomer = new Customer(1,"Test", "TestMan", "12345678");

        try {
            Mockito.when(customerStorageMock.getCustomerWithId(1)).thenReturn(testCustomer);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        smsServiceMock = mock(SmsService.class);
        bookingService = new BookingServiceImpl(bookingStorageMock, customerStorageMock, smsServiceMock);
    }

    @Test
    public void mustCallStorageWhenCreatingBooking() throws BookingServiceException, SQLException {
        // Arrange
        // Act
        var custId = 1;
        var empId = 1;
        var date = new Date(123456789l);
        var start = new Time(123456789l);
        var end = new Time(123456789l);

        bookingService.createBooking(custId, empId, date, start, end);

        // Assert
        // Can be read like: verify that storageMock was called 1 time on the method
        //   'createCustomer' with an argument whose 'firstname' == firstName and
        //   whose 'lastname' == lastName
        verify(bookingStorageMock, times(1))
                .createBooking(
                        argThat(x -> x.getEmployeeId()== empId &&
                                x.getCustomerId() == custId));
    }

    @Test
    public void mustCallSMSServiceWhenCreatingBooking() throws BookingServiceException, SQLException {
        // Arrange
        // Act
        var custId = 1;
        var empId = 1;
        var date = new Date(123456789l);
        var start = new Time(123456789l);
        var end = new Time(123456789l);

        bookingService.createBooking(custId, empId, date, start, end);

        verify(smsServiceMock, times(1)).sendSms(new SmsMessage("12345678", "Booking created"));
    }
}
