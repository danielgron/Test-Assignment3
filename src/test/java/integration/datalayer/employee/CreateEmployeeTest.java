package integration.datalayer.employee;

import com.github.javafaker.Faker;
import datalayer.customer.CustomerStorage;
import datalayer.customer.CustomerStorageImpl;
import datalayer.employee.EmployeeStorage;
import datalayer.employee.EmployeeStorageImpl;
import dto.CustomerCreation;
import dto.EmployeeCreation;
import integration.ContainerizedDbIntegrationTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.Date;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
class CreateEmployeeTest extends ContainerizedDbIntegrationTest {
    private EmployeeStorage employeeStorage;

    /* changed code */

    @BeforeAll
    public void Setup() throws SQLException {
        runMigration(4);

        employeeStorage = new EmployeeStorageImpl(getConnectionString(), "root", getDbPassword());

        var numCustomers = employeeStorage.getEmployees().size();
        if (numCustomers < 100) {
            addFakeEmployees(100 - numCustomers);
        }
    }

    private void addFakeEmployees(int numEmployees) throws SQLException {
        Faker faker = new Faker();
        for (int i = 0; i < numEmployees; i++) {
            EmployeeCreation e = new EmployeeCreation(faker.name().firstName(), faker.name().lastName(), new Date(faker.date().birthday().getTime()));
            employeeStorage.createEmployee(e);
        }
    }

    @Test
    public void mustSaveCustomerInDatabaseWhenCallingCreateCustomer() throws SQLException {
        // Arrange
        // Act
        employeeStorage.createEmployee(new EmployeeCreation("John","Carlssonn", new Date(2323223232L)));

        // Assert
        var customers = employeeStorage.getEmployees();
        assertTrue(
                customers.stream().anyMatch(x ->
                        x.getFirstname().equals("John") &&
                        x.getLastname().equals("Carlssonn")));
    }

    @Test
    public void mustReturnLatestId() throws SQLException {
        // Arrange
        // Act
        var id1 = employeeStorage.createEmployee(new EmployeeCreation("a", "b", new Date(2323223232L)));
        var id2 = employeeStorage.createEmployee(new EmployeeCreation("c", "d", new Date(2323223232L)));

        // Assert
        assertEquals(1, id2 - id1);
    }
}
