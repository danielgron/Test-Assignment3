package unit.servicelayer.booking;

import datalayer.booking.BookingStorage;
import datalayer.customer.CustomerStorage;
import dto.Customer;
import dto.SmsMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import servicelayer.booking.BookingService;
import servicelayer.booking.BookingServiceException;
import servicelayer.booking.BookingServiceImpl;
import servicelayer.notifications.SmsService;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;

import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("unit")
public class GetBookingForEmployeeTest {

    // SUT (System Under Test)
    private BookingService bookingService;

    // DOC (Depended-on Component)
    private BookingStorage bookingStorageMock;

    // DOC (Depended-on Component)
    private CustomerStorage customerStorageMock;

    // DOC (Depended-on Component)
    private SmsService smsServiceMock;


    @BeforeEach
    public void beforeEach() throws BookingServiceException {
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
    public void mustGetBookingWhenExists() throws BookingServiceException, SQLException {
        // Arrange
        // Act
        bookingService.getBookingsForEmployee(2);

        verify(bookingStorageMock, times(1))
                .getBookingsForEmployee(
                        intThat(x -> x == 2)
                );
    }
}
