package com.edu.less08.dao.impl;

import com.edu.less08.dao.DaoException;
import com.edu.less08.dao.RoleDao;
import com.edu.less08.dao.pool.ConnectionManager;
import com.edu.less08.model.UserRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleDaoDB implements RoleDao {
    private final ConnectionManager connectionManager = new ConnectionManager();

    @Override
    public int getRoleIdByName(UserRole userRole) throws DaoException {
        String sqlQuery = "Select id from roles where type = ?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)){
            preparedStatement.setString(1, userRole.name());
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()) {
                    return resultSet.getInt("id");
                }
                throw new DaoException("role with name = %s not found".formatted(userRole.name()));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public UserRole getRoleNameById(int roleId) throws DaoException {
        String sqlQuery = "Select type from roles where id = ?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)){
            preparedStatement.setInt(1, roleId);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()) {
                    return (UserRole)resultSet.getObject("type");
                }
                throw new DaoException("role with id = %d not found".formatted(roleId));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
