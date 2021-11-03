package integration.datalayer.employee;

import com.github.javafaker.Faker;
import datalayer.employee.EmployeeStorage;
import datalayer.employee.EmployeeStorageImpl;
import dto.EmployeeCreation;
import integration.ContainerizedDbIntegrationTest;
import org.junit.jupiter.api.*;

import java.sql.Date;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
class GetEmployeeWithIdTest extends ContainerizedDbIntegrationTest {
    private EmployeeStorage employeeStorage;

    /* changed code */

    @BeforeEach
    public void Setup() throws SQLException {
        runMigration(4);

        employeeStorage = new EmployeeStorageImpl(getConnectionString(), "root", getDbPassword());

    }



    @Test
    public void mustGetEmployeeWithId() throws SQLException {
        // Arrange
        // Act
        var emp = new EmployeeCreation("John","Carlssonn", new Date(2323223232L));

        var id = employeeStorage.createEmployee(emp);

        // Assert
        var employees = employeeStorage.getEmployeeWithId(id);
        assertEquals(employees.stream().findFirst().get().getFirstname(), "John");
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
