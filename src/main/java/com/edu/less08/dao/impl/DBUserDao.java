package com.edu.less08.dao.impl;

import com.edu.less08.dao.DaoException;
import com.edu.less08.dao.UserDao;
import com.edu.less08.dao.pool.ConnectionManager;
import com.edu.less08.model.User;

import java.sql.*;
import java.util.List;

public class DBUserDao implements UserDao {
    private final ConnectionManager connectionManager = new ConnectionManager();

    @Override
    public List<User> getAllUsers() throws DaoException {
        return null;
    }

    @Override
    public User getUserByLogin(String login) throws DaoException {
        String sqlQuery = "Select * from users where login = ?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);) {
            preparedStatement.setString(1, login);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                User user = null;
                if (resultSet.next()) {
                    user = new User(
                            resultSet.getInt("id"),
                            resultSet.getString("login"),
                            resultSet.getString("email"),
                            resultSet.getString("password"),
                            resultSet.getInt("role_id"),
                            resultSet.getInt("status_id")
                    );
                }
                return user;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public User addUser(User user) throws DaoException {
        String sqlQuery = "INSERT INTO users (login, email, password, role_id, status_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setInt(4, user.getRoleId());
            preparedStatement.setInt(5, user.getStatusId());

            int affectedRows = preparedStatement.executeUpdate();

            return null; //подумать нужно ли возвращать юзера
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void removeUserByLogin(String login) throws DaoException {

    }

    @Override
    public User updateUser(User user) throws DaoException {
        return null;
    }

    @Override
    public int findIndexNewsByLogin(String login) throws DaoException {
        return 0;
    }
}
