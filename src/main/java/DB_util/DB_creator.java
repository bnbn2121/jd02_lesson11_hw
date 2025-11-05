package DB_util;

import java.sql.*;

public class DB_creator {
    String url = "jdbc:mysql://127.0.0.1:3306/?sslMode=DISABLED&allowPublicKeyRetrieval=true";
    String daoUser = "root";
    String daoPassword = "123";
    String url2 = "jdbc:mysql://127.0.0.1:3306/news_app_database?sslMode=DISABLED&allowPublicKeyRetrieval=true";

    private Connection connection;
    private final String dbName = "news_app_database";

    public static void main(String[] args) {
        DB_creator creator = new DB_creator();
        creator.createAndInitDatabase();
    }


    public void initConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url, daoUser, daoPassword);
    }

    public void createAndInitDatabase() {
        try {
            initConnection();
            createDB();
            useDB();
            createTables();
            initEnumTables();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }

    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void executeQuery(String query) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
        }
    }

    public void createDB() throws SQLException {
        executeQuery("CREATE DATABASE IF NOT EXISTS " + dbName);
    }

    public void useDB() throws SQLException {
        executeQuery("USE " + dbName);
    }

    public void createTables() throws SQLException {
        executeQuery(
                "CREATE TABLE `roles` (\n" +
                        "  `id` int NOT NULL AUTO_INCREMENT,\n" +
                        "  `type` varchar(100) NOT NULL,\n" +
                        "  PRIMARY KEY (`id`),\n" +
                        "  UNIQUE KEY `roles_unique` (`type`)\n" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;"
        );
        executeQuery(
                "CREATE TABLE `status` (\n" +
                        "  `id` int NOT NULL AUTO_INCREMENT,\n" +
                        "  `type` varchar(100) NOT NULL,\n" +
                        "  PRIMARY KEY (`id`),\n" +
                        "  UNIQUE KEY `status_uk` (`type`)\n" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;"
        );
        executeQuery(
                "CREATE TABLE `users` (\n" +
                        "  `id` int NOT NULL AUTO_INCREMENT,\n" +
                        "  `login` varchar(100) NOT NULL,\n" +
                        "  `email` varchar(100) NOT NULL,\n" +
                        "  `password` varchar(100) NOT NULL,\n" +
                        "  `role_id` int NOT NULL,\n" +
                        "  `status_id` int NOT NULL,\n" +
                        "  PRIMARY KEY (`id`),\n" +
                        "  KEY `users_role_FK` (`role_id`),\n" +
                        "  KEY `users_status_FK` (`status_id`),\n" +
                        "  UNIQUE KEY `login_unique` (`login`)\n" +
                        "  UNIQUE KEY `email_unique` (`email`)\n" +
                        "  CONSTRAINT `users_role_FK` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),\n" +
                        "  CONSTRAINT `users_status_FK` FOREIGN KEY (`status_id`) REFERENCES `status` (`id`)\n" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;\n"
        );
        executeQuery(
                "CREATE TABLE `comments` (\n" +
                        "  `id` int NOT NULL AUTO_INCREMENT,\n" +
                        "  `text` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,\n" +
                        "  `author_id` int NOT NULL,\n" +
                        "  `publish_date` datetime NOT NULL,\n" +
                        "  `status_id` int NOT NULL,\n" +
                        "  PRIMARY KEY (`id`),\n" +
                        "  KEY `comments_users_FK` (`author_id`),\n" +
                        "  KEY `comments_status_FK` (`status_id`),\n" +
                        "  CONSTRAINT `comments_status_FK` FOREIGN KEY (`status_id`) REFERENCES `status` (`id`),\n" +
                        "  CONSTRAINT `comments_users_FK` FOREIGN KEY (`author_id`) REFERENCES `users` (`id`)\n" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;\n"
        );


        executeQuery(
                "CREATE TABLE `comments_users` (\n" +
                        "  `id` int NOT NULL,\n" +
                        "  `user_id` int NOT NULL,\n" +
                        "  PRIMARY KEY (`id`),\n" +
                        "  KEY `comments_users_users_FK` (`user_id`),\n" +
                        "  CONSTRAINT `comments_users_comments_FK` FOREIGN KEY (`id`) REFERENCES `comments` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,\n" +
                        "  CONSTRAINT `comments_users_users_FK` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)\n" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;"
        );
        executeQuery(
                "CREATE TABLE `news` (\n" +
                        "  `id` int NOT NULL AUTO_INCREMENT,\n" +
                        "  `title` varchar(100) NOT NULL,\n" +
                        "  `brief` varchar(300) DEFAULT NULL,\n" +
                        "  `content_path` varchar(100) NOT NULL,\n" +
                        "  `image_path` varchar(200) DEFAULT NULL,\n" +
                        "  `publish_date` date NOT NULL,\n" +
                        "  `publisher_id` int NOT NULL,\n" +
                        "  `status_id` int NOT NULL,\n" +
                        "  PRIMARY KEY (`id`),\n" +
                        "  KEY `news_users_FK` (`publisher_id`),\n" +
                        "  KEY `news_status_FK` (`status_id`),\n" +
                        "  CONSTRAINT `news_status_FK` FOREIGN KEY (`status_id`) REFERENCES `status` (`id`),\n" +
                        "  CONSTRAINT `news_users_FK` FOREIGN KEY (`publisher_id`) REFERENCES `users` (`id`)\n" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;\n"
        );
        executeQuery(
                "CREATE TABLE `reports` (\n" +
                        "  `id` int NOT NULL AUTO_INCREMENT,\n" +
                        "  `content` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,\n" +
                        "  `author_id` int NOT NULL,\n" +
                        "  `publish_date` datetime NOT NULL,\n" +
                        "  `status_id` int NOT NULL,\n" +
                        "  `resolver_id` int DEFAULT NULL,\n" +
                        "  `resolver_comment` varchar(250) DEFAULT NULL,\n" +
                        "  PRIMARY KEY (`id`),\n" +
                        "  KEY `complaints_users_FK` (`author_id`),\n" +
                        "  KEY `complaints_status_FK` (`status_id`),\n" +
                        "  CONSTRAINT `complaints_status_FK` FOREIGN KEY (`status_id`) REFERENCES `status` (`id`),\n" +
                        "  CONSTRAINT `complaints_users_FK` FOREIGN KEY (`author_id`) REFERENCES `users` (`id`)\n" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;\n"
        );
        executeQuery(
                "CREATE TABLE `reports_comments` (\n" +
                        "  `id` int NOT NULL,\n" +
                        "  `comment_id` int NOT NULL,\n" +
                        "  PRIMARY KEY (`id`),\n" +
                        "  KEY `reports_comments_comments_FK` (`comment_id`),\n" +
                        "  CONSTRAINT `reports_comments_comments_FK` FOREIGN KEY (`comment_id`) REFERENCES `comments` (`id`),\n" +
                        "  CONSTRAINT `reports_comments_reports_FK` FOREIGN KEY (`id`) REFERENCES `reports` (`id`)\n" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;\n"
        );
        executeQuery(
                "CREATE TABLE `reports_news` (\n" +
                        "  `id` int NOT NULL,\n" +
                        "  `news_id` int NOT NULL,\n" +
                        "  PRIMARY KEY (`id`),\n" +
                        "  KEY `reports_news_news_FK` (`news_id`),\n" +
                        "  CONSTRAINT `reports_news_news_FK` FOREIGN KEY (`news_id`) REFERENCES `news` (`id`),\n" +
                        "  CONSTRAINT `reports_news_reports_FK` FOREIGN KEY (`id`) REFERENCES `reports` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT\n" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;"
        );
        executeQuery(
                "CREATE TABLE `reports_users` (\n" +
                        "  `id` int NOT NULL,\n" +
                        "  `user_id` int NOT NULL,\n" +
                        "  PRIMARY KEY (`id`),\n" +
                        "  KEY `reports_users_users_FK` (`user_id`),\n" +
                        "  CONSTRAINT `reports_users_reports_FK` FOREIGN KEY (`id`) REFERENCES `reports` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,\n" +
                        "  CONSTRAINT `reports_users_users_FK` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)\n" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;"
        );
        executeQuery(
                "CREATE TABLE `comments_news` (\n" +
                        "  `id` int NOT NULL,\n" +
                        "  `news_id` int NOT NULL,\n" +
                        "  PRIMARY KEY (`id`),\n" +
                        "  KEY `comments_news_news_FK` (`news_id`),\n" +
                        "  CONSTRAINT `comments_news_comments_FK` FOREIGN KEY (`id`) REFERENCES `comments` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,\n" +
                        "  CONSTRAINT `comments_news_news_FK` FOREIGN KEY (`news_id`) REFERENCES `news` (`id`)\n" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;"
        );
    }

    public void initEnumTables() throws SQLException {
        executeQuery(
                "INSERT INTO `roles` (`type`) VALUES \n" +
                        "('user'),\n" +
                        "('moderator'), \n" +
                        "('admin'),\n" +
                        "('superadmin');"
        );
        executeQuery(
                "INSERT INTO `status` (`type`) VALUES \n" +
                        "('active'),\n" +
                        "('awaiting'),\n" +
                        "('closed');"
        );
    }
}
