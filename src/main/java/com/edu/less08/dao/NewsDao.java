package com.edu.less08.dao;

import com.edu.less08.model.News;
import com.edu.less08.model.Status;

import java.util.List;
import java.util.Optional;

public interface NewsDao {
    List<News> getNewsPaginated(int offset, int limit) throws DaoException;
    List<News> getAllNews() throws DaoException;
    Optional<News> getNewsById(int idNews) throws DaoException;
    News addNews(News news, String content) throws DaoException;
    void deleteNewsById(int idNews) throws DaoException;
    void updateNews(News news) throws DaoException;
    int getCountNews(Status... status) throws DaoException;
}
