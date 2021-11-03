package servicelayer.employee;

import dto.Customer;
import dto.Employee;

import java.sql.SQLException;
import java.util.Collection;
import java.sql.Date;

public interface EmployeeService {
    int createEmployee(String firstName, String lastName, Date birthdate) throws EmployeeServiceException;
    Collection<Employee> getEmployeeById(int id) throws SQLException;
    Collection<Employee> getCustomersByFirstName(String firstName);
}
