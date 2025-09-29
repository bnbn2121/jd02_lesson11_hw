package com.edu.less08.service;

import com.edu.less08.dao.NewsDao;
import com.edu.less08.model.News;

import java.util.List;

public interface NewsService {
    List<News> getNews(int indexFirst, int countNews) throws ServiceException;
}
