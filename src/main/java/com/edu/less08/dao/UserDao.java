package com.edu.less08.dao;

import com.edu.less08.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<User> getUserByLogin(String login) throws DaoException;
    Optional<User> getUserById(int id) throws DaoException;
    User addUser(User user) throws DaoException;
    void deleteUserByLogin(String login) throws DaoException;
    void updateUser(User user) throws DaoException;
}
