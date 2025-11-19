package com.edu.less08.service.impl;

import com.edu.less08.dao.*;
import com.edu.less08.model.News;
import com.edu.less08.model.Status;
import com.edu.less08.model.User;
import com.edu.less08.service.NewsService;
import com.edu.less08.service.ServiceException;

import java.util.List;
import java.util.Optional;

public class NewsServiceImpl implements NewsService {
    private final NewsDao newsDao = DaoProvider.getInstance().getNewsDao();
    private final UserDao userDao = DaoProvider.getInstance().getUserDao();

    private News createNewsPrototype(String title, String brief, String imagePath, User publisher) throws ServiceException {
        News.NewsBuilder builder = new News.NewsBuilder();
        News prototypeNews = builder
                .setTitle(title)
                .setBrief(brief)
                .setImagePath(imagePath)
                .setPublisher(publisher)
                .build();
        return prototypeNews;
    }

    @Override
    public List<News> getNews(int indexFirst, int countNews) throws ServiceException {
        try {
            return newsDao.getNewsPaginatedWithoutContent(indexFirst, countNews);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public News addNews(String title, String brief, String content, String imagePath, int publisherId) throws ServiceException {
        try {
            Optional<User> optionalUser = userDao.getUserById(publisherId);
            if (optionalUser.isEmpty()) {
                throw new ServiceException().setUserMessage("error identification user");
            }
            News prototypeNews = createNewsPrototype(title, brief, imagePath, optionalUser.get());
            return newsDao.addNews(prototypeNews, content);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int getAllActiveNewsCount() throws ServiceException {
        try {
            return newsDao.getCountNewsByStatus(Status.ACTIVE);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public News getNewsById(int id) throws ServiceException {
        try {
            Optional<News> optionalNews = newsDao.getNewsById(id);
            if (optionalNews.isEmpty()) {
                throw new ServiceException("news not found");
            }
            return optionalNews.get();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public News updateNews(int newsId, String newTitle, String newBrief, String newContent, String newImagePath, Status newStatus) throws ServiceException {
        try {
            News newsForUpdate = getNewsById(newsId);
            newsForUpdate.setTitle(newTitle);
            newsForUpdate.setBrief(newBrief);
            newsForUpdate.setContent(newContent);
            newsForUpdate.setImagePath(newImagePath);
            newsForUpdate.setStatus(newStatus);
            newsDao.updateNews(newsForUpdate);
            return newsForUpdate;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean deleteNewsById(int newsId) throws ServiceException {
        try {
            newsDao.deleteNewsById(newsId);
            return true;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
