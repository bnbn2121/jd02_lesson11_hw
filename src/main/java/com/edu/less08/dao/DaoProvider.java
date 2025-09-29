package com.edu.less08.dao;


import com.edu.less08.dao.impl.DBNewsDao;
import com.edu.less08.dao.impl.DBUserDao;
import com.edu.less08.dao.impl.HardcodeNewsDao;

public class DaoProvider {
    private static final DaoProvider instance = new DaoProvider();
    private final UserDao userDao = new DBUserDao();
    private final NewsDao newsDao = new HardcodeNewsDao();

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
}
