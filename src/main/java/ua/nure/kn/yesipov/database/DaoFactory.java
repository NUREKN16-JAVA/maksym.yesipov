package main.java.ua.nure.kn.yesipov.database;

import java.io.IOException;
import java.util.Properties;

public abstract class DaoFactory {
    protected static Properties properties;

    private static final String DAO_FACTORY = "dao.factory";
    private static final String PROPERTIES_FILE = "settings.properties";
    private static final String CONNECTION_DRIVER = "connection.driver";
    private static final String CONNECTION_URL = "connection.url";
    private static final String CONNECTION_USER = "connection.user";
    private static final String CONNECTION_PASSWORD = "connection.password";
    protected static final String USER_DAO = "dao.ua.nure.kn.yesipov.usermanagement.db.UserDao";

    private static DaoFactory instance;

    static {
        properties = new Properties();
        try {
            properties.load(DaoFactory.class
                    .getClassLoader()
                    .getResourceAsStream(PROPERTIES_FILE));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized DaoFactory getInstance() {
        if (instance == null) {
            Class<?> factoryClass;
            try {
                factoryClass = Class.forName(properties.getProperty(DAO_FACTORY));
                instance = (DaoFactory) factoryClass.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }


    public DaoFactory() {
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void init(Properties p) {
        properties = p;
        instance = null;
    }

    protected ConnectionFactory getConnectionFactory() {
        String user = properties.getProperty(CONNECTION_USER);
        String password = properties.getProperty(CONNECTION_PASSWORD);
        String url = properties.getProperty(CONNECTION_URL);
        String driver = properties.getProperty(CONNECTION_DRIVER);
        return new ConnectionFactoryImpl(driver, url, user, password);
    }

    public abstract UserDao getUserDao();
}
