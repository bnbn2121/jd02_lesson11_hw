package com.edu.less08.dao;

import com.edu.less08.model.News;

import java.util.List;

public interface NewsDao {
    List<News> getNews(int indexFirst, int countNews) throws DaoException;
    List<News> getAllNews() throws DaoException;
    News getNewsById(int idNews) throws DaoException;
    void addNews(News news) throws DaoException;
    void removeNewsById(int idNews) throws DaoException;
    News updateNews(News news) throws DaoException;
    int findIndexNewsById(int idNews) throws DaoException;
}
