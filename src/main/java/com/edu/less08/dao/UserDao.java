package com.edu.less08.dao;

import com.edu.less08.model.User;

import java.util.List;

public interface UserDao {
    List<User> getAllUsers() throws DaoException;
    User getUserByLogin(String login) throws DaoException;
    User addUser(User user) throws DaoException;
    void removeUserByLogin(String login) throws DaoException;
    User updateUser(User user) throws DaoException;
    int findIndexNewsByLogin(String login) throws DaoException;
    int getRoleIdByName(String role) throws DaoException;
}
