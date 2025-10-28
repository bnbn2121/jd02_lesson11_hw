package com.edu.less08.dao.impl;

import com.edu.less08.dao.DaoException;
import com.edu.less08.dao.pool.ConnectionPoolCustom;
import com.edu.less08.model.Status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatusUtil {

    public static int getStatusIdByName(Status status) throws DaoException {
        String sqlQuery = "Select id from status where type = ?";
        try (Connection connection = ConnectionPoolCustom.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)){
            preparedStatement.setString(1, status.name());
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()) {
                    return resultSet.getInt("id");
                }
                throw new DaoException("status with name = %s not found".formatted(status.name()));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public static Status getStatusNameById(int statusId) throws DaoException {
        String sqlQuery = "Select type from status where id = ?";
        try (Connection connection = ConnectionPoolCustom.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)){
            preparedStatement.setInt(1, statusId);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()) {
                    String statusName = (String)resultSet.getObject("type");
                    return Status.valueOf(statusName.toUpperCase());
                }
                throw new DaoException("status with id = %d not found".formatted(statusId));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
