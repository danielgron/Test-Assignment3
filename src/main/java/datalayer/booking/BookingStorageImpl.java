package datalayer.booking;

import dto.Booking;
import dto.BookingCreation;
import dto.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class BookingStorageImpl implements BookingStorage{
    private String connectionString;
    private String username, password;

    public BookingStorageImpl(String conStr, String user, String pass){
        connectionString = conStr;
        username = user;
        password = pass;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionString, username, password);
    }

    @Override
    public Collection<Booking> getBookingsForCustomer(int customerId) throws SQLException {
        var results = new ArrayList<Booking>();

        var sql = "select ID, customerId, employeeId, date, start, end from Bookings where customerId = ?";
        try (var con = getConnection();
             var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, customerId);

            try (var resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("ID");
                    int custId = resultSet.getInt("customerId");
                    int empId = resultSet.getInt("employeeId");
                    Date date = resultSet.getDate("date");
                    Time start = resultSet.getTime("start");
                    Time end = resultSet.getTime("end");;
                    results.add(new Booking(id, custId, empId, date, start, end));
                }

            }
            return results;
        }
    }

    @Override
    public Collection<Booking> getBookingsForEmployee(int employeeId) throws SQLException {
        var results = new ArrayList<Booking>();

        var sql = "select ID, customerId, employeeId, date, start, end from Bookings where employeeId = ?";
        try (var con = getConnection();
             var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, employeeId);

            try (var resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("ID");
                    int custId = resultSet.getInt("customerId");
                    int empId = resultSet.getInt("employeeId");
                    Date date = resultSet.getDate("date");
                    Time start = resultSet.getTime("start");
                    Time end = resultSet.getTime("end");;
                    results.add(new Booking(id, custId, empId, date, start, end));
                }

            }
            return results;
        }
    }

    @Override
    public Collection<Booking> getAllBookings() throws SQLException {
        try (var con = getConnection();
             var stmt = con.createStatement()) {
            var results = new ArrayList<Booking>();

            try (ResultSet resultSet = stmt.executeQuery("select ID, customerId, employeeId, date, start, end from Bookings")) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("ID");
                    int custid = resultSet.getInt("customerId");
                    int empid = resultSet.getInt("employeeId");
                    Date date = resultSet.getDate("date");
                    Time start = resultSet.getTime("start");
                    Time end = resultSet.getTime("end");;

                    //int customerId, int employeeId, Date date, Time start, Time end
                    Booking b = new Booking(id, custid, empid, date, start, end);
                    results.add(b);
                }
            }

            return results;
        }
    }

    @Override
    public int createBooking(BookingCreation booking) throws SQLException {
        var sql = "insert into Bookings(customerId, employeeId, date, start, end) values (?, ?, ?, ?, ?)";
        try (var con = getConnection();
             var stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, booking.getCustomerId());
            stmt.setInt(2, booking.getEmployeeId());
            stmt.setDate(3, booking.getDate());
            stmt.setTime(4, booking.getStart());
            stmt.setTime(5, booking.getEnd());

            stmt.executeUpdate();

            // get the newly created id
            try (var resultSet = stmt.getGeneratedKeys()) {
                resultSet.next();
                int newId = resultSet.getInt(1);
                return newId;
            }
        }
    }
}
