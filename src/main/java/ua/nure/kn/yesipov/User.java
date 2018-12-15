package main.java.ua.nure.kn.yesipov;

import java.util.Calendar;
import java.util.Date;

public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;

    public User() {
    }

    public User(Long id, String firstName, String lastName, Date dateOfBirth) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

    public User(String firstName, String lastName, Date date) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = date;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(Date dateOfBirthd) {
        this.dateOfBirth = dateOfBirthd;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public Object getFullName() {
        return getLastName() + ", " + getFirstName();
    }
    public int getAge() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        int currentYear = calendar.get(Calendar.YEAR);
        calendar.setTime(getDateOfBirth());
        int year = calendar.get(Calendar.YEAR);
        return currentYear - year;
    }
}

