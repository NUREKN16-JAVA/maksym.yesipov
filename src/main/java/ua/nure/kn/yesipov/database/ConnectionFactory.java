package main.java.ua.nure.kn.yesipov.database;

import java.sql.Connection;

public interface ConnectionFactory {
    Connection createConnection() throws DatabaseException;
}
