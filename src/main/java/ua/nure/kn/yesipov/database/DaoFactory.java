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
}
