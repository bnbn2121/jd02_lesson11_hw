package com.edu.less08.dao;

import com.edu.less08.dao.impl.*;

public class DaoProvider {
    private static final DaoProvider instance = new DaoProvider();
    private UserDao userDao;
    private NewsDao newsDao;
    private NewsContentStorageDao newsContentStorageDao;
    private CommentDao commentDao;


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

    public NewsContentStorageDao getNewsContentStorageDao() {
        if (newsContentStorageDao == null) {
            newsContentStorageDao = new NewsContentStorageDaoFile();
        }
        return newsContentStorageDao;
    }

    public CommentDao getCommentDao() {
        if (commentDao == null) {
            commentDao = new CommentDaoDB();
        }
        return commentDao;
    }
}
