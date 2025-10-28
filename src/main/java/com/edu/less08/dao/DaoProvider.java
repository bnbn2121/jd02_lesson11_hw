package com.edu.less08.dao;

import com.edu.less08.dao.impl.*;

public class DaoProvider {
    private static final DaoProvider instance = new DaoProvider();
    private UserDao userDao;
    private NewsDao newsDao;
    private NewsContextStorageDao newsContextStorageDao;


    private DaoProvider(){}

    public static DaoProvider getInstance() {
        return instance;
    }

    public UserDao getUserDao() {
        if (userDao == null) {
            userDao = new UserDaoDB();
        }
        return userDao;
    }

    public NewsDao getNewsDao() {
        if (newsDao == null) {
            newsDao = new NewsDaoDB();
        }
        return newsDao;
    }


    public NewsContextStorageDao getNewsContextStorageDao() {
        if (newsContextStorageDao == null) {
            newsContextStorageDao = new NewsContextStorageDaoFile();
        }
        return newsContextStorageDao;
    }
}
