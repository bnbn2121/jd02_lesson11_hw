package com.edu.less08.dao;

public interface NewsContentStorageDao {
    String addContent(int newsId, String content) throws DaoException;
    String getContentById(int newsId) throws DaoException;
    String getContentByPath(String path) throws DaoException;
    String updateContentById(int newsId, String content) throws DaoException;
    boolean deleteContentById(int id) throws DaoException;
}
