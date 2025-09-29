package com.edu.less08.dao.impl;

import com.edu.less08.dao.DaoException;
import com.edu.less08.dao.UserDao;
import com.edu.less08.model.User;

import java.util.List;

public class DBUserDao implements UserDao {
    @Override
    public List<User> getAllUsers() throws DaoException {
        return null;
    }

    @Override
    public User getUserByLogin(String login) throws DaoException {
        return null;
    }

    @Override
    public void addUser(User user) throws DaoException {

    }

    @Override
    public void removeUserByLogin(String login) throws DaoException {

    }

    @Override
    public User updateUser(User user) throws DaoException {
        return null;
    }

    @Override
    public int findIndexNewsByLogin(String login) throws DaoException {
        return 0;
    }
}
