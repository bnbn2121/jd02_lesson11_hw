package com.edu.less08.dao;

import com.edu.less08.dao.impl.RoleDaoDB;
import com.edu.less08.dao.impl.UserDaoDB;
import com.edu.less08.dao.impl.HardcodeNewsDao;

public class DaoProvider {
    private static final DaoProvider instance = new DaoProvider();
    private final UserDao userDao = new UserDaoDB();
    private final NewsDao newsDao = new HardcodeNewsDao();
    private final RoleDao roleDao = new RoleDaoDB(); //реализовать через интерфейс


    private DaoProvider(){}

    public static DaoProvider getInstance() {
        return instance;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public NewsDao getNewsDao() {
        return newsDao;
    }

    public RoleDao getRoleDao() {
        return roleDao;
    }
}
