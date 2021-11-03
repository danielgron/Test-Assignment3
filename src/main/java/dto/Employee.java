package dto;
import java.sql.Date;

public class Employee {
    private final int id;
    private final String firstname;
    private final String lastname;
    private final Date birthdate;

    public Employee(int id, String firstname, String lastname, Date birthDate) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthDate;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public Date getBirthdate() {
        return birthdate;
    }
}
