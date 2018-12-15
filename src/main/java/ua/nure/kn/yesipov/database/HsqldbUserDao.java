package main.java.ua.nure.kn.yesipov.database;

import main.java.ua.nure.kn.yesipov.User;

import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;

public class HsqldbUserDao implements UserDao {
    private ConnectionFactory connectionFactory;

    private static final String INSERT_QUERY = "INSERT into users (firstname, lastname, dateOfBirth) values (?, ?, ?)";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM users";
    private static final String UPDATE_QUERY = "UPDATE users SET firstname = ?, lastname = ?, dateofbirth = ?  WHERE id = ?";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM users WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM users WHERE id = ?";

    public HsqldbUserDao() {
    }

    public HsqldbUserDao(ConnectionFactory connectionFactory){
        this.connectionFactory = connectionFactory;
    }

    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public User create(User user) throws DatabaseException {
        try {
            Connection connection = connectionFactory.createConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_QUERY);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setDate(3, new Date(user.getDateOfBirth().getTime()));
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
            return user;
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override

    public void update(User user) throws DatabaseException {
        try (Connection connection = connectionFactory.createConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY);
            int count = 1;
            preparedStatement.setString(count++, user.getFirstName());
            preparedStatement.setString(count++, user.getLastName());
            preparedStatement.setDate(count++, new Date(user.getDateOfBirth().getTime()));
            preparedStatement.setLong(count, user.getId());
            int updatedRows = preparedStatement.executeUpdate();
            if (updatedRows != 1) {
                throw new DatabaseException("Exception while update operation. Effected rows: " + updatedRows);
            }
            preparedStatement.close();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public void delete(User user) throws DatabaseException {
        try (Connection connection = connectionFactory.createConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY);
            preparedStatement.setLong(1, user.getId());
            int updatedRows = preparedStatement.executeUpdate();
            if (updatedRows != 1) {
                throw new DatabaseException("Exception while delete operation. Effected rows: " + updatedRows);
            }
            preparedStatement.close();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public User find(Long id) throws DatabaseException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = connectionFactory.createConnection()) {
            preparedStatement = connection.prepareStatement(SELECT_BY_ID_QUERY);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                preparedStatement.close();
                User resultUser = mapUser(resultSet);
                resultSet.close();
                return resultUser;
            }
            throw new DatabaseException("Can not find user by id: " + id);
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public Collection<User> findAll() throws DatabaseException {
        Collection<User> result = new LinkedList<>();
        try {
            Connection connection = connectionFactory.createConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_QUERY);
            while(resultSet.next()) {
                User user = new User();
                user.setId(new Long(resultSet.getLong(1)));
                user.setFirstName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setDateOfBirth(resultSet.getDate(4));
                result.add(user);
            }
            statement.close();
            connection.close();
        } catch (DatabaseException e) {
            throw e;
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
        return result;
    }

    private User mapUser(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(1);
        String firstName = resultSet.getString(2);
        String lastName = resultSet.getString(3);
        Date dateOfBirth = resultSet.getDate(4);
        User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setDateOfBirth(dateOfBirth);
        return user;
    }
}
