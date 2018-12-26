package Test.java.ua.nure.kn.yesipov.database;

import main.java.ua.nure.kn.yesipov.User;
import main.java.ua.nure.kn.yesipov.database.ConnectionFactory;
import main.java.ua.nure.kn.yesipov.database.ConnectionFactoryImpl;
import main.java.ua.nure.kn.yesipov.database.DatabaseException;
import main.java.ua.nure.kn.yesipov.database.HsqldbUserDao;
import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.junit.Before;

import java.util.Collection;
import java.util.Date;

public class HsqldbUserDaoTest extends DatabaseTestCase {
    private HsqldbUserDao dao;
    private ConnectionFactory connectionFactory;

    private static final long TEST_ID = 1001L;
    private static final String FIRST_NAME = "Jerry";
    private static final String LAST_NAME = "Hopes";

    @Before
    public void setUp() throws Exception {
        super.setUp();
        connectionFactory = new ConnectionFactoryImpl();
        dao = new HsqldbUserDao(connectionFactory);
    }

    public void testCreate() {
        try {
            User user = new User();
            user.setFirstName(FIRST_NAME);
            user.setLastName(LAST_NAME);
            user.setDateOfBirth(new Date());
            assertNull(user.getId());

            User userExpected = dao.create(user);
            assertNotNull(userExpected);
            assertNotNull(userExpected.getId());
            assertEquals(userExpected.getFirstName(), user.getFirstName());
            assertEquals(userExpected.getLastName(), user.getLastName());
            assertEquals(userExpected.getDateOfBirth(), user.getDateOfBirth());
        } catch (DatabaseException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    public void testFindAll() {
        try {
            Collection collection = dao.findAll();
            assertNotNull("Collection is null", collection);
            assertEquals("Collection size", 2, collection.size());
        } catch (DatabaseException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    public void testFind() {
        long id = TEST_ID;
        try {
            User user = dao.find(id);
            assertNotNull(user);
            long userId = user.getId();
            assertEquals(id, userId);
        } catch (DatabaseException e) {
            fail(e.getMessage());
        }
    }

    public void testDelete() {
        User testUser = createUser();
        try {
            int beforeSize = dao.findAll().size();
            dao.delete(testUser);
            int afterSize = dao.findAll().size();
            assertEquals(2, beforeSize);
            assertEquals(1, afterSize);
        } catch (DatabaseException e) {
            fail(e.getMessage());
        }
    }

    public void testUpdate() {
        User testUser = createUser();
        try {
            dao.update(testUser);
            User updatedUser = dao.find(TEST_ID);
            assertEquals(FIRST_NAME, updatedUser.getFirstName());
            assertEquals(LAST_NAME, updatedUser.getLastName());
        } catch (DatabaseException e) {
            fail(e.getMessage());
        }
    }

    private User createUser() {
        User user = new User();
        user.setId(TEST_ID);
        user.setFirstName(FIRST_NAME);
        user.setLastName(LAST_NAME);
        user.setDateOfBirth(new Date());
        return user;
    }

    @Override
    protected IDatabaseConnection getConnection() throws Exception {
        connectionFactory = new ConnectionFactoryImpl();
        return new DatabaseConnection(connectionFactory.createConnection());
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new XmlDataSet(getClass().getClassLoader().getResourceAsStream("usersDataSet.xml"));
    }
}