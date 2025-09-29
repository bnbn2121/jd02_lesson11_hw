package com.edu.less08.dao.impl;

import com.edu.less08.dao.DaoException;
import com.edu.less08.dao.NewsDao;
import com.edu.less08.model.News;

import java.util.List;

public class DBNewsDao implements NewsDao {
    @Override
    public List<News> getNews(int indexFirst, int countNews) throws DaoException {
        return null;
    }

    @Override
    public List<News> getAllNews() throws DaoException {
        return null;
    }

    @Override
    public News getNewsById(int idNews) throws DaoException {
        return null;
    }

    @Override
    public void addNews(News news) throws DaoException {

    }

    @Override
    public void removeNewsById(int idNews) throws DaoException {

    }

    @Override
    public News updateNews(News news) throws DaoException {
        return null;
    }

    @Override
    public int findIndexNewsById(int idNews) throws DaoException {
        return 0;
    }
}
