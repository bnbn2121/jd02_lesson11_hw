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

    private News createNewsPrototype(String title, String brief, String imagePath, int publisherId) throws ServiceException {
        News.NewsBuilder builder = new News.NewsBuilder();
        News prototypeNews = builder
                .setTitle(title)
                .setBrief(brief)
                .setImagePath(imagePath)
                .setPublisherId(publisherId)
                .build();
        return prototypeNews;
    }

    @Override
    public List<News> getNews(int indexFirst, int countNews) throws ServiceException {
        try {
            return newsDao.getNewsPaginated(indexFirst, countNews);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public News addNews(String title, String brief, String content, String imagePath, String publisherLogin) throws ServiceException {
        try {
            Optional<User> optionalUser = userDao.getUserByLogin(publisherLogin);
            if (optionalUser.isEmpty()) {
                throw new ServiceException().setUserMessage("error identification user");
            }
            int publisherId = optionalUser.get().getId();
            News prototypeNews = createNewsPrototype(title, brief, imagePath, publisherId);
            return newsDao.addNews(prototypeNews, content);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int getAllActiveNewsCount() throws ServiceException {
        try {
            return newsDao.getAllNewsCount(Status.ACTIVE);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
