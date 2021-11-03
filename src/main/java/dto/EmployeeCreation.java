package dto;


import java.sql.Date;

public class EmployeeCreation {
    String firstname;
    String lastname;
    Date birthdate;

    public EmployeeCreation(String firstname, String lastname, Date birthDate) {
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
