package com.edu.less08.dao.impl;

import com.edu.less08.dao.DaoException;
import com.edu.less08.dao.NewsDao;
import com.edu.less08.dao.pool.ConnectionManager;
import com.edu.less08.model.News;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NewsDaoDB implements NewsDao {
    private final ConnectionManager connectionManager = new ConnectionManager();
    private News.NewsBuilder newsBuilder = new News.NewsBuilder();
    private static final String SELECT_NEWS_PAGINATED = "SELECT * FROM news ORDER BY publish_date DESC LIMIT ? OFFSET ?";
    private static final String SELECT_NEWS_BY_ID = "SELECT * FROM news WHERE id=?";
    private static final String INSERT_NEWS = "INSERT INTO news (title, brief, content_path, image_path, publish_date, publisher_id, status_id)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?);";
    private static final String DELETE_NEWS_BY_ID = "DELETE FROM news WHERE id = ?";
    private static final String UPDATE_NEWS = "UPDATE news SET title = ?, brief = ?, content_path = ?, image_path = ?, publisher_id = ?, status_id = ? WHERE id = ?";

    @Override
    public List<News> getNewsPaginated(int offset, int limit) throws DaoException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_NEWS_PAGINATED)){
            preparedStatement.setInt(1, limit);
            preparedStatement.setInt(2, offset);
            List<News> newsList = executeQueryAndCreateListNews(preparedStatement);
            return newsList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private List<News> executeQueryAndCreateListNews(PreparedStatement preparedStatement) throws DaoException {
        try (ResultSet resultSet = preparedStatement.executeQuery()){
            List<News> newsList = new ArrayList<>();
            while (resultSet.next()) {
                newsList.add(createNewsFromResultSet(resultSet));
            }
            return newsList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private News createNewsFromResultSet(ResultSet resultSet) throws SQLException {
        Date dateFromResultSet = resultSet.getDate("publish_date");
        LocalDate publishDate = (dateFromResultSet != null) ? dateFromResultSet.toLocalDate() : null;

        newsBuilder.clearBuilder();
        newsBuilder.setId(resultSet.getInt("id"))
                .setTitle(resultSet.getString("title"))
                .setBrief(resultSet.getString("brief"))
                .setContentPath(resultSet.getString("content_path"))
                .setImagePath(resultSet.getString("image_path"))
                .setPublishDate(publishDate)
                .setPublisherId(resultSet.getInt("publisher_id"))
                .setStatusId(resultSet.getInt("status_id"));
        return newsBuilder.build();
    }

    @Override
    public List<News> getAllNews() throws DaoException {
        return null;
    }

    @Override
    public Optional<News> getNewsById(int idNews) throws DaoException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_NEWS_BY_ID)){
            preparedStatement.setInt(1, idNews);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    News news = createNewsFromResultSet(resultSet);
                    return Optional.of(news);
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public News addNews(News news) throws DaoException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEWS, PreparedStatement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1, news.getTitle());
            preparedStatement.setString(2, news.getBrief());
            preparedStatement.setString(3, news.getContentPath());
            preparedStatement.setString(4, news.getImagePath());
            preparedStatement.setDate(5, Date.valueOf(LocalDate.now()));
            preparedStatement.setInt(6, news.getPublisherId());
            preparedStatement.setInt(7, news.getStatusId());
            preparedStatement.executeUpdate();
            int newsId = getGeneratedId(preparedStatement);
            news.setId(newsId);
            return news;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private int getGeneratedId(PreparedStatement preparedStatement) throws DaoException {
        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new DaoException("Creating news failed, no ID obtained.");
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void deleteNewsById(int idNews) throws DaoException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_NEWS_BY_ID)) {
            preparedStatement.setInt(1, idNews);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void updateNews(News news) throws DaoException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_NEWS)) {
            preparedStatement.setString(1, news.getTitle());
            preparedStatement.setString(2, news.getBrief());
            preparedStatement.setString(3, news.getContentPath());
            preparedStatement.setString(4, news.getImagePath());
            preparedStatement.setInt(5, news.getPublisherId());
            preparedStatement.setInt(6, news.getStatusId());
            preparedStatement.setInt(7, news.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
