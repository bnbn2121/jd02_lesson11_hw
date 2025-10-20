package com.edu.less08.dao;

public interface NewsContextStorageDao {
    String addContext(int newsId, String context) throws DaoException;
    String getContextById(int newsId) throws DaoException;
    String updateContextById(int newsId, String context) throws DaoException;
    boolean deleteContextById(int id) throws DaoException;
}
