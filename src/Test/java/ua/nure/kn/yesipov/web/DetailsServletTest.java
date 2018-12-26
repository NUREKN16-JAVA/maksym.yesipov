package Test.java.ua.nure.kn.yesipov.web;

import main.java.ua.nure.kn.yesipov.User;
import main.java.ua.nure.kn.yesipov.web.DetailsServlet;

public class DetailsServletTest extends MockServletTestCase {
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        createServlet(DetailsServlet.class);
    }

    public void testBack() {
        addRequestParameter("backButton", "Back");
        User user = (User) getWebMockObjectFactory().getMockSession().getAttribute("user");
        assertNull("User was not deleted from session", user);
    }
}
