package com.edu.less08.dao.impl;

import com.edu.less08.dao.DaoException;
import com.edu.less08.dao.UserDao;
import com.edu.less08.dao.pool.ConnectionManager;
import com.edu.less08.model.User;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class UserDaoDB implements UserDao {
    private final ConnectionManager connectionManager = new ConnectionManager();
    private final static String SELECT_USER_BY_LOGIN = "SELECT * FROM users WHERE login = ?";
    private final static String SELECT_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
    private final static String INSERT_USER = "INSERT INTO users (login, email, password, role_id, status_id) VALUES (?, ?, ?, ?, ?)";
    private final static String DELETE_USER_BY_LOGIN = "DELETE FROM users WHERE login = ?";
    private final static String UPDATE_USER = "UPDATE users SET login = ?, email = ?, password = ?, role_id = ?, status_id = ? WHERE id = ?";

    @Override
    public List<User> getAllUsers() throws DaoException {
        return null;
    }

    @Override
    public Optional<User> getUserById(int id) throws DaoException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)) {
            preparedStatement.setInt(1, id);
            return executeQueryAndCreateOptionalUser(preparedStatement);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<User> getUserByLogin(String login) throws DaoException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_LOGIN)) {
            preparedStatement.setString(1, login);
            return executeQueryAndCreateOptionalUser(preparedStatement);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Optional<User> executeQueryAndCreateOptionalUser(PreparedStatement preparedStatement) throws DaoException {
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                User user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("login"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getInt("role_id"),
                        resultSet.getInt("status_id")
                );
                return Optional.of(user);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public User addUser(User user) throws DaoException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setInt(4, user.getRoleId());
            preparedStatement.setInt(5, user.getStatusId());
            preparedStatement.executeUpdate();
            int generatedId = getGeneratedId(preparedStatement);
            user.setId(generatedId);
            return user;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private int getGeneratedId(PreparedStatement preparedStatement) throws DaoException {
        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new DaoException("Creating user failed, no ID obtained.");
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void deleteUserByLogin(String login) throws DaoException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_BY_LOGIN)) {
            preparedStatement.setString(1, login);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void updateUser(User user) throws DaoException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setInt(4, user.getRoleId());
            preparedStatement.setInt(5, user.getStatusId());
            preparedStatement.setInt(6, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
