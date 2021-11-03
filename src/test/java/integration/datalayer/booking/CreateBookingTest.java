package integration.datalayer.booking;

import com.github.javafaker.Faker;
import datalayer.booking.BookingStorage;
import datalayer.booking.BookingStorageImpl;
import datalayer.customer.CustomerStorage;
import datalayer.customer.CustomerStorageImpl;
import datalayer.employee.EmployeeStorage;
import datalayer.employee.EmployeeStorageImpl;
import dto.Booking;
import dto.BookingCreation;
import dto.CustomerCreation;
import dto.EmployeeCreation;
import integration.ContainerizedDbIntegrationTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.time.temporal.TemporalField;
import java.util.Calendar;
import java.sql.Date;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
class CreateBookingTest extends ContainerizedDbIntegrationTest {
    private BookingStorage bookingStorage;
    private CustomerStorage customerStorage;
    private EmployeeStorage employeeStorage;

    /* changed code */

    @BeforeAll
    public void Setup() throws SQLException {
        runMigration(4);

        customerStorage = new CustomerStorageImpl(getConnectionString(), "root", getDbPassword());
        bookingStorage = new BookingStorageImpl(getConnectionString(), "root", getDbPassword());
        employeeStorage = new EmployeeStorageImpl(getConnectionString(), "root", getDbPassword());

        var numCustomers = customerStorage.getCustomers().size();
        if (numCustomers < 100) {
            addFakeCustomers(100 - numCustomers);
        }
        System.out.println(customerStorage.getCustomers().size() + " customers");

        var numEmployees = employeeStorage.getEmployees().size();
        if (numEmployees < 100) {
            addFakeEmployees(100 - numEmployees);
        }
        System.out.println(employeeStorage.getEmployees().size() + " employees");

        var numBookings = bookingStorage.getAllBookings().size();
        if (numBookings < 10) {
            addFakeBookings(10 - numBookings);
        }
    }

    private void addFakeCustomers(int numCustomers) throws SQLException {
        Faker faker = new Faker();
        for (int i = 0; i < numCustomers; i++) {
            CustomerCreation c = new CustomerCreation(faker.name().firstName(), faker.name().lastName());
            customerStorage.createCustomer(c);
        }
    }

    private void addFakeEmployees(int numEmployees) throws SQLException {
        Faker faker = new Faker();
        for (int i = 0; i < numEmployees; i++) {
            Date date = new Date(new GregorianCalendar(2021, Calendar.OCTOBER, 20).getTimeInMillis());
            EmployeeCreation e = new EmployeeCreation(faker.name().firstName(), faker.name().lastName(), date);
            employeeStorage.createEmployee(e);
        }
    }

    private void addFakeBookings(int numBookings) throws SQLException {
        Faker faker = new Faker();
        for (int i = 0; i < numBookings; i++) {
            Time sqlTime1 = Time.valueOf("10:00:00");
            Time sqlTime2 = Time.valueOf("12:00:00");
            Date date = new Date(new GregorianCalendar(2021, Calendar.OCTOBER, 20).getTimeInMillis());
            BookingCreation b = new BookingCreation(i + 1, i + 1, date, sqlTime1, sqlTime2);
            bookingStorage.createBooking(b);
        }

    }

    @Test
    public void mustSaveCustomerInDatabaseWhenCallingCreateCustomer() throws SQLException {
        // Arrange
        // Act
        Time sqlTime1 = Time.valueOf("10:00:00");
        Time sqlTime2 = Time.valueOf("12:00:00");
        Date date = new Date(new GregorianCalendar(2021, Calendar.OCTOBER, 20).getTimeInMillis());
        BookingCreation b = new BookingCreation(1, 1, date, sqlTime1, sqlTime2);
        bookingStorage.createBooking(b);

        // Assert
        var customers = bookingStorage.getAllBookings();
        assertTrue(
                customers.stream().anyMatch(x ->
                        x.getCustomerId() == 1 &&
                        x.getEmployeeId() == 1));
    }

    @Test
    public void mustReturnLatestId() throws SQLException {
        // Arrange
        // Act
        var id1 = bookingStorage.createBooking(createBooking());
        var id2 = bookingStorage.createBooking(createBooking());

        // Assert
        assertEquals(1, id2 - id1);
    }

    private BookingCreation createBooking(){
        Time sqlTime1 = Time.valueOf("10:00:00");
        Time sqlTime2 = Time.valueOf("12:00:00");
        Date date = new Date(new GregorianCalendar(2021, Calendar.OCTOBER, 20).getTimeInMillis());
        BookingCreation b = new BookingCreation(1, 1, date, sqlTime1, sqlTime2);
        return b;
    }
}
