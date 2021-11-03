# Assigment 3:


Tests has only been run in Intelij, no guarantees anywhere else.

 ## Notes on changes made:

testcontainers updated to 1.6.2 in order to be used with newer releases of docker.

The provided way of instantiating only one db for integration tests not very smart, as the state of the db is not deterministic for each integration test, but rather decided by the tests running beforehand.

I have changed the integrationtests to do a clean db as part of the migration.


This means each test run will take longer, and ideally it would be handled with a script for truncating tables.

For the scope of the assignment this fix suited me better.


Running it normally will have limited functionality as it is clearly stated that the sms service should not be implemented, and thus bookings will fail as the dependency is not there.


The database for a "production" db will be created by running docker-compose up from the root of the project.
This has a volume mapped to the folder with migration scripts that will automatically be called on the first run.

______________


# Requirements:
## R1: It must be possible to create customers, employees and bookings.

done


## R2: A customer may have a phone number (this change requires a database migration script).

done
script: V4__alter_table_Customers

changes made to customer + test


## R3: When booking an appointment with a customer, an SMS must be sent1.

done

changes made to customer + test



## Data layer
1. Create BookingStorage1 and BookingStorageImpl2 with methods
- int createBooking(Booking booking)3
- Collection<Booking> getBookingsForCustomer(int customerId)

done

2. Create EmployeeStorage and EmployeeStorageImpl with methods
- int createEmployee(Employee employee)4
- Collection<Employee> getEmployeeWithId(int employeeId)


done

## Service layer

1. Create BookingService and BookingServiceImpl with methods
- int createBooking(customerId, employeeId, date, start, end)
- Collection<Booking> getBookingsForCustomer(customerId)
- Collection<Booking> getBookingsForEmployee(employeeId)

2. Create EmployeeService and EmployeeServiceImpl with methods
• int createEmployee(employee)
• Employee getEmployeeById(employeeId)