package integration.datalayer.booking;

import com.github.javafaker.Faker;
import datalayer.booking.BookingStorage;
import datalayer.booking.BookingStorageImpl;
import datalayer.customer.CustomerStorage;
import datalayer.customer.CustomerStorageImpl;
import dto.Booking;
import dto.CustomerCreation;
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
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
class CreateBookingTest extends ContainerizedDbIntegrationTest {
    private BookingStorage bookingStorage;

    /* changed code */

    @BeforeAll
    public void Setup() throws SQLException {
        runMigration(2);

        bookingStorage = new BookingStorageImpl(getConnectionString(), "root", getDbPassword());

        var numBookings = bookingStorage.getAllBookings().size();
        if (numBookings < 100) {
            addFakeBookings(100 - numBookings);
        }
    }

    private void addFakeBookings(int numCustomers) throws SQLException {
        Faker faker = new Faker();
        for (int i = 0; i < numCustomers; i++) {
            Time sqlTime1 = Time.valueOf("10:00:00");
            Time sqlTime2 = Time.valueOf("12:00:00");
            Date date = new GregorianCalendar(2021, Calendar.OCTOBER, 20).getTime();
            Booking b = new Booking(i, i, date, sqlTime1, sqlTime2);
            bookingStorage.createBooking(b);
        }

    }

    @Test
    public void mustSaveCustomerInDatabaseWhenCallingCreateCustomer() throws SQLException {
        // Arrange
        // Act
        Time sqlTime1 = Time.valueOf("10:00:00");
        Time sqlTime2 = Time.valueOf("12:00:00");
        Date date = new GregorianCalendar(2021, Calendar.OCTOBER, 20).getTime();
        Booking b = new Booking(1, 1, date, sqlTime1, sqlTime2);
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

    private Booking createBooking(){
        Time sqlTime1 = Time.valueOf("10:00:00");
        Time sqlTime2 = Time.valueOf("12:00:00");
        Date date = new GregorianCalendar(2021, Calendar.OCTOBER, 20).getTime();
        Booking b = new Booking(1, 1, date, sqlTime1, sqlTime2);
        return b;
    }
}
