package main.java.ua.nure.kn.yesipov.database;

import main.java.ua.nure.kn.yesipov.User;

import java.util.Collection;

public interface UserDao {
    User create(User user) throws DatabaseException;
    void update(User user) throws DatabaseException;
    void delete(User user) throws DatabaseException;
    User find(Long id) throws DatabaseException;
    Collection find(String firstName, String lastName) throws DatabaseException;
    Collection findAll() throws DatabaseException;
    void setConnectionFactory(ConnectionFactory connectionFactory);
}
