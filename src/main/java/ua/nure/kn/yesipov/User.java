package main.java.ua.nure.kn.yesipov;


import java.util.Calendar;
import java.util.Date;

public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private Date dateOfBirthd;

    public Date getDateOfBirthd() {
        return dateOfBirthd;
    }

    public void setDateOfBirthd(Date dateOfBirthd) {
        this.dateOfBirthd = dateOfBirthd;
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
        calendar.setTime(getDateOfBirthd());
        int year = calendar.get(Calendar.YEAR);
        return currentYear - year;
    }
}

