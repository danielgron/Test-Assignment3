package dto;

public class BookingCreation {
    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public final String firstname, lastname;

    public BookingCreation(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }
}
