package Test.java.ua.nure.kn.yesipov.database;

import com.mockobjects.dynamic.Mock;
import main.java.ua.nure.kn.yesipov.database.DaoFactory;
import main.java.ua.nure.kn.yesipov.database.UserDao;

public class MockDaoFactory extends DaoFactory {
    private Mock mockUserDao;

    public MockDaoFactory() {
        mockUserDao = new Mock(UserDao.class);
    }

    public Mock getMockUserDao() {
        return mockUserDao;
    }

    public UserDao getUserDao() {
        return (UserDao) mockUserDao.proxy();
    }
}
