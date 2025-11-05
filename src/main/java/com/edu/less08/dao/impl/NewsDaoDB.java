package com.edu.less08.dao.impl;

import com.edu.less08.dao.*;
import com.edu.less08.dao.pool.ConnectionPoolCustom;
import com.edu.less08.model.News;
import com.edu.less08.model.Status;
import com.edu.less08.model.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NewsDaoDB implements NewsDao {
    private final NewsContentStorageDao contentStorageDaoFile = DaoProvider.getInstance().getNewsContentStorageDao();
    private Status defaultAddNewsStatus = Status.ACTIVE;
    private static final String SELECT_NEWS_PAGINATED = "SELECT n.*, u.login as publisher_login, " +
            "s.type as status_type " +
            "FROM news n " +
            "JOIN users u ON n.publisher_id = u.id " +
            "JOIN status s ON n.status_id = s.id " +
            "WHERE s.type = ? " +
            "ORDER BY n.publish_date DESC, n.id DESC LIMIT ? OFFSET ?";
    private static final String SELECT_NEWS_BY_ID = "SELECT n.*, u.login as publisher_login, " +
            "s.type as status_type " +
            "FROM news n " +
            "JOIN users u ON n.publisher_id = u.id " +
            "JOIN status s ON n.status_id = s.id " +
            "WHERE n.id=?";
    private static final String INSERT_NEWS = "INSERT INTO news (title, brief, content_path, image_path, publish_date, publisher_id, status_id)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?);";
    private static final String DELETE_NEWS_BY_ID = "DELETE FROM news WHERE id = ?";
    private static final String UPDATE_NEWS = "UPDATE news SET title = ?, brief = ?, content_path = ?, image_path = ?, publisher_id = ?, status_id = ? WHERE id = ?";
    private static final String SELECT_COUNT_NEWS = "SELECT COUNT(*) FROM news WHERE status_id = ?";
    private static final String UPDATE_NEWS_CONTENT = "UPDATE news SET content_path = ? WHERE id = ?";

    @Override
    public List<News> getNewsPaginatedWithoutContent(int offset, int limit) throws DaoException {
        try (Connection connection = ConnectionPoolCustom.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_NEWS_PAGINATED)){
            preparedStatement.setString(1, Status.ACTIVE.name().toLowerCase());
            preparedStatement.setInt(2, limit);
            preparedStatement.setInt(3, offset);
            List<News> newsList = executeQueryAndCreateListNews(preparedStatement);
            return newsList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private List<News> executeQueryAndCreateListNews(PreparedStatement preparedStatement) throws SQLException {
        try (ResultSet resultSet = preparedStatement.executeQuery()){
            List<News> newsList = new ArrayList<>();
            while (resultSet.next()) {
                newsList.add(createNewsFromResultSetWithoutContent(resultSet));
            }
            return newsList;
        }
    }

    private News createNewsFromResultSetWithoutContent(ResultSet resultSet) throws SQLException {
        Date dateFromResultSet = resultSet.getDate("publish_date");
        LocalDate publishDate = (dateFromResultSet != null) ? dateFromResultSet.toLocalDate() : null;

        User publisher = new User();
        publisher.setId(resultSet.getInt("publisher_id"));
        publisher.setLogin(resultSet.getString("publisher_login"));
        Status status = Status.valueOf(resultSet.getString("status_type").toUpperCase());

        News.NewsBuilder builder = new News.NewsBuilder();
        builder.setId(resultSet.getInt("id"))
                .setTitle(resultSet.getString("title"))
                .setBrief(resultSet.getString("brief"))
                .setImagePath(resultSet.getString("image_path"))
                .setPublishDate(publishDate)
                .setPublisher(publisher)
                .setStatus(status);
        return builder.build();
    }

    @Override
    public List<News> getAllNews() throws DaoException {
        return null;
    }

    @Override
    public Optional<News> getNewsById(int idNews) throws DaoException {
        try (Connection connection = ConnectionPoolCustom.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_NEWS_BY_ID)){
            preparedStatement.setInt(1, idNews);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    News news = createNewsFromResultSetWithoutContent(resultSet);
                    loadContent(news);
                    return Optional.of(news);
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private void loadContent(News news) throws DaoException{
        String content = contentStorageDaoFile.getContentById(news.getId());
        news.setContent(content);
    }

    @Override
    public News addNews(News news, String content) throws DaoException {
        try (Connection connection = ConnectionPoolCustom.getInstance().getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement preparedStatementAdd = connection.prepareStatement(INSERT_NEWS, PreparedStatement.RETURN_GENERATED_KEYS);
                 PreparedStatement preparedStatementUpdateContent = connection.prepareStatement(UPDATE_NEWS_CONTENT)) {
            preparedStatementAdd.setString(1, news.getTitle());
            preparedStatementAdd.setString(2, news.getBrief());
            preparedStatementAdd.setString(3, "");
            preparedStatementAdd.setString(4, news.getImagePath());
            LocalDate createdDate = LocalDate.now();
            preparedStatementAdd.setDate(5, Date.valueOf(createdDate));
            preparedStatementAdd.setInt(6, news.getPublisher().getId());
            preparedStatementAdd.setInt(7, StatusUtil.getStatusIdByName(defaultAddNewsStatus));
            preparedStatementAdd.executeUpdate();

            int newsId = getGeneratedId(preparedStatementAdd);
            String contentPath = contentStorageDaoFile.addContent(newsId, content);
            preparedStatementUpdateContent.setString(1, contentPath);
            preparedStatementUpdateContent.setInt(2, newsId);
            preparedStatementUpdateContent.executeUpdate();
            connection.commit();

            news.setContent(contentPath);
            news.setId(newsId);
            news.setStatus(Status.ACTIVE);
            news.setPublishDate(createdDate);
            return news;
            } catch (Exception e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private int getGeneratedId(PreparedStatement preparedStatement) throws DaoException {
        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new DaoException("id not obtained.");
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void deleteNewsById(int idNews) throws DaoException {
        try (Connection connection = ConnectionPoolCustom.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_NEWS_BY_ID)) {
            preparedStatement.setInt(1, idNews);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void updateNews(News news) throws DaoException {
        try (Connection connection = ConnectionPoolCustom.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_NEWS)) {
            preparedStatement.setString(1, news.getTitle());
            preparedStatement.setString(2, news.getBrief());
            String content = news.getContent();
            String contentPath = contentStorageDaoFile.updateContentById(news.getId(), content);
            preparedStatement.setString(3, contentPath);
            preparedStatement.setString(4, news.getImagePath());
            preparedStatement.setInt(5, news.getPublisher().getId());
            preparedStatement.setInt(6, StatusUtil.getStatusIdByName(news.getStatus()));
            preparedStatement.setInt(7, news.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public int getCountNewsByStatus(Status ... status) throws DaoException {
        try (Connection connection = ConnectionPoolCustom.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_COUNT_NEWS)) {
            int countNews = 0;
            for (Status oneStatus : status) {
                int statusId = StatusUtil.getStatusIdByName(oneStatus);
                preparedStatement.setInt(1, statusId);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()) {
                    countNews += resultSet.getInt(1);
                }
            }
            return countNews;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
