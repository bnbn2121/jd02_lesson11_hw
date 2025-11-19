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
    private static final String SELECT_STATUS = "Select * from status";

    static {
        initMap();
    }

    private static void initMap() {
        try (Connection connection = ConnectionPoolCustom.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_STATUS);
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

    public static int getStatusIdByName(Status status) throws DaoException {
        for (Map.Entry<Integer, Status> entry : statusMap.entrySet()) {
            if (entry.getValue() == status) {
                return (int) entry.getKey();
            }
        }
        throw new DaoException("status %s not found".formatted(status.name()));
    }

        public static Status getStatusById (int statusId) throws DaoException {
            if (statusMap.containsKey(statusId)) {
                return statusMap.get(statusId);
            } else {
                throw new DaoException("status id = %d not found".formatted(statusId));
            }
        }
    }
