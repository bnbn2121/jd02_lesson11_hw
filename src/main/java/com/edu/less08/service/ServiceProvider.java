package com.edu.less08.service;


import com.edu.less08.service.impl.CommentServiceImpl;
import com.edu.less08.service.impl.NewsServiceImpl;
import com.edu.less08.service.impl.UserSecurityImpl;

public class ServiceProvider {
    private static final ServiceProvider instance = new ServiceProvider();
    private final UserSecurityService userSecurityService = new UserSecurityImpl();
    private final NewsService newsService = new NewsServiceImpl();
    private final CommentService commentService = new CommentServiceImpl();

    private ServiceProvider(){}

    public static ServiceProvider getInstance() {
        return instance;
    }

    public UserSecurityService getUserSecurityService() {
        return userSecurityService;
    }

    public NewsService getNewsService() {
        return newsService;
    }

    public CommentService getCommentService() {
        return commentService;
    }
}
