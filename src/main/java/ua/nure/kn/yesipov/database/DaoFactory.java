package main.java.ua.nure.kn.yesipov.database;

import java.io.IOException;
import java.util.Properties;

public class DaoFactory {
    private final Properties properties;

    private static final String PROPERTIES_FILE = "settings.properties";

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
}
