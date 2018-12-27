package main.java.ua.nure.kn.yesipov.database;

import main.java.ua.nure.kn.yesipov.User;

import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;

import static java.util.Objects.nonNull;

public class HsqldbUserDao implements UserDao {
    private ConnectionFactory connectionFactory;

    private static final String INSERT_QUERY = "INSERT INTO users (firstname, lastname, dateofbirth) VALUES (?, ?, ?)";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM users";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM users WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM users WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE users SET firstname = ?, lastname = ?, dateofbirth = ?  WHERE id = ?";
    private static final String CALL_IDENTITY = "CALL IDENTITY()";
    private static final String FIND_BY_NAME = "SELECT * FROM users WHERE firstname=? AND lastname=?";
    public static final String FIND_ALL = "SELECT * FROM users";

    public HsqldbUserDao() {
    }

    public HsqldbUserDao(ConnectionFactory connectionFactory) {
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

        CallableStatement callableStatement = null;

        ResultSet resultSet = null;

        try (Connection connection = connectionFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {
            int k = 1;
            statement.setString(k++, user.getFirstName());
            statement.setString(k++, user.getLastName());
            statement.setDate(k, new Date(user.getDateOfBirth().getTime()));

            int n = statement.executeUpdate();
            if (n != 1) {
                throw new DatabaseException("Number of expected rows: " + n);
            }

            callableStatement = connection.prepareCall(CALL_IDENTITY);
            resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                user.setId(resultSet.getLong(1));
            }

        } catch (SQLException e) {
            throw new DatabaseException("Exception occurred during execution query", e);
        } finally {
            tryToCloseResultSet(resultSet);
            if (nonNull(callableStatement)) {
                try {
                    callableStatement.close();
                } catch (SQLException e) {
                    throw new DatabaseException("Cannot close resources", e);
                }
            }
        }
        return user;
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
    public Collection<User> find(String firstName, String lastName) throws DatabaseException {
        Collection<User> result = new LinkedList<>();
        User user;
        ResultSet resultSet = null;
        try (Connection connection = connectionFactory.createConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_NAME)) {

            statement.setString(1, firstName);
            statement.setString(2, lastName);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user = new User();
                int k = 1;

                user.setId(resultSet.getLong(k++));
                user.setFirstName(resultSet.getString(k++));
                user.setLastName(resultSet.getString(k++));
                user.setDateOfBirth(resultSet.getDate(k));
                result.add(user);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Exception occurred during execution query", e);
        } finally {
            tryToCloseResultSet(resultSet);
        }
        return result;
    }


    @Override
    public Collection<User> findAll() throws DatabaseException {
        Collection<User> result = new LinkedList<>();
        try {
            Connection connection = connectionFactory.createConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_QUERY);
            while (resultSet.next()) {
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

    private void tryToCloseResultSet(ResultSet resultSet) throws DatabaseException {
        if (nonNull(resultSet)) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new DatabaseException("Cannot close resources", e);
            }
        }
    }
}
