package com.edu.less08.dao.impl;

import com.edu.less08.dao.DaoException;
import com.edu.less08.dao.pool.ConnectionPoolCustom;
import com.edu.less08.model.Status;
import com.edu.less08.model.UserRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class RoleUtil {
    private static Map<Integer, UserRole> roleMap = new HashMap<>();
    private static final String SELECT_ROLES = "Select * from roles";

    static {
        initMap();
    }

    private static void initMap() {
        try (Connection connection = ConnectionPoolCustom.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ROLES);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String roleName = resultSet.getString("type").toUpperCase();
                roleMap.put(id, UserRole.valueOf(roleName));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getRoleIdByName(UserRole role) throws DaoException {
        for (Map.Entry<Integer, UserRole> entry : roleMap.entrySet()) {
            if (entry.getValue() == role) {
                return (int) entry.getKey();
            }
        }
        throw new DaoException("role %s not found".formatted(role.name()));
    }

    public static UserRole getRoleNameById (int roleId) throws DaoException {
        if (roleMap.containsKey(roleId)) {
            return roleMap.get(roleId);
        } else {
            throw new DaoException("role id = %d not found".formatted(roleId));
        }
    }
}
