package com.edu.less08.dao;

import com.edu.less08.dao.impl.*;

public class DaoProvider {
    private static final DaoProvider instance = new DaoProvider();
    private UserDao userDao;
    private NewsDao newsDao;
    private RoleDao roleDao;
    private StatusDao statusDao;
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

    public RoleDao getRoleDao() {
        if (roleDao == null) {
            roleDao = new RoleDaoDB();
        }
        return roleDao;
    }

    public StatusDao getStatusDao() {
        if (statusDao == null) {
            statusDao = new StatusDaoDB();
        }
        return statusDao;
    }

    public NewsContextStorageDao getNewsContextStorageDao() {
        if (newsContextStorageDao == null) {
            newsContextStorageDao = new NewsContextStorageDaoFile();
        }
        return newsContextStorageDao;
    }
}
