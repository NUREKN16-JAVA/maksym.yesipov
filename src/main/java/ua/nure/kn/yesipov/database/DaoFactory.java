package main.java.ua.nure.kn.yesipov.database;

import main.java.ua.nure.kn.yesipov.User;

import java.io.IOException;
import java.util.Properties;

public class DaoFactory {
    private final Properties properties;

    private static final String PROPERTIES_FILE = "settings.properties";
    private static final String USER_DAO = "dao.ua.nure.kn.yesipov.management.db.UserDao";

    public DaoFactory(){
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ConnectionFactory getConnectionFactory(){
        String user = properties.getProperty("connection.user");
        String password = properties.getProperty("connection.password");
        String url = properties.getProperty("connection.url");
        String driver = properties.getProperty("connection.driver");
        return new ConnectionFactoryImpl(driver, url, user, password);
    }

    public UserDao getUserDao(){
        UserDao result = null;
        try {
            Class cl = Class.forName(properties.getProperty(USER_DAO));
            UserDao userDao = (UserDao) cl.newInstance();
            userDao.setConnectionFactory(getConnectionFactory());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
