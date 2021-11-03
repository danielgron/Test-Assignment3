package integration.datalayer.customer;

import com.github.javafaker.Faker;
import datalayer.customer.CustomerStorage;
import datalayer.customer.CustomerStorageImpl;
import dto.CustomerCreation;
import integration.ContainerizedDbIntegrationTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
class CreateCustomerTest extends ContainerizedDbIntegrationTest {
    private CustomerStorage customerStorage;

    /* changed code */

    @BeforeAll
    public void Setup() throws SQLException {
        runMigration(4);

        customerStorage = new CustomerStorageImpl(getConnectionString(), "root", getDbPassword());

        var numCustomers = customerStorage.getCustomers().size();
        if (numCustomers < 100) {
            addFakeCustomers(100 - numCustomers);
        }
    }

    private void addFakeCustomers(int numCustomers) throws SQLException {
        Faker faker = new Faker();
        for (int i = 0; i < numCustomers; i++) {
            CustomerCreation c = new CustomerCreation(faker.name().firstName(), faker.name().lastName(), faker.phoneNumber().phoneNumber());
            customerStorage.createCustomer(c);
        }
    }

    @Test
    public void mustSaveCustomerInDatabaseWhenCallingCreateCustomer() throws SQLException {
        // Arrange
        // Act
        customerStorage.createCustomer(new CustomerCreation("John","Carlssonn", null));

        // Assert
        var customers = customerStorage.getCustomers();
        assertTrue(
                customers.stream().anyMatch(x ->
                        x.getFirstname().equals("John") &&
                        x.getLastname().equals("Carlssonn")));
    }

    @Test
    public void mustReturnLatestId() throws SQLException {
        // Arrange
        // Act
        var id1 = customerStorage.createCustomer(new CustomerCreation("a", "b", null));
        var id2 = customerStorage.createCustomer(new CustomerCreation("c", "d", null));

        // Assert
        assertEquals(1, id2 - id1);
    }

    @Test
    public void mustSavePhoneNumber() throws SQLException {
        // Arrange
        // Act
        var phone = "12345678";
        var id1 = customerStorage.createCustomer(new CustomerCreation("a", "b", phone));

        var phoneReturned = customerStorage.getCustomerWithId(id1).getPhone();

        // Assert
        assertEquals(phone, phoneReturned);
    }
}
