package integration.servicelayer.booking;

import datalayer.booking.BookingStorage;
import datalayer.booking.BookingStorageImpl;
import datalayer.customer.CustomerStorage;
import datalayer.customer.CustomerStorageImpl;
import datalayer.employee.EmployeeStorage;
import datalayer.employee.EmployeeStorageImpl;
import dto.CustomerCreation;
import dto.Employee;
import dto.EmployeeCreation;
import integration.ContainerizedDbIntegrationTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import servicelayer.booking.BookingService;
import servicelayer.booking.BookingServiceException;
import servicelayer.booking.BookingServiceImpl;
import servicelayer.customer.CustomerService;
import servicelayer.customer.CustomerServiceException;
import servicelayer.customer.CustomerServiceImpl;
import servicelayer.notifications.SmsService;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CreateBookingTest extends ContainerizedDbIntegrationTest {

    private BookingService svc;
    private CustomerStorage customerStorage;
    private BookingStorage bookingStorage;
    private EmployeeStorage employeeStorage;

    @BeforeAll
    public void setup() {
        runMigration(4);

        employeeStorage = new EmployeeStorageImpl(getConnectionString(), "root", getDbPassword());
        customerStorage = new CustomerStorageImpl(getConnectionString(), "root", getDbPassword());
        bookingStorage = new BookingStorageImpl(getConnectionString(), "root", getDbPassword());
        svc = new BookingServiceImpl(bookingStorage, mock(SmsService.class));
    }

    @Test
    public void shouldCreateBooking() throws SQLException, BookingServiceException {
        // Arrange
        employeeStorage.createEmployee(new EmployeeCreation("Adam", "Bo", null));
        customerStorage.createCustomer(new CustomerCreation("Bob", "Chaplin", null));
        var id = svc.createBooking(1,1 , new Date(123456789L), new Time(123456789L), new Time(123456789L));

        // Act
        var bookings = svc.getAllBookings();

        // Assert
        assertEquals(bookings.stream().findFirst().get().getCustomerId(), 1);
    }

}
