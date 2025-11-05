package com.edu.less08.dao.impl;

import com.edu.less08.dao.CommentDao;
import com.edu.less08.dao.DaoException;
import com.edu.less08.dao.pool.ConnectionPoolCustom;
import com.edu.less08.model.Comment;
import com.edu.less08.model.Status;
import com.edu.less08.model.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommentDaoDB implements CommentDao {
    private Status defaultAddCommentStatus = Status.ACTIVE;
    private Status defaultGetListCommentsStatus = Status.ACTIVE;
    private static final String ADD_COMMENT = "INSERT INTO comments (text, author_id, publish_date, status_id) VALUES (?, ?, ?, ?)";
    private static final String ADD_LINK_NEWS = "INSERT INTO comments_news (id, news_id) VALUES (?, ?)";
    private static final String ADD_LINK_USER = "INSERT INTO comments_users (id, user_id) VALUES (?, ?)";
    private static final String FIND_COMMENT_BY_ID =
            "SELECT c.*, u.login as author_login, cn.news_id, s.type " +
                    "FROM comments c " +
                    "INNER JOIN users u ON c.author_id = u.id " +
                    "INNER JOIN comments_news cn ON c.id = cn.id " +
                    "INNER JOIN status s ON c.status_id = s.id " +
                    "WHERE c.id = ?";
    private static final String FIND_COMMENTS_BY_NEWS_ID =
            "SELECT c.*, u.login as author_login, cn.news_id, s.type  " +
                    "FROM comments c " +
                    "INNER JOIN users u ON c.author_id = u.id " +
                    "INNER JOIN comments_news cn ON c.id = cn.id " +
                    "INNER JOIN status s ON c.status_id = s.id " +
                    "WHERE cn.news_id = ? AND c.status_id = ? " +
                    "ORDER BY c.publish_date DESC";
    private static final String DELETE_COMMENT_BY_ID = "DELETE FROM comments WHERE id = ?";

    @Override
    public Comment addNewsComment(Comment comment, int newsId) throws DaoException {
        try (Connection connection = ConnectionPoolCustom.getInstance().getConnection()) {
            try {
                connection.setAutoCommit(false);
                int commentId = insertComment(connection, comment);
                insertLinkNews(connection, commentId, newsId);
                connection.commit();
                return comment;
            } catch (Exception e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Comment addUserComment(Comment comment, int userId) throws DaoException {
        return null;
    }

    private int insertComment(Connection connection, Comment comment) throws SQLException, DaoException{
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_COMMENT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, comment.getText());
            preparedStatement.setInt(2, comment.getAuthor().getId());
            LocalDateTime createdTime = LocalDateTime.now();
            preparedStatement.setTimestamp(3, Timestamp.valueOf(createdTime));
            preparedStatement.setInt(4, StatusUtil.getStatusIdByName(defaultAddCommentStatus));
            preparedStatement.executeUpdate();
            int commentId = getGeneratedId(preparedStatement);
            comment.setId(commentId);
            comment.setPublishDate(createdTime);
            return commentId;
        }
    }

    private int getGeneratedId(PreparedStatement preparedStatement) throws SQLException {
        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("id not obtained");
            }
        }
    }

    private void insertLinkNews(Connection connection, int commentId, int newsId) throws SQLException{
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_LINK_NEWS)) {
            preparedStatement.setInt(1, commentId);
            preparedStatement.setInt(2, newsId);
            preparedStatement.executeUpdate();
        }
    }

    private void insertLinkUsers(Connection connection, Comment comment) throws SQLException{
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_LINK_USER)) {
            preparedStatement.setInt(1, comment.getId());
            preparedStatement.setInt(2, comment.getAuthor().getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public Optional<Comment> getCommentById(int commentId) throws DaoException {
        try (Connection connection = ConnectionPoolCustom.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_COMMENT_BY_ID)) {
            preparedStatement.setInt(1, commentId);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Comment comment = createCommentFromResultSet(resultSet);
                    return Optional.of(comment);
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Comment> getAllCommentsByNews(int newsId) throws DaoException {
        try (Connection connection = ConnectionPoolCustom.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_COMMENTS_BY_NEWS_ID)) {
            Status foundedNewsStatus = defaultGetListCommentsStatus;
            preparedStatement.setInt(1, newsId);
            preparedStatement.setInt(2, StatusUtil.getStatusIdByName(foundedNewsStatus));
            List<Comment> commentList = executeQueryAndGetListComment(preparedStatement);
            return commentList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private List<Comment> executeQueryAndGetListComment(PreparedStatement preparedStatement) throws SQLException {
        try(ResultSet resultSet = preparedStatement.executeQuery()) {
            List<Comment> commentList = new ArrayList<>();
            while (resultSet.next()) {
                Comment comment = createCommentFromResultSet(resultSet);
                commentList.add(comment);
            }
            return commentList;
        }
    }

    private Comment createCommentFromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("author_id"));
        user.setLogin(resultSet.getString("author_login"));
        Comment comment = new Comment(
                resultSet.getInt("id"),
                resultSet.getString("text"),
                user,
                resultSet.getTimestamp("publish_date").toLocalDateTime(),
                Status.valueOf(resultSet.getString("type").toUpperCase())
        );
        return comment;
    }

    @Override
    public List<Comment> getAllCommentsByUser(int userId) throws DaoException {
        return List.of();
    }

    @Override
    public void deleteComment(int commentId) throws DaoException {
        try (Connection connection = ConnectionPoolCustom.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_COMMENT_BY_ID)) {
            preparedStatement.setInt(1, commentId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
