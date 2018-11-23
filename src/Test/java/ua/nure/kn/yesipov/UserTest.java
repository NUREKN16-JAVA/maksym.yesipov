package Test.java.ua.nure.kn.yesipov;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import main.java.ua.nure.kn.yesipov.*;
import org.junit.Before;
import org.junit.Test;

public class UserTest {
    private User user;
    private Date dateOfBirth;

    @Before
    public void setUp() throws Exception {
        user = new User();

        Calendar calendar = Calendar.getInstance();
        calendar.set(1999, Calendar.AUGUST, 12);
        dateOfBirth = calendar.getTime();
    }

    @Test
    public void testGetFullName() {
        user.setFirstName("Jerry");
        user.setLastName("Hopes");
        assertEquals("Hopes, Jerry", user.getFullName());
    }

    @Test
    public void testGetAge() {
        user.setDateOfBirthd(dateOfBirth);
        assertEquals(2018 - 1999, user.getAge());
    }
}
