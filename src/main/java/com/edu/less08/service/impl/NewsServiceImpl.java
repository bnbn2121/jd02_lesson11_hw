package com.edu.less08.service.impl;

import com.edu.less08.dao.DaoException;
import com.edu.less08.dao.DaoProvider;
import com.edu.less08.dao.NewsDao;
import com.edu.less08.model.News;
import com.edu.less08.service.NewsService;
import com.edu.less08.service.ServiceException;

import java.util.List;

public class NewsServiceImpl implements NewsService {
    private final NewsDao newsDao = DaoProvider.getInstance().getNewsDao();

    @Override
    public List<News> getNews(int indexFirst, int countNews) throws ServiceException {
        try {
            return newsDao.getNews(indexFirst, countNews);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
