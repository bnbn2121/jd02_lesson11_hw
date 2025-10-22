package com.edu.less08.dao;

import com.edu.less08.model.Status;

public interface StatusDao {
    int getStatusIdByName(Status status) throws DaoException;
    Status getStatusNameById(int statusId) throws DaoException;
}
