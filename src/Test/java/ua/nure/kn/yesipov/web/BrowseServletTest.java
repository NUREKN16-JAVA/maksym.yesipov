package Test.java.ua.nure.kn.yesipov.web;

import main.java.ua.nure.kn.yesipov.User;
import main.java.ua.nure.kn.yesipov.web.BrowseServlet;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class BrowseServletTest extends MockServletTestCase {
    private static final String FIND_ALL_METHOD = "findAll";
    private static final String USERS_ATTRIBUTE = "users";
    private static final long TEST_ID = 1000L;
    private static final String TEST_ID_STRING = "1000";
    private static final String EDIT_BUTTON = "editButton";
    private static final String DETAILS_BUTTON = "detailsButton";
    private static final String DELETE_BUTTON = "deleteButton";
    private static final String USER_FIRST_NAME = "John";
    private static final String USER_LAST_NAME = "Doe";

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        createServlet(BrowseServlet.class);
    }

    public void testBrowse() {
        User expectedUser = new User(TEST_ID, USER_FIRST_NAME, USER_LAST_NAME, new Date());
        List<User> expectedUsers = Collections.singletonList(expectedUser);
        getMockUserDao().expectAndReturn(FIND_ALL_METHOD, expectedUsers);
        doGet();
        Collection<User> actualUsers = (Collection<User>) getWebMockObjectFactory()
                .getMockSession()
                .getAttribute(USERS_ATTRIBUTE);
        assertNotNull("Could not find list of users in session", actualUsers);
        assertEquals(expectedUsers, actualUsers);
    }
}
