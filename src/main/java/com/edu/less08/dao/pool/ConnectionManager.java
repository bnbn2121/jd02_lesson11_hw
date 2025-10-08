package com.edu.less08.dao.pool;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {
    private static ConnectionPoolCustom connectionPool;

    public static void initConnectionPool(String url, String User, String Password, int maxPoolSize) throws SQLException {
        if (connectionPool == null) {
            connectionPool = new ConnectionPoolCustom(url, User, Password, maxPoolSize);
        }
    }

    public static void closeConnectionPool() throws SQLException {
        connectionPool.shutdownPool();
    }

    public Connection getConnection() throws SQLException {
        return connectionPool.getConnection();
    }
}
