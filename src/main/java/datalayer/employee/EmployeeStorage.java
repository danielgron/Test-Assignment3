package datalayer.employee;

import dto.Customer;
import dto.Employee;
import dto.EmployeeCreation;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public interface EmployeeStorage {
    int createEmployee(EmployeeCreation employee) throws SQLException;
    public List<Employee> getEmployees() throws SQLException;

    // <3 the design choice here...
    Collection<Employee> getEmployeeWithId(int employeeId) throws SQLException;
}
