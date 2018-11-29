package main.java.ua.nure.kn.yesipov.database;

import main.java.ua.nure.kn.yesipov.User;

import java.sql.*;
import java.util.Collection;

public class HsqldbUserDao implements UserDao {
    private static final String INSERT_QUERY = "INSERT INTO users (firstname, lastname, dateofbirth) VALUES (,?,?)";
    private ConnectionFactory connectionFactory;

    public HsqldbUserDao(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public User create(User user) throws DatabaseException {
        try {
            Connection connection = connectionFactory.createConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_QUERY);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setDate(3, new Date(user.getDateOfBirthd().getTime()));
            int n = statement.executeUpdate();
            if (n != 1){
                throw new DatabaseException("Number of updated rows: " + n);
            }
            CallableStatement callableStatement = connection.prepareCall("call IDENTITY()");
            ResultSet keys = callableStatement.executeQuery();
            if (keys.next()){
                user.setId(new Long(keys.getLong(1)));
            }
            callableStatement.close();
            keys.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }

        return user;
    }

    @Override
    public void update(User user) throws DatabaseException {

    }

    @Override
    public void delete(User user) throws DatabaseException {

    }

    @Override
    public User find(Long id) throws DatabaseException {
        return null;
    }

    @Override
    public Collection findAll() throws DatabaseException {
        return null;
    }
}
