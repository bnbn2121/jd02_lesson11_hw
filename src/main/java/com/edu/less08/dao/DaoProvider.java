package com.edu.less08.dao;

import com.edu.less08.dao.impl.*;

public class DaoProvider {
    private static final DaoProvider instance = new DaoProvider();
    private final UserDao userDao = new UserDaoDB();
    private final NewsDao newsDao = new NewsDaoDB();
    private final RoleDao roleDao = new RoleDaoDB();
    private final NewsContextStorageDao newsContextStorageDao = new NewsContextStorageDaoFile();


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

    public NewsContextStorageDao getNewsContextStorageDao() {
        return newsContextStorageDao;
    }
}
