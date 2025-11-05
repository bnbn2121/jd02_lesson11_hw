package com.edu.less08.dao.impl;

import com.edu.less08.dao.DaoException;
import com.edu.less08.dao.pool.ConnectionPoolCustom;
import com.edu.less08.model.Status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class StatusUtil {
    private static Map<Integer, Status> statusMap = new HashMap<>();

    static {
        initMap();
    }

    private static void initMap() {
        String sqlQuery = "Select * from status";
        try (Connection connection = ConnectionPoolCustom.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String statusName = resultSet.getString("type").toUpperCase();
                statusMap.put(id, Status.valueOf(statusName));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getStatusIdByName(Status status) throws DaoException{
        int statusId = 0;
        if (statusMap.containsValue(status)) {
            for(Map.Entry entry : statusMap.entrySet()) {
                if (entry.getValue() == status) {
                    statusId = (int) entry.getKey();
                }
            }
        } else {
            statusId = getStatusIdByNameFromDB(status);
            statusMap.put(statusId, status);
        }
        return statusId;
    }

    public static Status getStatusById(int statusId) throws DaoException{
        if (statusMap.containsKey(statusId)) {
            return statusMap.get(statusId);
        } else {
            Status status = getStatusByIdFromDB(statusId);
            statusMap.put(statusId, status);
            return status;
        }
    }

    private static int getStatusIdByNameFromDB(Status status) throws DaoException {
        String sqlQuery = "Select id from status where type = ?";
        try (Connection connection = ConnectionPoolCustom.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, status.name());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
                throw new DaoException("status %s not found".formatted(status.name()));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private static Status getStatusByIdFromDB(int statusId) throws DaoException {
        String sqlQuery = "Select type from status where id = ?";
        try (Connection connection = ConnectionPoolCustom.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setInt(1, statusId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String statusName = (String) resultSet.getObject("type");
                    return Status.valueOf(statusName.toUpperCase());
                }
                throw new DaoException("status with id = %d not found".formatted(statusId));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
