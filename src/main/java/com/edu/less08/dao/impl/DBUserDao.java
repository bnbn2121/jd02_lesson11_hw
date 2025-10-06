package com.edu.less08.dao.impl;

import com.edu.less08.dao.DaoException;
import com.edu.less08.dao.UserDao;
import com.edu.less08.model.User;

import java.sql.*;
import java.util.List;

public class DBUserDao implements UserDao {
    String url = "jdbc:mysql://127.0.0.1/NewsAppDatabase?sslMode=DISABLED";
    String daoUser = "root";
    String daoPassword = "123";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found", e);
        }
    }

    @Override
    public List<User> getAllUsers() throws DaoException {
        return null;
    }

    @Override
    public User getUserByLogin(String login) throws DaoException {
        String sqlQuery = "Select * from users where login = ?";
        try (Connection connection = DriverManager.getConnection(url, daoUser, daoPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);) {
            preparedStatement.setString(1, login);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                User user = null;
                if (resultSet.next()) {
                    user = new User(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getInt(5)
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
        String sqlQuery = "INSERT INTO users (login, email, password, role_id) VALUES (?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, daoUser, daoPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setInt(4, user.getRoleId());

            int affectedRows = preparedStatement.executeUpdate();

            return null; //подумать нужно ли что-то возвращать
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
