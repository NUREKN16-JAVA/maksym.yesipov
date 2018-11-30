package Test.java.ua.nure.kn.yesipov.database;

import junit.framework.TestCase;
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
import org.junit.Test;

import java.util.Date;

public class HsqldbUserDaoTest extends DatabaseTestCase {
    HsqldbUserDao dao;
    private ConnectionFactory connectionFactory;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        connectionFactory = new ConnectionFactoryImpl();
        dao = new HsqldbUserDao(connectionFactory);
    }

    @Test
    public void testCreate() {
        try {
            User user = new User();
            user.setFirstName("Jerry");
            user.setLastName("Hopes");
            user.setDateOfBirthd(new Date());
            assertNull(user.getId());
            user = dao.create(user);
            assertNotNull(user);
            assertNotNull(user.getId());
        } catch (DatabaseException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @Override
    protected IDatabaseConnection getConnection() throws Exception {
        connectionFactory = new ConnectionFactoryImpl();
        return new DatabaseConnection(connectionFactory.createConnection());
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        IDataSet dataSet = new XmlDataSet(getClass().getClassLoader().getResourceAsStream("usersDataSet.xml"));
        return dataSet;
    }
}