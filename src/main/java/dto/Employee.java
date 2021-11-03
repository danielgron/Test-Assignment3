package dto;


import java.util.Date;

public class Employee {
    int Id;
    String firstname;
    String lastname;
    Date birthdate;

    public Employee(int id, String firstname, String lastname, Date birthDate) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthDate;
    }
}
