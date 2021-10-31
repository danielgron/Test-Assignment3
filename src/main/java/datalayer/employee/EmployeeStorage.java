package datalayer.employee;

import dto.Employee;

import java.util.Collection;

public interface EmployeeStorage {
    int createEmployee(Employee employee);

    // <3 the design choice here...
    Collection<Employee> getEmployeeWithId(int employeeId);
}
