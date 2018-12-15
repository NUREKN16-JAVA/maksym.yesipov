package Test.java.ua.nure.kn.yesipov.database;

import junit.framework.TestCase;
import main.java.ua.nure.kn.yesipov.database.DaoFactory;
import main.java.ua.nure.kn.yesipov.database.UserDao;

public class DaoFactoryTest extends TestCase {
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testGetUserDao() {
        try {
            DaoFactory daoFactory = DaoFactory.getInstance();
            assertNotNull("DaoFactory instance is null", daoFactory);
            UserDao userDao = daoFactory.getUserDao();
            assertNotNull("UserDao instance is null", userDao);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }
}
