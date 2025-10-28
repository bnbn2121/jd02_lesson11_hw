package com.edu.less08.listener;

import com.edu.less08.dao.pool.ConnectionPoolCustom;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.sql.SQLException;
import java.util.Properties;

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
            ConnectionPoolCustom.initializeConnectionPool(url, daoUser, daoPassword, maxPoolSize);
        } catch (SQLException e) {
            throw new RuntimeException("error with initialize connection pool", e);
            // реализовать нормальную обработку исключения с редиректом на страницу ошибок мб
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            ConnectionPoolCustom.getInstance().shutdownPool();
        } catch (SQLException e) {
            throw new RuntimeException("error with close connection pool", e);
            // реализовать нормальную обработку исключения с редиректом
        }
    }
}
