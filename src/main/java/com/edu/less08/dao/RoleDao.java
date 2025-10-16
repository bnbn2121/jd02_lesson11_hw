package com.edu.less08.dao;

import com.edu.less08.model.UserRole;

public interface RoleDao {
    int getRoleIdByName(UserRole userRole) throws DaoException;
    UserRole getRoleNameById(int roleId) throws DaoException;
}
