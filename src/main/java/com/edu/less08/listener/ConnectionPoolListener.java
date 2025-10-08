package com.edu.less08.listener;

import com.edu.less08.dao.pool.ConnectionManager;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.sql.SQLException;

@WebListener
public class ConnectionPoolListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String url = "jdbc:mysql://127.0.0.1/news_app_database?sslMode=DISABLED&allowPublicKeyRetrieval=true";
        String daoUser = "root";
        String daoPassword = "123";
        int maxPoolSize = 3;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found", e);
        }

        try {
            ConnectionManager.initConnectionPool(url, daoUser, daoPassword, maxPoolSize);
        } catch (SQLException e) {
            throw new RuntimeException("error with initialize connection pool", e);
            // реализовать нормальную обработку исключения с редиректом
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            ConnectionManager.closeConnectionPool();
        } catch (SQLException e) {
            throw new RuntimeException("error with close connection pool", e);
            // реализовать нормальную обработку исключения с редиректом
        }
    }
}
